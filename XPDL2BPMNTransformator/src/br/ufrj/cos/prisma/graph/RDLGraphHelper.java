package br.ufrj.cos.prisma.graph;

import java.util.HashMap;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RDLGraphHelper {

	public static HashMap<String, Node> nodesIds = new HashMap<String, Node>();

	public static DirectedGraph<Node, DefaultEdge> g = new DefaultDirectedGraph<Node, DefaultEdge>(
			DefaultEdge.class);

	public static DirectedGraph<Node, DefaultEdge> createGraph() {
		Document document = GraphHelper.getDomObject("metamodel/rdl.xml");

		NodeList taskNodes = GraphHelper.getNodesWithType(document, "task");
		createNodesForType(taskNodes);

		NodeList xorGatewayNodes = GraphHelper.getNodesWithType(document, "exclusiveGateway");
		createNodesForType(xorGatewayNodes);
		
		NodeList orGatewayNodes = GraphHelper.getNodesWithType(document, "inclusiveGateway");
		createNodesForType(orGatewayNodes);
		
		NodeList edges = GraphHelper.getNodesWithType(document, "sequenceFlow");
		createEdges(edges);

		GraphHelper.log("Transitions", String.valueOf(edges.getLength()));
		GraphHelper.log("Activities: " + nodesIds.size());
		GraphHelper.log("Vertexs: " + g.vertexSet().size());
		GraphHelper.log("Edges: " + g.edgeSet().size());
		
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
			
			GraphHelper.log(node.getNodeName() + " : " + element.getAttribute("Id"));

			g.addVertex(node);
			nodesIds.put(id, node);	
		}
	}
	
	public static void createEdges(NodeList edges) {
		for (int temp = 0; temp < edges.getLength(); temp++) {
			Node sequenceNode = edges.item(temp);
			if (sequenceNode == null
					|| sequenceNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element sequenceElement = (Element) sequenceNode;
			String sourceId = sequenceElement.getAttribute("sourceRef");
			String targetId = sequenceElement.getAttribute("targetRef");

			GraphHelper.log(String.format(
					"Source: %s |  Target: %s | existskey: %b", sourceId,
					targetId, nodesIds.containsKey(sourceId)));

			if (nodesIds.get(sourceId) != null
					&& nodesIds.get(targetId) != null) {
				g.addEdge(nodesIds.get(sourceId), nodesIds.get(targetId));
			}
		}
	}
	
}
