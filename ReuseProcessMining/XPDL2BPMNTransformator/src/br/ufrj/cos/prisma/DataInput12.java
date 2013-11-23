 

package br.ufrj.cos.prisma;

import java.util.Collection;
import java.util.ArrayList;
import net.sf.jetset.runtime.generator.NodeGenerator;
import net.sf.jetset.runtime.generator.XmlNodeGenerator;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CODE: DO NOT EDIT! (unless you really want to)
 */
public class DataInput12 extends XmlNodeGenerator{

   
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlNodeGenerator#getAttributeGenerators()
    */
   protected Collection<String> getAttributeNames(){
      ArrayList<String> names = new ArrayList<String>();
      names.add("id");
      names.add("name");
      return names;
   }
	

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlNodeGenerator#getAttributeGenerators()
    */
   protected Collection<PropertyGenerator> getAttributeValueGenerators(){
      ArrayList<PropertyGenerator> generators = new ArrayList<PropertyGenerator>();
      generators.add(new br.ufrj.cos.prisma.DataInput12$Id());
      generators.add(new br.ufrj.cos.prisma.DataInput12$Name());
      return generators;
   }
	
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlGenerator#getElementTextGenerator()
    */
   protected PropertyGenerator getElementTextGenerator(){

      return null;
   }    
    

	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlGenerator#getElementName()
    */
   protected String getElementName(){   
      return "dataInput";
   }
	
	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlGenerator#hasChildElements()
    */
   protected boolean hasChildElements(){   
      return false;
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlGenerator#getIndent()
    */
   protected String getIndent(){
      return "         ";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlNodeGenerator#getXmlSchemaLocation()
    */
   protected String getXmlSchemaLocation(){
      return "http://www.omg.org/spec/BPMN/20100524/MODEL bpmn-semantic.xsd";   
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlNodeGenerator#getXmlNamespacePrefix()
    */
   protected String getXmlNamespacePrefix(){
      return "model";   
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlNodeGenerator#getAllXmlNamespaceURIs()
    */
   protected Collection<String> getAllXmlNamespaceURIs(){
      Collection<String> uris = new ArrayList<String>();
      uris.add("http://www.omg.org/spec/BPMN/20100524/MODEL");
      return uris;
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlNodeGenerator#getAllXmlNamespaceURIs()
    */
   protected String getXmlNamespacePrefix(String uri){
      if(uri.equals("http://www.omg.org/spec/BPMN/20100524/MODEL")) return "model";
      return null;
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlGenerator#getElementType()
    */
   protected String getElementType(){
      return null;
   }

	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlGenerator#isElementQualificationRequired()
    */
   protected boolean isElementQualificationRequired(){
      return false;
   }

	
   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.XmlGenerator#getNodeName()
    */
   protected String getNodeName(){
      return "dataInput";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getNodePath()
    */
   protected String getNodePath(){
      return "Document Root -> WorkflowProcess -> WorkflowProcess -> process -> Activities -> Activitys [New package] -> task [new package] -> ioSpecification -> dataInput";
   }

   /* (non-Javadoc)
    * @see net.sf.jetset.runtime.generator.NodeGenerator#getChildNodeGenerators()
    */
   protected NodeGenerator[] getChildNodeGenerators(){
	
      NodeGenerator[] childGenerators = new NodeGenerator[0];
      return childGenerators;
      
   }

}
