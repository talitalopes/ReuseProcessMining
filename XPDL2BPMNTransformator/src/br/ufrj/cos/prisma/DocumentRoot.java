  


package br.ufrj.cos.prisma;

import net.sf.jetset.runtime.generator.NodeGenerator;
import net.sf.jetset.runtime.generator.ProjectNodeGenerator;

/**
 * This is the main class of the generated generator.
 * It contains a main function so that it can be called
 * from a command line console.
 * @see br.ufrj.cos.prisma.DocumentRoot#main
 *
 */
public class DocumentRoot extends ProjectNodeGenerator{


   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getSourceRootClass()
    */
   protected String getSourceRootClass(){
      return "xpdl2:PackageType";
   }
    
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodeName()
    */
   protected String getNodeName(){
      return "DocumentRoot";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodePath()
    */
   protected String getNodePath(){
      return "DocumentRoot";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getChildNodeGenerators()
    */
   protected NodeGenerator[] getChildNodeGenerators(){	
      NodeGenerator[] childGenerators = new NodeGenerator[1];
      childGenerators[0] = new br.ufrj.cos.prisma.WorkflowProcesses();
      return childGenerators;
      
   }

} 