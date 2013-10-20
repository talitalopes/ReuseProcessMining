package br.ufrj.cos.prisma.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufrj.cos.prima.model.DocGateway;
import br.ufrj.cos.prima.model.DocNode;
import br.ufrj.cos.prima.model.DocTransition;

public class XPDLPreprocessor {
	private static boolean verbose = true;
	
	public static String ACTIVITY_TAG_NAME = "Activity";
	public static String TRANSITION_TAG_NAME = "Transition";

	String[] actitiesToRemovePrefix = { "START [", "Artificial", "END [" };

	public static HashMap<String, DocNode> nodes = new HashMap<String, DocNode>();
	public static HashMap<String, DocGateway> gateways = new HashMap<String, DocGateway>();
	public static HashMap<String, DocNode> startsAndEnds = new HashMap<String, DocNode>();
	public static SortedSet<String> elementsIds;

	public static void format(String inputFile, String outputFile) {
		try {
			File fXmlFile = new File(inputFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList classes = doc.getElementsByTagName(ACTIVITY_TAG_NAME);
			NodeList transitions = doc
					.getElementsByTagName(TRANSITION_TAG_NAME);

			log("1. Get Nodes and Transitions from Doc");
			getNodesAndTransitionsFromDoc(doc, classes, transitions);
			
			log("2. Import Model And Create Package Tasks");
			importModelAndCreatePackageTasks();

			log("3. Add Conditional Tasks For Gateways");
			addConditionalTasksForGateways();
			
			//log("4. Remove Artificials Start And End");
			//removeArtificialStart();
			//removeArtificialEnd();
			
			log("4. Update Doc");
			updateDoc(doc, outputFile);
			
			log("5. Start Transformation");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getNodesAndTransitionsFromDoc(Document doc,
			NodeList activities, NodeList transitions) {
		for (int temp = 0; temp < activities.getLength(); temp++) {
			Node activityNode = activities.item(temp);

			if (activityNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element nodeElement = (Element) activityNode;
			DocNode node = new DocNode();
			node.id = nodeElement.getAttribute("Id");
			node.name = nodeElement.getAttribute("Name");

			if (shouldBeRemoved(node.name)) {
				if (node.id.equals("1459158898"))
					log (node.id);
				startsAndEnds.put(node.id, node);
			}

			if (node.name.contains("GATEWAY")) {
				DocGateway gateway = new DocGateway();
				gateway.id = node.id;
				gateway.name = node.name;
				gateway.isSplit = nodeElement.getElementsByTagName("Split")
						.getLength() > 0;

				if (gateway.isSplit) {
					gateway.logicalType = ((Element) nodeElement
							.getElementsByTagName("Split").item(0))
							.getAttribute("Type");
				} else {
					gateway.logicalType = ((Element) nodeElement
							.getElementsByTagName("Join").item(0))
							.getAttribute("Type");
				}
				
				gateways.put(gateway.id, gateway);
			}

			nodes.put(node.id, node);
		}

		for (int temp = 0; temp < transitions.getLength(); temp++) {
			Node transitionNode = transitions.item(temp);

			if (transitionNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element transitionElement = (Element) transitionNode;
			DocTransition transition = new DocTransition();
			transition.id = transitionElement.getAttribute("Id");
			transition.source = transitionElement.getAttribute("From");
			transition.target = transitionElement.getAttribute("To");

			updateNodeTransitions(transition);
		}

		elementsIds = new TreeSet<String>(nodes.keySet());
	}

	public static void updateNodeTransitions(DocTransition transition) {
		if (nodes.get(transition.source) != null) {
			nodes.get(transition.source).outgoing.add(transition);
		}

		if (nodes.get(transition.target) != null) {
			nodes.get(transition.target).incoming.add(transition);
		}

		if (startsAndEnds.get(transition.source) != null) {
			startsAndEnds.get(transition.source).outgoing.add(transition);
		}

		if (startsAndEnds.get(transition.target) != null) {
			startsAndEnds.get(transition.target).incoming.add(transition);
		}
		
		if (gateways.get(transition.source) != null) {
			gateways.get(transition.source).outgoing.add(transition);
		}

		if (gateways.get(transition.target) != null) {
			gateways.get(transition.target).incoming.add(transition);
		}

	}
	
	public static void removeArtificialStart() {
		List<String> idsToRemove = new ArrayList<String>();
		
		for (DocNode node : startsAndEnds.values()) {
			if ((node.outgoing.size() > 1) && (node.incoming.size() > 1))
				continue;

			if (node.name.toLowerCase().contains("end"))
				continue;
			
			// previousNode -- START
			DocTransition incoming = node.incoming.iterator().next();
			// nextNode -- Node next
			DocTransition outgoing = node.outgoing.iterator().next();

			// Result: Node previousNode -- Node nextNode
			DocNode previousNode = nodes.get(incoming.source);
			DocNode nextNode = nodes.get(outgoing.target);

			outgoing.source = incoming.source;

			previousNode.outgoing.clear();
			previousNode.outgoing.add(outgoing);

			nextNode.incoming.clear();
			nextNode.incoming.add(incoming);
		}
		
		for (String id: idsToRemove) {
			nodes.remove(id);
			startsAndEnds.remove(id);			
		}
	}
	
	// Not working :(
	public static void removeArtificialEnd() {
		for (DocNode node : startsAndEnds.values()) {
			if ((node.outgoing.size() > 1) && (node.incoming.size() > 1))
				continue;

			if (node.name.toLowerCase().contains("start"))
				continue;

			// previousNode -- END
			DocTransition incoming = node.incoming.iterator().next();
			// nextNode -- Node next
			DocTransition outgoing = node.outgoing.iterator().next();

			// Result: Node previousNode -- Node nextNode
			DocNode previousNode = nodes.get(incoming.source);
			DocNode nextNode = nodes.get(outgoing.target);
			
			outgoing.source = incoming.source;

			previousNode.outgoing.clear();
			previousNode.outgoing.add(outgoing);

			nextNode.incoming.clear();
			nextNode.incoming.add(incoming);

			nodes.remove(node.id);
		}
	}

	public static void importModelAndCreatePackageTasks() {
		DocNode startNode = null;
		for (DocNode n: nodes.values()) {
			if (n.name.equals("START EVENT")) {
				startNode = n;
			}
		}
		
		if (startNode == null) {
			log("Couldn't find start node");
			return;
		}
		
		DocNode importModelTask = new DocNode();
		importModelTask.id = generateIdForElement();
		importModelTask.name = "ImportModel_models/arcade.uml";

		DocNode declarePackageVariableTask = new DocNode();
		declarePackageVariableTask.id = generateIdForElement();
		declarePackageVariableTask.name = String.format("%s_%s", "Declaration", "packageVar");

		DocNode createPackageTask = new DocNode();
		createPackageTask.id = generateIdForElement();
		createPackageTask.name = String.format("%s_%s", "NewPackage", "?");
		
		DocTransition startNodeToImportModel = new DocTransition();
		startNodeToImportModel.id = generateIdForElement();
		startNodeToImportModel.source = startNode.id;
		startNodeToImportModel.target = importModelTask.id;
		
		DocTransition importModel2PackageVarDeclaration = new DocTransition();
		importModel2PackageVarDeclaration.id = generateIdForElement();
		importModel2PackageVarDeclaration.source = importModelTask.id;
		importModel2PackageVarDeclaration.target = declarePackageVariableTask.id;
		
		DocTransition packageVarDeclaration2CreatePackage = new DocTransition();
		packageVarDeclaration2CreatePackage.id = generateIdForElement();
		packageVarDeclaration2CreatePackage.source = declarePackageVariableTask.id;
		packageVarDeclaration2CreatePackage.target = createPackageTask.id;
		
		DocTransition createPackage2StartNodeTarget = new DocTransition();
		createPackage2StartNodeTarget.id = generateIdForElement();
		createPackage2StartNodeTarget.source = createPackageTask.id;
		createPackage2StartNodeTarget.target = startNode.outgoing.iterator().next().target;
		 
		startNode.outgoing.clear(); 
		startNode.outgoing.add(startNodeToImportModel);
		
		importModelTask.incoming.add(startNodeToImportModel);
		importModelTask.outgoing.add(importModel2PackageVarDeclaration);

		declarePackageVariableTask.incoming.add(importModel2PackageVarDeclaration);
		declarePackageVariableTask.outgoing.add(packageVarDeclaration2CreatePackage);

		createPackageTask.incoming.add(packageVarDeclaration2CreatePackage);
		createPackageTask.outgoing.add(createPackage2StartNodeTarget);
		
		nodes.put(importModelTask.id, importModelTask);
		nodes.put(declarePackageVariableTask.id, declarePackageVariableTask);
		nodes.put(createPackageTask.id, createPackageTask);
	}
	
	public static void addConditionalTasksForGateways() {
		for (DocGateway gateway : gateways.values()) {
			if (gateway.isSplit) {
				String conditionalTaskId = String.valueOf((Integer
						.parseInt(gateway.id) - 1));
				String conditionName = String.format("%s_%s",
						"EvalConditionBPMN", "?");

				DocNode conditionalNode = new DocNode();
				conditionalNode.id = conditionalTaskId;
				conditionalNode.name = conditionName;

				DocTransition transitionOut = new DocTransition();
				transitionOut.id = generateIdForElement();
				transitionOut.target = gateway.id;
				transitionOut.source = conditionalTaskId;

				conditionalNode.outgoing.add(transitionOut);

				for (DocTransition t : gateway.incoming) {
					t.target = conditionalTaskId;
					conditionalNode.incoming.add(t);
					
					for (DocTransition dt: nodes.get(t.source).outgoing) {
						dt.target = conditionalTaskId;
					}
				}
				
				gateway.incoming.clear();
				gateway.incoming.add(transitionOut);

				nodes.put(conditionalTaskId, conditionalNode);
			}
		}
	}

	public static void updateDoc(Document doc, String outputFile) {
		Node activitiesParent = doc.getElementsByTagName("Activities").item(0);
		Node transitionsParent = doc.getElementsByTagName("Transitions")
				.item(0);

		clearChildNodes(activitiesParent);
		clearChildNodes(transitionsParent);

		for (DocNode n : nodes.values()) {
			if (gateways.get(n.id) != null) {
				gateways.get(n.id).createNodeElement(doc, activitiesParent, transitionsParent);
				continue;
			}
			n.write2Doc(doc, activitiesParent, transitionsParent);
		}

		Util.writeEditedDom(doc, outputFile);
	}

	/*
	 * public static void addDeclarationForClasses(Document doc, NodeList
	 * classes) { for (int temp = 0; temp < classes.getLength(); temp++) { Node
	 * classNode = classes.item(temp);
	 * 
	 * if (classNode.getNodeType() == Node.ELEMENT_NODE) { Element classElement
	 * = (Element) classNode; String elementName =
	 * classElement.getAttribute("Name");
	 * 
	 * if (elementName.contains("ClassExtension")) { int id =
	 * Integer.parseInt(classElement.getAttribute("Id")); int declarationTaskId
	 * = id - 1; String declarationName = String.format("%s_%s", "Declaration",
	 * classElement.getAttribute("Name") .split("_")[1]); Element
	 * declarationElement = addDeclarationElement(doc, declarationTaskId,
	 * declarationName);
	 * 
	 * Transition transitionObj = new Transition(); transitionObj.targetNode =
	 * classNode; transitionObj.sourceNode = declarationElement;
	 * transitionsMap.put(classElement.getAttribute("Id"), transitionObj);
	 * 
	 * classNode.getParentNode().appendChild(declarationElement); } } } }
	 * 
	 * public static Element addDeclarationElement(Document doc, int id, String
	 * name) { Element declarationElement =
	 * doc.createElement(ACTIVITY_TAG_NAME);
	 * declarationElement.setAttribute("Id", String.valueOf(id));
	 * declarationElement.setAttribute("Name", String.valueOf(name));
	 * 
	 * return declarationElement; }
	 * 
	 * public static void addNewTransitions(Document doc, NodeList transitions)
	 * { List<Element> newTransitions = new ArrayList<Element>(); Node
	 * transitionsParent = null;
	 * 
	 * for (int temp = 0; temp < transitions.getLength(); temp++) { Node nNode =
	 * transitions.item(temp);
	 * 
	 * if (nNode.getNodeType() == Node.ELEMENT_NODE) { Element transitionElement
	 * = (Element) nNode; transitionsParent = transitionElement.getParentNode();
	 * String targetId = transitionElement.getAttribute("To");
	 * 
	 * if (transitionsMap.containsKey(targetId)) { Transition transition =
	 * transitionsMap.get(targetId);
	 * 
	 * Element declarationTransition = addDeclarationTransition( doc,
	 * transitionElement, (Element) transition.sourceNode, (Element)
	 * transition.targetNode);
	 * 
	 * newTransitions.add(declarationTransition); }
	 * 
	 * // Remove graphic info clearChildNodes(nNode); }
	 * 
	 * }
	 * 
	 * if (transitionsParent != null) { for (Element e : newTransitions) {
	 * transitionsParent.appendChild(e); }
	 * 
	 * for (Element e : elementsToAdd) { transitionsParent.appendChild(e); }
	 * 
	 * } }
	 */

	/**
	 * 
	 * <Transition Id="transitionId" From="1455064424" To="declarationId">
	 * <Transition Id="transitionId + 1" From="declarationId" To="classId">
	 * 
	 */
	/*
	 * public static Element addDeclarationTransition(Document doc, Element
	 * transition, Element declaration, Element classElement) {
	 * 
	 * int declarationId = Integer.parseInt(declaration.getAttribute("Id")); int
	 * classId = Integer.parseInt(classElement.getAttribute("Id")); int
	 * transitionId = Integer.parseInt(transition.getAttribute("Id"));
	 * 
	 * // replace target in the current node transition.setAttribute("To",
	 * String.valueOf(declarationId));
	 * 
	 * // create new node Element transitionElement =
	 * doc.createElement(TRANSITION_TAG_NAME);
	 * transitionElement.setAttribute("Id", String.valueOf(transitionId + 1));
	 * transitionElement.setAttribute("From", String.valueOf(declarationId));
	 * transitionElement.setAttribute("To", String.valueOf(classId));
	 * 
	 * return transitionElement; }
	 */

	public static void clearChildNodes(Node node) {
		while (node.hasChildNodes()) {
			NodeList nList = node.getChildNodes();

			int index = node.getChildNodes().getLength() - 1;

			Node n = nList.item(index);
			clearChildNodes(n);
			node.removeChild(n);
		}
	}

	private static String generateIdForElement() {
		Integer id = (Integer.parseInt(elementsIds.last())) + 1;
		String idStr = String.valueOf(id);
		elementsIds.add(idStr);

		return idStr;
	}

	private static boolean shouldBeRemoved(String name) {
		String[] actitiesToRemove = { "START [", "Artificial", "END [" };
		for (int i = 0; i < actitiesToRemove.length; i++) {
			// System.out.println(String.format("Compare string '%s' : '%s'",
			// name.toLowerCase(), actitiesToRemove[i].toLowerCase()));

			if (name.toLowerCase().contains(actitiesToRemove[i].toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/***
	 * Method to sort activity's nodes.
	 * 
	 * */
	public static void sortActivities(Document doc) {
		Node activitiesNode = doc.getElementsByTagName("Activities").item(0);
		NodeList activities = activitiesNode.getChildNodes();

		Map<Integer, Node> activitiesNodes = new HashMap<Integer, Node>();

		for (int i = 0; i < activities.getLength(); i++) {
			Node nNode = activities.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				int id = Integer.parseInt(((Element) nNode).getAttribute("Id"));
				activitiesNodes.put(id, nNode);
			}
		}

		clearChildNodes(activitiesNode);

		// Add sorted activities
		SortedSet<Integer> keys = new TreeSet<Integer>(activitiesNodes.keySet());
		for (Integer key : keys) {
			Node activityNode = activitiesNodes.get(key);
			activitiesNode.appendChild(activityNode);
		}

	}

	private static void log(String message) {
		if (verbose) {
			System.out.println(message);
		}
	}
}
