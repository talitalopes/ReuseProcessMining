package br.ufrj.cos.prisma;

import java.util.*;
import org.eclipse.emf.ecore.*;
import net.sf.jetset.runtime.*;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CLASS: DO NOT EDIT! (unless you really want to)
 */
@SuppressWarnings("unused")public class DataInputRefs8$Text extends PropertyGenerator
{

  protected static String nl;
  public static synchronized DataInputRefs8$Text create(String lineSeparator)
  {
    nl = lineSeparator;
    DataInputRefs8$Text result = new DataInputRefs8$Text();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<dataInputRefs>_";
  protected final String TEXT_2 = "VAR_NAMEInput</dataInputRefs>";

   /**
    * The emitter method.
    * @return The translation text.
    */
   public String generate()
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(getValue("Id")+1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
