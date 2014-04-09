package br.ufrj.cos.prisma.miner.popup.actions;

import org.eclipse.jface.action.IAction;

public class GenerateXESLogAction extends BaseAction {

	@Override
	public void run(IAction action) {
		super.run(action);
		System.out.println("GenerateXESLogAction new action");
	}
}
