package br.ufrj.cos.prisma;

import java.util.*;
import org.eclipse.emf.ecore.*;
import net.sf.jetset.runtime.*;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CLASS: DO NOT EDIT! (unless you really want to)
 */
@SuppressWarnings("unused")public class ActivitysOrDiverging$Condition extends PropertyGenerator
{

  protected static String nl;
  public static synchronized ActivitysOrDiverging$Condition create(String lineSeparator)
  {
    nl = lineSeparator;
    ActivitysOrDiverging$Condition result = new ActivitysOrDiverging$Condition();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";

   /**
    * The emitter method.
    * @return The translation text.
    */
   public String generate()
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(getValue("Name").contains("INCLUSIVE_GATEWAY"));
    return stringBuffer.toString();
  }
}
