package br.ufrj.cos.prisma;

import java.util.*;
import org.eclipse.emf.ecore.*;
import net.sf.jetset.runtime.*;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CLASS: DO NOT EDIT! (unless you really want to)
 */
@SuppressWarnings("unused")public class DataInputAssociation5$TargetRef extends PropertyGenerator
{

  protected static String nl;
  public static synchronized DataInputAssociation5$TargetRef create(String lineSeparator)
  {
    nl = lineSeparator;
    DataInputAssociation5$TargetRef result = new DataInputAssociation5$TargetRef();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "_";
  protected final String TEXT_2 = "SUB_NAMEInput";

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
