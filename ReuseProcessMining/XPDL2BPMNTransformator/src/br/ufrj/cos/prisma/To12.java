 

package br.ufrj.cos.prisma;

import net.sf.jetset.runtime.generator.NodeGenerator;
import net.sf.jetset.runtime.generator.TextNodeGenerator;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CODE: DO NOT EDIT! (unless you really want to)
 */
public class To12 extends TextNodeGenerator{

	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.TextNodeGenerator#getTextGenerator()
    */
   protected PropertyGenerator getTextGenerator(){
      return new br.ufrj.cos.prisma.To12$Text();
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodeName()
    */
   protected String getNodeName(){
      return "to";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodePath()
    */
   protected String getNodePath(){
      return "Document Root -> WorkflowProcess -> WorkflowProcess -> process -> Activities -> Activitys [Method Extension] -> task -> dataInputAssociation -> assignment -> to -> to";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getChildNodeGenerators()
    */
   protected NodeGenerator[] getChildNodeGenerators(){
      return new NodeGenerator[0];
   }

}
