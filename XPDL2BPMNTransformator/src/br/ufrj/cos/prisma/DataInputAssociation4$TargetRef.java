package br.ufrj.cos.prisma;

import java.util.*;
import org.eclipse.emf.ecore.*;
import net.sf.jetset.runtime.*;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CLASS: DO NOT EDIT! (unless you really want to)
 */
@SuppressWarnings("unused")public class DataInputAssociation4$TargetRef extends PropertyGenerator
{

  protected static String nl;
  public static synchronized DataInputAssociation4$TargetRef create(String lineSeparator)
  {
    nl = lineSeparator;
    DataInputAssociation4$TargetRef result = new DataInputAssociation4$TargetRef();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "_";
  protected final String TEXT_2 = "SUPER_NAMEInput";

   /**
    * The emitter method.
    * @return The translation text.
    */
   public String generate()
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(getValue("Id"));
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}
