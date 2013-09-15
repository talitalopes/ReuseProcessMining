package br.ufrj.cos.prisma.xml;

import net.sf.jetset.runtime.RuntimeLog;
import net.sf.jetset.runtime.generator.ProjectNodeGenerator;

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
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			usage();
			return;
		}
		try {
			// Preprocess XPDL input
			// XPDLPreprocessor.format(args[1], args[1]);
			
			RuntimeLog.logInfo("XPDL2BPMNTransformator");
			RuntimeLog.logInfo("   Meta Model : " + args[0]);
			RuntimeLog.logInfo("   Model      : " + args[1]);
			RuntimeLog.logInfo("   Output Dir : " + args[2]);
			br.ufrj.cos.prisma.XPDL2BPMNTransformator instance = new br.ufrj.cos.prisma.XPDL2BPMNTransformator();
			instance.run(args[0], args[1], args[2]);
			
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
		ProjectNodeGenerator projectNodeGen = new br.ufrj.cos.prisma.DocumentRoot();
		projectNodeGen.generate(metaModelUri, modelUri, outputDirectory);
	}

}