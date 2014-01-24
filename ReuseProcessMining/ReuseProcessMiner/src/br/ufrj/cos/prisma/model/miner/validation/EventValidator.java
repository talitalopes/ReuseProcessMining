/**
 *
 * $Id$
 */
package br.ufrj.cos.prisma.model.miner.validation;

import java.util.Date;

import miner.Activity;

/**
 * A sample validator interface for {@link br.ufrj.cos.prisma.model.miner.Event}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EventValidator {
	boolean validate();

	boolean validateActivity(Activity value);
	boolean validateDate(Date value);
	boolean validateLifecycleStatus(String value);
}
