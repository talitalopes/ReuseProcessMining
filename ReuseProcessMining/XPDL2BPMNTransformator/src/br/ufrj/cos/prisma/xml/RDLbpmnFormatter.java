package br.ufrj.cos.prisma.xml;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RDLbpmnFormatter {

	public static void format(String inputFile, String outputFile) {
		replaceWrongTags(inputFile, outputFile);

		// Code below helps identifying problems in the xml.
		/*
		 * File fXmlFile = new File(outputFile); DocumentBuilderFactory
		 * dbFactory = DocumentBuilderFactory.newInstance(); DocumentBuilder
		 * dBuilder;
		 * 
		 * try { dBuilder = dbFactory.newDocumentBuilder(); Document doc =
		 * dBuilder.parse(fXmlFile); doc.getDocumentElement().normalize();
		 * Util.writeEditedDom(doc, outputFile);
		 * 
		 * } catch (ParserConfigurationException e) { e.printStackTrace(); }
		 * catch (SAXException e) { e.printStackTrace(); } catch (IOException e)
		 * { e.printStackTrace(); }
		 */
	}

	private static void replaceWrongTags(String inputFile, String outputFile) {
		String content = "";

		try {
			content = new Scanner(new File(inputFile)).useDelimiter("\\Z")
					.next();
			content = content.replace("flowElementxsi:type",
					"flowElement xsi:type");
			content = content.replace("fromxsi:type =", "from xsi:type=");
			content = content.replace("toxsi:type = ", "to xsi:type=");
			
			content = content.replace(
					"flowElement xsi:type=\"model:tExclusiveGateway\"",
					"exclusiveGateway");

			content = content.replace("flowElement xsi:type=\"model:tTask\"",
					"task");
			content = content.replace("</flowElement>", "</task>");
			content = content.replace("model:tFormalExpression ",
					"\"model:tFormalExpression");

			content = content.replace("model:tFormalExpression ",
					"\"model:tFormalExpression");

			content = content.replace("<flowElement xsi:type =", "");
			content = content
					.replace("model:tSequenceFlow \"", "<sequenceFlow");
			content = content.replace("flowElement xsi:type=\"model:tSequenceFlow\"", "sequenceFlow");
			content = content.replace("model:tTask \" name=\"Declaration\"",
					"<task name=\"Declaration\"");
			content = content.replace("fromxsi:type=\"model:tFormalExpression\"", "from xsi:type=\"model:tFormalExpression\"");
			content = content.replace("toxsi:type=\"model:tFormalExpression\"", "to xsi:type=\"model:tFormalExpression\"");

			content = content.replace("model:process", "process");

			System.out.println("start");
			System.out.println(content);
			System.out.println("end");

			Util.writeStringToFile(outputFile, content);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
