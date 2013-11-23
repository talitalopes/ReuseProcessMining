package br.ufrj.cos.prima.model;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DocNode {
	public static String ACTIVITY_TAG_NAME = "Activity";
	
	public String name;
	public String id;
	public Set<DocTransition> incoming = new HashSet<DocTransition>();
	public Set<DocTransition> outgoing = new HashSet<DocTransition>();
	
	public void createNodeElement(Document doc, Node parent) {
		Element element = doc.createElement(ACTIVITY_TAG_NAME);
		element.setAttribute("Id", id);
		element.setAttribute("Name", name);
		
		parent.appendChild(element);
	}
	
	public void createTransitions(Document doc, Node parent) {
		for (DocTransition transition: incoming) {
			transition.createNodeElement(doc, parent);
		}
		
		for (DocTransition transition: outgoing) {
			transition.createNodeElement(doc, parent);
		}
	}
	
	public void write2Doc(Document doc, Node activitiesParent, Node transitionsParent) {
		createNodeElement(doc, activitiesParent);

		for (DocTransition transition: outgoing) {
			transition.createNodeElement(doc, transitionsParent);
		}
	}
	
}
