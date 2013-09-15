package br.cos.ufrj.prisma;

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

public class XPDLPreprocessor {
	public static String ACTIVITY_TAG_NAME = "Activity";
	public static String TRANSITION_TAG_NAME = "Transition";

	public static Map<String, Transition> transitionsMap = new HashMap<String, Transition>();
	
	public static void format(String inputFile, String outputFile) {
		try {

			File fXmlFile = new File(inputFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList classes = doc.getElementsByTagName("Activity");
			addDeclarationForClasses(doc, classes);

			NodeList transitions = doc.getElementsByTagName("Transition");
			addTransitionsToClassDeclaration(doc, transitions);

			// TODO: try to sort activities. This method is not being used because of gateway activities.
			// sortActivities(doc);
			
			Util.writeEditedDom(doc, outputFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void addDeclarationForClasses(Document doc, NodeList classes) {
		for (int temp = 0; temp < classes.getLength(); temp++) {
			Node classNode = classes.item(temp);

			if (classNode.getNodeType() == Node.ELEMENT_NODE) {
				Element classElement = (Element) classNode;
				String elementName = classElement.getAttribute("Name");

				if (elementName.contains("ClassExtension")) {
					int id = Integer.parseInt(classElement.getAttribute("Id"));
					int declarationTaskId = id - 1;
					String declarationName = String.format("%s_%s",
							"Declaration", classElement.getAttribute("Name")
									.split("_")[1]);
					Element declarationElement = addDeclarationElement(doc,
							declarationTaskId, declarationName);

					Transition transitionObj = new Transition();
					transitionObj.classNode = classNode;
					transitionObj.declarationNode = declarationElement;
					transitionsMap.put(classElement.getAttribute("Id"),
							transitionObj);

					classNode.getParentNode().appendChild(declarationElement);
				}
			}
		}
	}

	public static Element addDeclarationElement(Document doc, int id,
			String name) {
		Element declarationElement = doc.createElement(ACTIVITY_TAG_NAME);
		declarationElement.setAttribute("Id", String.valueOf(id));
		declarationElement.setAttribute("Name", String.valueOf(name));

		return declarationElement;
	}

	public static void addTransitionsToClassDeclaration(Document doc,
			NodeList transitions) {
		List<Element> newTransitions = new ArrayList<Element>();
		Node transitionsParent = null;

		for (int temp = 0; temp < transitions.getLength(); temp++) {
			Node nNode = transitions.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element transitionElement = (Element) nNode;
				transitionsParent = transitionElement.getParentNode();
				String targetId = transitionElement.getAttribute("To");

				if (transitionsMap.containsKey(targetId)) {
					Transition transition = transitionsMap.get(targetId);

					Element declarationTransition = addDeclarationTransition(
							doc, transitionElement,
							(Element) transition.declarationNode,
							(Element) transition.classNode);

					newTransitions.add(declarationTransition);
				}

				// Remove graphic info
				clearChildNodes(nNode);
			}

		}

		if (transitionsParent != null) {
			for (Element e : newTransitions) {
				transitionsParent.appendChild(e);
			}
		}
	}

	/**
	 * 
	 * <Transition Id="transitionId" From="1455064424" To="declarationId">
	 * <Transition Id="transitionId + 1" From="declarationId" To="classId">
	 * 
	 */
	public static Element addDeclarationTransition(Document doc,
			Element transition, Element declaration, Element classElement) {

		int declarationId = Integer.parseInt(declaration.getAttribute("Id"));
		int classId = Integer.parseInt(classElement.getAttribute("Id"));
		int transitionId = Integer.parseInt(transition.getAttribute("Id"));

		// replace target in the current node
		transition.setAttribute("To", String.valueOf(declarationId));

		// create new node
		Element transitionElement = doc.createElement(TRANSITION_TAG_NAME);
		transitionElement.setAttribute("Id", String.valueOf(transitionId + 1));
		transitionElement.setAttribute("From", String.valueOf(declarationId));
		transitionElement.setAttribute("To", String.valueOf(classId));

		return transitionElement;
	}

	public static void clearChildNodes(Node node) {
		while (node.hasChildNodes()) {
			NodeList nList = node.getChildNodes();

			int index = node.getChildNodes().getLength() - 1;

			Node n = nList.item(index);
			clearChildNodes(n);
			node.removeChild(n);
		}

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
			
			if (nNode.getNodeType()  == Node.ELEMENT_NODE) {
				int id = Integer.parseInt(((Element)nNode).getAttribute("Id"));
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

}
