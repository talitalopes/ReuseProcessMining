package br.ufrj.cos.prima.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DocGateway extends DocNode {
	public boolean isSplit;
	public String logicalType;
	
	public void createNodeElement(Document doc, Node parent, Node transitionsParent) {
		Element element = doc.createElement(ACTIVITY_TAG_NAME);
		element.setAttribute("Id", id);
		element.setAttribute("Name", name);
		
		parent.appendChild(element);
		
		Element routeElement = doc.createElement("Route");
		routeElement.setAttribute("GatewayType", logicalType);
		routeElement.setAttribute("MarkerVisible", "true");
		
		Element transitionRestrictions = doc.createElement("TransitionRestrictions");
		Element transitionRestriction = doc.createElement("TransitionRestriction");
		
		Element split;
		if (isSplit) {
			split = doc.createElement("Split");
			split.setAttribute("Type", logicalType);
		} else {
			split = doc.createElement("Join");
			split.setAttribute("Type", logicalType);
		}
		
		transitionRestriction.appendChild(split);
		transitionRestrictions.appendChild(transitionRestriction);
		element.appendChild(routeElement);
		element.appendChild(transitionRestrictions);
		
		for (DocTransition transition: outgoing) {
			transition.createNodeElement(doc, transitionsParent);
		}

	}
}
