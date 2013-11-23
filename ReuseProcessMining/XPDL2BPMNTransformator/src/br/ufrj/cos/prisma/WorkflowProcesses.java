  


package br.ufrj.cos.prisma;

import net.sf.jetset.runtime.generator.NodeGenerator;
import net.sf.jetset.runtime.generator.ProjectNodeGenerator;

/**
 * This is the main class of the generated generator.
 * It contains a main function so that it can be called
 * from a command line console.
 * @see br.ufrj.cos.prisma.WorkflowProcesses#main
 *
 */
public class WorkflowProcesses extends ProjectNodeGenerator{


   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getSourceRootClass()
    */
   protected String getSourceRootClass(){
      return "xpdl2:WorkflowProcesses_._type";
   }
    
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodeName()
    */
   protected String getNodeName(){
      return "WorkflowProcesses";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodePath()
    */
   protected String getNodePath(){
      return "WorkflowProcesses";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getChildNodeGenerators()
    */
   protected NodeGenerator[] getChildNodeGenerators(){	
      NodeGenerator[] childGenerators = new NodeGenerator[1];
      childGenerators[0] = new br.ufrj.cos.prisma.WorkflowProcess();
      return childGenerators;
      
   }

} 