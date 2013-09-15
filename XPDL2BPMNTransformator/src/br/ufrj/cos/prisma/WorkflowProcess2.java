 

package br.ufrj.cos.prisma;

import net.sf.jetset.runtime.generator.NodeGenerator;
import net.sf.jetset.runtime.generator.FileNodeGenerator;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CODE: DO NOT EDIT! (unless you really want to)
 */
public class WorkflowProcess2 extends FileNodeGenerator{

   
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.FileNodeGenerator#getFileNameGenerator()
    */
   protected PropertyGenerator getFileNameGenerator(){
      return new br.ufrj.cos.prisma.WorkflowProcess2$FileName();
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.FileNodeGenerator#overwriteExisting()
    */
   protected boolean overwriteExisting(){
      return true;
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodeName()
    */
   protected String getNodeName(){
      return "WorkflowProcess";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodePath()
    */
   protected String getNodePath(){
      return "Document Root -> WorkflowProcess -> WorkflowProcess";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getChildNodeGenerators()
    */
   protected NodeGenerator[] getChildNodeGenerators(){
	
      NodeGenerator[] childGenerators = new NodeGenerator[1];
      childGenerators[0] = new br.ufrj.cos.prisma.Process();
      return childGenerators;
      
   }

}
