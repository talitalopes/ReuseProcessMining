package br.ufrj.cos.prisma;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MetamodelParser {

	public static void main(String[] args) {
		String inputFile = "metamodel/xpdl.xsd";
		
		format(inputFile);
	}

	public static void format(String inputFile) {
		try {
			File fXmlFile = new File(inputFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			String output = "";
			
			NodeList list = doc.getElementsByTagName("xsd:schema").item(0).getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element)list.item(i);
					
					if (el.getAttribute("name").length() > 0)
						output += String.format("entity('%s');", el.getAttribute("name"));
					
					Element n = (Element)el.getElementsByTagName("xsd:complexType").item(0);
					if ( n != null && n.getNodeType() == Node.ELEMENT_NODE) {
						NodeList attributes = n.getElementsByTagName("xsd:attribute");
						
						for (int j = 0; j < attributes.getLength(); j++) {
							if (list.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element attr = (Element)list.item(j);
								
								System.out.println(String.format("Element: %s, Attribute: %s", el.getAttribute("name"),
										attr.getAttribute("name")));
							}
							
						}
					}
					output += "\n";
				}
			}
			
			System.out.println(output);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
