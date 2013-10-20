package br.ufrj.cos.prisma.graph;

import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XPDLGraphHelper {

	private static String ACTIVITY_TAG = "Activity";
	private static String TRANSITION_TAG = "Transition";

	public static HashMap<String, Node> nodesIds = new HashMap<String, Node>();

	public static DirectedGraph<Node, DefaultEdge> g = new DefaultDirectedGraph<Node, DefaultEdge>(
			DefaultEdge.class);
	
	public static Document document;
	
	public static DirectedGraph<Node, DefaultEdge> createGraph() {
		document = GraphHelper
				.getDomObject("metamodel/input.prom.xpdl.xml");
		
		NodeList activitiesNodes = GraphHelper.getNodesWithType(document,
				ACTIVITY_TAG);
		createNodesForType(activitiesNodes);

		NodeList edges = GraphHelper.getNodesWithType(document, TRANSITION_TAG);
		createEdges(edges);

		removeArtificialStartAndEnd();
		
		addConditionals();

		GraphHelper.log("Transitions", String.valueOf(edges.getLength()));
		GraphHelper.log("Activities: " + nodesIds.size());
		GraphHelper.log("Vertexs: " + g.vertexSet().size());
		GraphHelper.log("Edges: " + g.edgeSet().size());
		
		return g;
	}

	public static DirectedGraph<Node, DefaultEdge> getGraph() {
		return g;
	}

	public static void createNodesForType(NodeList nodes) {
		for (int temp = 0; temp < nodes.getLength(); temp++) {
			Node node = nodes.item(temp);
			if (node == null || node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element element = (Element) node;
			String id = element.getAttribute("Id");

			g.addVertex(node);
			nodesIds.put(id, node);
		}
	}

	private static void createEdges(NodeList edges) {
		for (int temp = 0; temp < edges.getLength(); temp++) {
			Node sequenceNode = edges.item(temp);
			if (sequenceNode == null
					|| sequenceNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element sequenceElement = (Element) sequenceNode;
			String sourceId = sequenceElement.getAttribute("From");
			String targetId = sequenceElement.getAttribute("To");

			GraphHelper.log(String.format(
					"Source: %s |  Target: %s | existskey: %b", sourceId,
					targetId, nodesIds.containsKey(sourceId)));

			if (nodesIds.get(sourceId) != null
					&& nodesIds.get(targetId) != null) {
				g.addEdge(nodesIds.get(sourceId), nodesIds.get(targetId));
			}
		}
	}

	private static void removeArtificialStartAndEnd() {
		for (Node n: g.vertexSet()) {
			if (n.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
		}
	}
	
	private static void addConditionals() {
		HashSet<DefaultEdge> edgesToBeDeleted = new HashSet<DefaultEdge>();
		
		for (Node n: g.vertexSet()) {
			if (getNodeName(n).contains("GATEWAY") && isSplitGateway(n)) {
				String id = Integer.toString(getConditionalId(n));
				Element conditional = document.createElement("Activity");
				conditional.setAttribute("Id", String.valueOf(id));
				conditional.setAttribute("Name", String.valueOf("EvalConditionBPMN"));		
				
				g.addVertex(conditional);
				
				// Insert node before gateway
				DefaultEdge edge = g.incomingEdgesOf(n).iterator().next();
				
				g.addEdge(g.getEdgeSource(edge), conditional);
				g.addEdge(conditional, g.getEdgeTarget(edge));
				
				if (g.getEdgeTarget(edge) == n) {
					System.out.println("OK!!!");
				}
				edgesToBeDeleted.add(edge);
			}
		}
		
		for (DefaultEdge edge: edgesToBeDeleted) {
			g.removeEdge(edge);
		}
	}
	
	private static String getNodeName(Node n) {
		Element nodeElement = (Element)n;
		return nodeElement.getAttribute("Name");
	}
	
	private static boolean isSplitGateway(Node n) {
		Element nodeElement = (Element)n;
		return nodeElement.getElementsByTagName("Split").getLength() > 0;
	}
	
	private static int getConditionalId(Node n) {
		Element nodeElement = (Element)n;
		return Integer.parseInt(nodeElement.getAttribute("Id")) - 1;
	}
	
	private static boolean shouldBeRemoved(String name) {
		String[] actitiesToRemove = {"START [", "Artificial ", "END ["};
		for (int i = 0; i < actitiesToRemove.length; i++) {
			if (name.toLowerCase().contains(actitiesToRemove[i].toLowerCase()))
				return true;
		}
		return false;
	}
}
