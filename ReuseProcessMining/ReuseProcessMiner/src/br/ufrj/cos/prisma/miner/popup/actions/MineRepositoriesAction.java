package br.ufrj.cos.prisma.miner.popup.actions;

import java.util.List;

import minerv1.Commit;
import minerv1.FrameworkApplication;
import minerv1.Minerv1Factory;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import br.ufrj.cos.prisma.helpers.GitRepositoryHelper;
import br.ufrj.cos.prisma.helpers.LogHelper;
import br.ufrj.cos.prisma.thread.ExploreCommitTask;
import br.ufrj.cos.prisma.thread.TaskListener;

public class MineRepositoriesAction extends BaseExtractionAction {
	int currentIndex;
	boolean wait;

	public MineRepositoriesAction() {
		super();
		wait = true;
	}

	@Override
	public void run(IAction action) {
		super.run(action);
		mineReuseActionsFromRepositories();
		// save();
	}

	private void mineReuseActionsFromRepositories() {
		for (FrameworkApplication app : process.getApplications()) {
			currentIndex = 0;
			if (!app.mine()) {
				continue;
			}
			final GitRepositoryHelper helper = GitRepositoryHelper
					.getInstanceForApplication(app);

			List<String> applications = helper.getCommitsHistoryFromMaster();

			LogHelper.log(String.format("%d commits found for application %s",
					applications.size(), app.getName()));

			while (currentIndex < applications.size()) {
				String currentCommitId = applications.get(currentIndex);
				this.currentCommit = createCommit(currentCommitId);

				ExploreCommitTask exploreTask = new ExploreCommitTask(app,
						currentCommitId);
				exploreTask.addListener(onTaskCompletedListener(app));

				// and now get the workbench to do the work
				final IWorkbench workbench = PlatformUI.getWorkbench();
				workbench.getDisplay().syncExec(exploreTask);

				while (wait) {
				}

				System.out.println("Preparing for next commit");
				this.currentIndex++;
			}

			helper.deleteParentFolder();
			System.out.println("Finishing FrameworkApplication "
					+ app.getName());

			// LogHelper.log("Start exploring commits");
			// for (String commitId : applications) {
			// LogHelper.log("Current commit: " + commitId);
			// Commit commit = Minerv1Factory.eINSTANCE.createCommit();
			// commit.setName(commitId);
			// commit.setId(commitId);
			// this.currentCommit = commit;
			// app.getCommits().add(this.currentCommit);
			//
			// helper.cloneFromCommitId(commitId);
			//
			// importProjectIntoWorkspace(helper.getRepoFile());
			//
			// try {
			// exploreProjectsInWorkspace(process);
			// } catch (JavaModelException e) {
			// LogHelper.log("Error: JavaModelException");
			// }

			// LogHelper.log("Deleting projects from workspace");
			// deleteApplicationProjectsFromWorkspace();

			// }
			// LogHelper.log("Finish exploring commits");
			// app.setMine(false);
		}
	}

	private TaskListener onTaskCompletedListener(final FrameworkApplication app) {
		return new TaskListener() {
			@Override
			public void threadComplete(Runnable runner,
					final List<IProject> projects) {
				exploreProjects(projects);
				
				app.getCommits().add(currentCommit);
				deleteProjectsFromWorkspace(projects);
				wait = false;

				System.out.println("Finishing task");
				save();
			}
		};
	}

	private void exploreProjects(List<IProject> projects) {
		for (IProject project : projects) {
			System.out.println("Exploring Project: " + project.getName());
			if (project.getName().toLowerCase().equals("miner")
					|| project.getName().toLowerCase()
							.equals(this.process.getName().toLowerCase())) {
				continue;
			}

			try {
				exploreProject(project);
			} catch (JavaModelException e) {
				LogHelper.log("JavaModelException", e.getMessage());
			}
		}
	}

	private Commit createCommit(String id) {
		Commit commit = Minerv1Factory.eINSTANCE.createCommit();
		commit.setName(id);
		commit.setId(id);
		return commit;
	}

	/**
	 * This method imports project inside a given folder to the workspace.
	 * 
	 * @param localDir
	 *            the location of the projects
	 * **/
	// private void importProjectIntoWorkspace(File repoDir) {
	// LogHelper.log("Importing projects into workspace from commit: " +
	// this.currentCommit.getId());
	// this.projectHelper.findProjectsInRepositoryFolder(repoDir);
	// this.projectHelper.testTaskListener(repoDir);
	// }

}