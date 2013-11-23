package br.ufrj.cos.prima.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DocTransition {
	public static String TRANSITION_TAG_NAME = "Transition";
	
	public String id;
	public String source;
	public String target;
	
	public void createNodeElement(Document doc, Node parent) {
		Element element = doc.createElement(TRANSITION_TAG_NAME);
		element.setAttribute("Id", id);
		element.setAttribute("From", source);
		element.setAttribute("To", target);
		
		parent.appendChild(element);
	}
	
}
