package br.cos.ufrj.prisma;


public class XMLFormatter {
	public static String PROM_XPDL_INPUT = "/Users/talitalopes/Documents/UFRJ/ReuseProcessMining/XPDLFormatter/files/prom.xml";
	public static String FORMATTED_XPDL = "/Users/talitalopes/Documents/UFRJ/ReuseProcessMining/XPDLFormatter/files/prom-edit.xml";
	public static String BPMN_RDL = "/Users/talitalopes/Documents/UFRJ/ReuseProcessMining/XPDLFormatter/files/rdl-bpmn.xml";
	public static String FORMATTED_BPMN_RDL = "/Users/talitalopes/Documents/UFRJ/ReuseProcessMining/XPDLFormatter/files/rdl-bpmn-output.xml";
	
	public static void main(String argv[]) {
		// XPDLPreprocessor.preprocessXPDL(PROM_XPDL_INPUT, FORMATTED_XPDL);
		
		RDLbpmnFormatter.format(BPMN_RDL, FORMATTED_BPMN_RDL);
	}
}
