 

package br.ufrj.cos.prisma;

import net.sf.jetset.runtime.generator.NodeGenerator;
import net.sf.jetset.runtime.generator.TextNodeGenerator;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CODE: DO NOT EDIT! (unless you really want to)
 */
public class TargetRef12 extends TextNodeGenerator{

	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.TextNodeGenerator#getTextGenerator()
    */
   protected PropertyGenerator getTextGenerator(){
      return new br.ufrj.cos.prisma.TargetRef12$Text();
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodeName()
    */
   protected String getNodeName(){
      return "targetRef";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodePath()
    */
   protected String getNodePath(){
      return "DocumentRoot -> WorkflowProcess -> WorkflowProcess -> process -> Activities -> Activitys [Export Model] -> task <exportModel> -> dataInputAssociation -> targetRef";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getChildNodeGenerators()
    */
   protected NodeGenerator[] getChildNodeGenerators(){
      return new NodeGenerator[0];
   }

}
