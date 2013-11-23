package br.ufrj.cos.prisma;

import java.util.*;
import org.eclipse.emf.ecore.*;
import net.sf.jetset.runtime.*;
import net.sf.jetset.runtime.generator.PropertyGenerator;

/**
 * GENERATED CLASS: DO NOT EDIT! (unless you really want to)
 */
@SuppressWarnings("unused")public class ActivitysArtificialEnd$Condition extends PropertyGenerator
{

  protected static String nl;
  public static synchronized ActivitysArtificialEnd$Condition create(String lineSeparator)
  {
    nl = lineSeparator;
    ActivitysArtificialEnd$Condition result = new ActivitysArtificialEnd$Condition();
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
    stringBuffer.append(getValue("Name").contains("Artificial End"));
    return stringBuffer.toString();
  }
}
