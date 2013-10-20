package br.ufrj.cos.prisma.graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GraphHelper {

	private static boolean verbose = true;

	public static Document getDomObject(String file) {
		File fXmlFile = new File(file);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	public static NodeList getNodesWithType(Document doc, String type) {
		return doc.getElementsByTagName(type);
	}

	public static void log(String tag, String message) {
		if (verbose)
			System.out.println(String.format("%s: %s", tag, message));
	}

	public static void log(String message) {
		if (verbose)
			System.out.println(String.format("%s", message));
	}

	public static void BFS(DirectedGraph<Node, DefaultEdge> g, Node startNode,
			Node finalNode) {
		Set<Node> visitedNodes = new HashSet<Node>();
		ArrayList<Node> queue = new ArrayList<Node>();
		queue.add(startNode);

		while (queue.size() != 0) {
			printList(queue);
			Node t = queue.remove(0);
			if (t.equals(finalNode))
				return;

			visitedNodes.add(t);
			for (DefaultEdge edge : g.edgesOf(t)) {
				Node u = g.getEdgeTarget(edge);
				if (!visitedNodes.contains(u)) {
					visitedNodes.add(u);
					queue.add(u);
				} else if (u == startNode) {
					log("Cycle");
				}
			}
		}
	}

	public static Node findClosestGateway(DirectedGraph<Node, DefaultEdge> g,
			Node startNode, Node finalNode) {
		Set<Node> visitedNodes = new HashSet<Node>();
		ArrayList<Node> queue = new ArrayList<Node>();
		queue.add(startNode);

		while (queue.size() != 0) {
			printList(queue);
			Node t = queue.remove(0);
			if (t.getNodeType() == Node.ELEMENT_NODE
					&& ((Element) t).getAttribute("Name").contains("GATEWAY")) {
				return t;
			}

			if (t.equals(finalNode))
				return t;

			visitedNodes.add(t);
			for (DefaultEdge edge : g.edgesOf(t)) {
				Node u = g.getEdgeTarget(edge);
				if (!visitedNodes.contains(u)) {
					visitedNodes.add(u);
					queue.add(u);
				} else if (u == startNode) {
					log("Cycle");
				}
			}
		}

		return null;
	}

	private static void printList(ArrayList<Node> queue) {
		String str = "";
		for (Node n : queue) {
			str += ((Element) n).getAttribute("Name") + " ";
		}
		log("Queue: " + str);
	}

}
