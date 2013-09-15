package br.ufrj.cos.prisma;

import java.util.*;
import org.eclipse.emf.ecore.*;
import net.sf.jetset.runtime.*;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CLASS: DO NOT EDIT! (unless you really want to)
 */
@SuppressWarnings("unused")public class TargetRef8$Text extends PropertyGenerator
{

  protected static String nl;
  public static synchronized TargetRef8$Text create(String lineSeparator)
  {
    nl = lineSeparator;
    TargetRef8$Text result = new TargetRef8$Text();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<targetRef>_";
  protected final String TEXT_2 = "VAR_NAMEInput</targetRef>";

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
