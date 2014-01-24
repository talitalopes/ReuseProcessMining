/**
 *
 * $Id$
 */
package br.ufrj.cos.prisma.model.miner.validation;

import miner.ActivityType;

/**
 * A sample validator interface for {@link br.ufrj.cos.prisma.model.miner.Activity}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ActivityValidator {
	boolean validate();

	boolean validateId(String value);
	boolean validateName(String value);
	boolean validateType(ActivityType value);
	boolean validateAppClass(String value);
}