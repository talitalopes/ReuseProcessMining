 

package br.ufrj.cos.prisma;

import net.sf.jetset.runtime.generator.IterationNodeGenerator;
import net.sf.jetset.runtime.generator.NodeGenerator;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CODE: DO NOT EDIT! (unless you really want to)
 */
public class Transitions2 extends IterationNodeGenerator{

	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.IterationNodeGenerator#getIterationreference()
    */
   protected String getIterationReference(){
      return "Transition (xpdl2:Transition_._type)";
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.IterationNodeGenerator#getConditionGenerator()
    */
   protected PropertyGenerator getConditionGenerator(){
      return null;      
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.IterationNodeGenerator#getPreIterationGenerator()
    */
   protected PropertyGenerator getPreIterationGenerator(){
      return null;      
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.IterationNodeGenerator#getPostIterationGenerator()
    */
   protected PropertyGenerator getPostIterationGenerator(){
      return null;      
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.IterationNodeGenerator#getNoIterationGenerator()
    */
   protected PropertyGenerator getNoIterationGenerator(){
      return null;      
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.IterationNodeGenerator#getSortCriteriaGenerator()
    */
   protected PropertyGenerator getSortCriteriaGenerator(){
      return null;            
   }
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.IterationNodeGenerator#sortDescending()
    */
   protected boolean sortDescending(){
      return false;            
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodeName()
    */
   protected String getNodeName(){
      return "Transitions";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodePath()
    */
   protected String getNodePath(){
      return "Document Root -> WorkflowProcess -> WorkflowProcess -> process -> Transitions -> Transitions";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getChildNodeGenerators()
    */
   protected NodeGenerator[] getChildNodeGenerators(){
	
      NodeGenerator[] childGenerators = new NodeGenerator[1];
      childGenerators[0] = new br.ufrj.cos.prisma.FlowElement3();
      return childGenerators;
      
   }
   
}
