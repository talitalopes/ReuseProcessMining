package br.ufrj.cos.prisma.xml;

import net.sf.jetset.runtime.RuntimeLog;
import net.sf.jetset.runtime.generator.ProjectNodeGenerator;
import br.ufrj.cos.prisma.DocumentRoot;

/**
 * This is the main class of the generated generator. It contains a main
 * function so that it can be called from a command line console.
 * 
 * @see br.ufrj.cos.prisma.XPDL2BPMNTransformator#main
 * 
 */
public class XPDL2BPMNTransformator {

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            meta model path, model path and output directory path.
	 *            
	 *           Params Example: metamodel/xpdl2-2.xsd metamodel/gef-prom.xpdl metamodel
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			usage();
			return;
		}
		
		try {
			// Preprocess XPDL input
			// metamodel/gef-prom.xpdl.xml
			String fileFormat = ".xml";
			String filename = String.format("%s%s", args[1], fileFormat);
			String editedFilename = String.format("%s-edit%s", args[1], fileFormat);
			
			XPDLPreprocessor.format(filename, editedFilename);
			
			RuntimeLog.logInfo("XPDL2BPMNTransformator");
			RuntimeLog.logInfo("   Meta Model : " + args[0]);
			RuntimeLog.logInfo("   Model      : " + editedFilename);
			RuntimeLog.logInfo("   Output Dir : " + args[2]);
			XPDL2BPMNTransformator instance = new XPDL2BPMNTransformator();
			instance.run(args[0], editedFilename, args[2]);
			
			// Process RDL BPMN
			RDLbpmnFormatter.format("metamodel/rdl.xml", "metamodel/rdl.xml");
			
		} catch (Exception e) {
			RuntimeLog.logError("");
			RuntimeLog
					.logError("An error occurred during model transformation:");
			RuntimeLog
					.logError("-------------------------------------------------------------");
			e.printStackTrace();
			RuntimeLog
					.logError("-------------------------------------------------------------");
			return;
		}
	}

	/**
	 * Displays usage info
	 */
	private static void usage() {
		System.out
				.println("Usage: java.exe XPDL2BPMNTransformator metaModelURI modelURI outputDirectoryURI");
	}

	public void run(String metaModelUri, String modelUri, String outputDirectory) {
		ProjectNodeGenerator projectNodeGen = new DocumentRoot();
		projectNodeGen.generate(metaModelUri, modelUri, outputDirectory);
	}

}