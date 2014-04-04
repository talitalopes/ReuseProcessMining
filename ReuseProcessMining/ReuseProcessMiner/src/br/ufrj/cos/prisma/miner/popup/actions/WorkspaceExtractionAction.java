package br.ufrj.cos.prisma.miner.popup.actions;

import org.eclipse.jface.action.IAction;

public class WorkspaceExtractionAction extends BaseAction {

	public WorkspaceExtractionAction() {
		super();
	}

	@Override
	public void run(IAction action) {
		super.run(action);
		extractReuseActionsFromWorkspaceProjects();
	}

	// TODO: implement
	private void extractReuseActionsFromWorkspaceProjects() {
		
	}
}
