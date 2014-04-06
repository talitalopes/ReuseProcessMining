package br.ufrj.cos.prisma.miner.popup.actions;

import java.io.File;
import java.util.List;

import minerv1.Commit;
import minerv1.FrameworkApplication;
import minerv1.FrameworkProcess;
import minerv1.Minerv1Factory;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jgit.revwalk.RevCommit;

import br.ufrj.cos.prisma.helpers.GitRepositoryHelper;
import br.ufrj.cos.prisma.helpers.LogHelper;

public class MineRepositoriesAction extends BaseExtractionAction {

	public MineRepositoriesAction() {
		super();
	}
	
	@Override
	public void run(IAction action) {
		super.run(action);
		mineReuseActionsFromRepositories();
		save();
	}
	
	private void mineReuseActionsFromRepositories() {
		for (FrameworkApplication app : process.getApplications()) {
			if (!app.mine()) {
				continue;
			}
			
			GitRepositoryHelper helper = getRepositoryHelper(app);

			List<RevCommit> applications = helper.getCompleteCommitsHistory();
			LogHelper.log(String.format("%d commits found for application %s",
					applications.size(), app.getName()));
			
			LogHelper.log("Start exploring commits");
			for (RevCommit c : applications) {
				LogHelper.log("Current commit: " + c.getName());
				Commit commit = Minerv1Factory.eINSTANCE.createCommit();
				commit.setName(c.getName());
				commit.setId(c.getId().getName());
				this.currentCommit = commit;

				helper.cloneFromCommit(c);
				
				LogHelper.log("Importing projects into workspace");
				importProjectIntoWorkspace(helper.getRepoFile());
				
				app.getCommits().add(this.currentCommit);
				LogHelper.log(this.currentCommit.getId());
				try {
					exploreProjectsInWorkspace(process);
				} catch (JavaModelException e) {
					LogHelper.log("Error: JavaModelException");
				}
				
				LogHelper.log("Deleting projects from workspace");
				deleteApplicationProjectsFromWorkspace();
			}
			LogHelper.log("Finish exploring commits");
			app.setMine(false);
		}
	}
	
	private void exploreProjectsInWorkspace(FrameworkProcess process) throws JavaModelException {
		List<IProject> projects = projectHelper.getProjects();
		
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).getName().toLowerCase().equals("miner")
					|| projects.get(i).getName().toLowerCase().equals(this.process.getName().toLowerCase())) {
				continue;
			}

			exploreProject(projects.get(i));
		}		
	}
	
	protected GitRepositoryHelper getRepositoryHelper(FrameworkApplication app) {
		final String REPO_CLONE_LOCAL_DIR = "/users/talitalopes/Documents/Mestrado/github/";
		String repoLocalDir = String.format("%s%s", REPO_CLONE_LOCAL_DIR,
				app.getName());
		File repoFile = new File(repoLocalDir);
		GitRepositoryHelper helper = new GitRepositoryHelper(
				app.getRepositoryUrl(), repoFile);
		return helper;
	}
	
	/**
	 * This method imports project inside a given folder to the workspace.
	 * 
	 * @param localDir
	 *            the location of the projects
	 * **/
	private void importProjectIntoWorkspace(File repoDir) {
		this.projectHelper.findProjectsInRepositoryFolder(repoDir);
	}
	
	protected void deleteApplicationProjectsFromWorkspace(FrameworkApplication app) {
		deleteApplicationProjectsFromWorkspace();
		getRepositoryHelper(app).deleteParentFolder();
	}
	
}