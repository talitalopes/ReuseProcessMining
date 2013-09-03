  


package br.ufrj.cos.prisma;

import net.sf.jetset.runtime.RuntimeLog;
import net.sf.jetset.runtime.generator.ProjectNodeGenerator;

/**
 * This is the main class of the generated generator.
 * It contains a main function so that it can be called
 * from a command line console.
 * @see br.ufrj.cos.prisma.XPDLToBPMNTransformator#main
 *
 */
public class XPDLToBPMNTransformator {


   /**
    * Program entry point
    * @param args meta model path, model path and output directory path.
    */
   public static void main(String[] args){
      if(args.length!=3){
         usage();
         return;
      }
      try{
         RuntimeLog.logInfo("XPDLToBPMNTransformator");
         RuntimeLog.logInfo("   Meta Model : "+args[0]);
         RuntimeLog.logInfo("   Model      : "+args[1]);
         RuntimeLog.logInfo("   Output Dir : "+args[2]);
         br.ufrj.cos.prisma.XPDLToBPMNTransformator instance = new br.ufrj.cos.prisma.XPDLToBPMNTransformator();
         instance.run(args[0], args[1], args[2]);
      }
      catch(Exception e){
         RuntimeLog.logError("");
         RuntimeLog.logError("An error occurred during model transformation:");
         RuntimeLog.logError("-------------------------------------------------------------");
         e.printStackTrace();
         RuntimeLog.logError("-------------------------------------------------------------");
         return;
      }
   }
		
   /**
    * Displays usage info
    */
   private static void usage(){
      System.out.println("Usage: java.exe XPDLToBPMNTransformator metaModelURI modelURI outputDirectoryURI");
   }
   
   public void run(String metaModelUri, String modelUri, String outputDirectory){
      ProjectNodeGenerator projectNodeGen = new br.ufrj.cos.prisma.DocumentRoot();
      projectNodeGen.generate(metaModelUri, modelUri, outputDirectory);
   }
	

} 