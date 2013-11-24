/**
 *
 * $Id$
 */
package br.ufrj.cos.prisma.model.miner.validation;

import br.ufrj.cos.prisma.model.miner.Activity;
import br.ufrj.cos.prisma.model.miner.ProcessInstance;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link br.ufrj.cos.prisma.model.miner.Process}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ProcessValidator {
	boolean validate();

	boolean validateName(String value);
	boolean validateActivities(EList<Activity> value);
	boolean validateInstances(EList<ProcessInstance> value);
}
