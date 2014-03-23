package br.ufrj.cos.prisma.miner.Extractor;

import java.io.File;
import java.util.List;

import minerv1.Commit;
import minerv1.FrameworkApplication;
import minerv1.FrameworkProcess;
import minerv1.Minerv1Factory;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.widgets.Shell;

import br.ufrj.cos.prisma.helpers.GitRepositoryHelper;
import br.ufrj.cos.prisma.helpers.ProjectHelper;
import br.ufrj.cos.prisma.helpers.RepositoriesHelper;
import br.ufrj.cos.prisma.miner.util.Constants;
import br.ufrj.cos.prisma.miner.util.Log;
import br.ufrj.cos.prisma.model.GithubRepository;

public class RepositoriesExtractor {

	private static ProjectHelper projectHelper;
	
	public static void start(FrameworkProcess process, Shell shell) {
		if (process == null) {
			Log.i(Constants.ERROR_KEY, Constants.PROCESS_NOT_EXISTS);
			return;
		}

		if (process.getName() == null) {
			Log.i(Constants.ERROR_KEY, Constants.ERROR_LOADING_FRAMEWORK);
			return;
		}

		System.out.println(String.format(
				"Extracting reuse actions related to %s framework",
				process.getName()));

		manageFrameworkApplications(process);
		
	}
	
	private static void manageFrameworkApplications(FrameworkProcess process) {
		List<GithubRepository> repositories = listRepositories();

		System.out.println(String.format("%d repositories found",
				repositories.size()));

		// A repository corresponds to a framework application (process
		// instance)
		for (GithubRepository repo : repositories) {
			FrameworkApplication app = Minerv1Factory.eINSTANCE.createFrameworkApplication();
			app.setName(repo.getName());
			app.setRepositoryUrl(repo.getUrl());
			process.getApplications().add(app);

			GitRepositoryHelper helper = new GitRepositoryHelper(repo);
			List<RevCommit> applications = helper.getCommitsHistory();
			projectHelper = new ProjectHelper();

			System.out.println(String.format("%d commits found for application %s",
					applications.size(), repo.getName()));
			
			for (RevCommit c : applications) {
				Commit commit = Minerv1Factory.eINSTANCE.createCommit();
				commit.setName(c.getName());
				commit.setId(c.getId().getName());
				app.getCommits().add(commit);
				
				helper.cloneFromCommit(c);
				
				System.out.println("Importing projects into folder: " + repo.getRepoFile());
				importProjectIntoWorkspace(repo.getRepoFile());
				
				exploreProjectsInWorkspace();
				
				System.out.println("Deleting projects from workspace");
				deleteApplicationProjectsFromWorkspace();
			}
			
//				MinerCommit mCommit = new MinerCommit(c.getId().getName(),
//						c.getCommitTime());
//
//				System.out.println("Cloning repo in " + repo.getLocalDir());
//				helper.cloneFromCommit(c);
//				
//			System.out.println("Importing projects into folder: " + repo.getRepoFile());
//			importProjectIntoWorkspace(repo.getRepoFile());
//				
//				List<IProject> projects = new ArrayList<IProject>();
//				IProject[] projectsArray = jdtHelper.getAllProjectsInWorkspace();
//				for (int i = 0; i < projectsArray.length; i++) {
//					if (projectsArray[i].getName().toLowerCase().contains("miner")) {
//						continue;
//					}
//					projects.add(projectsArray[i]);
//				}
//				app.setWorkspaceProjects(projects);
//
//				System.out.println("Mining RA");
//				mineReuseActionsFromCommit(mCommit);
//				
//				System.out.println("Deleting projects from workspace");
//				deleteApplicationProjectsFromWorkspace();
//				
//				app.addCommit(mCommit);
//				mProcess.addApplication(app);
//			}
		}
	}
	
	/**
	 * This method imports project inside a given folder to the workspace.
	 * 
	 * @param localDir
	 *            the location of the projects
	 * **/
	private static void importProjectIntoWorkspace(File repoDir) {
		projectHelper.importAllProjectsInsideRepoFolder(repoDir);
	}

	/**
	 * This method removes a project from the workspace. 
	 * @param ?
	 * **/
	private static void deleteApplicationProjectsFromWorkspace() {
		projectHelper.deleteProjectsFromWorkspace();
	}

	private static List<GithubRepository> listRepositories() {
		return RepositoriesHelper.listRepositories("JJTV5_gef");
	}

	private static void exploreProjectsInWorkspace() {
		
	}
	
}
