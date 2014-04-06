package br.ufrj.cos.prisma.thread;

import java.io.File;

import minerv1.FrameworkApplication;
import minerv1.FrameworkProcess;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.ui.wizards.JavaCapabilityConfigurationPage;

import br.ufrj.cos.prisma.helpers.GitRepositoryHelper;

public class ExploreCommitTask extends NotificationThread {
	FrameworkProcess process;
	FrameworkApplication app;
	String currentCommit;
	GitRepositoryHelper gitHelper;

	public ExploreCommitTask(FrameworkProcess process,
			FrameworkApplication app, String currentCommit) {
		super();
		
		this.process = process;
		this.app = app;
		this.currentCommit = currentCommit;
		this.gitHelper = GitRepositoryHelper.getInstanceForApplication(app);
	}

	@Override
	public void doWork() {
		// clone repository with current commit's content
		cloneCurrentCommit();

		// find and import projects into workspace
		findProjectsInReporitoryFolder();
		
		exploreProjects();
	}

	private void cloneCurrentCommit() {
		System.out.println("Cloning commit: " + this.currentCommit);
		gitHelper.cloneFromCommit(this.currentCommit);
	}

	private void findProjectsInReporitoryFolder() {
		System.out.println("Finding projects in workspace");
		File[] folderFiles = gitHelper.getRepoFile().listFiles();

		for (int i = 0; i < folderFiles.length; i++) {
			File file = folderFiles[i];
			System.out.println(file.getName());

			if (file.getName().equals(".project")) {
				importProjects(file.getPath());
				continue;
			}

			if (file.isDirectory() && isProjectFolder(file)) {
				String path = file.getPath() + "/.project";
				File f = new File(path);
				importProjects(f.getPath());
			}
		}
	}

	private void importProjects(String projectPath) {
		System.out.println("Importing found projects to workspace");
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();

			IPath projectDotProjectFile = new Path(projectPath);
			IProjectDescription projectDescription = workspace
					.loadProjectDescription(projectDotProjectFile);
			IProject project = workspace.getRoot().getProject(
					projectDescription.getName());
			JavaCapabilityConfigurationPage.createProject(project,
					projectDescription.getLocationURI(), null);

			projects.add(project);
			System.out.println("javaProjects: " + projects.size());

		} catch (CoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Couldn't import project: " + projectPath);
		}
	}

	private void exploreProjects() {
		if (projects.size() == 0) {
			return;
		}
		System.out.println("exploreProjectsInWorkspace: " + process.getName());
	}

//	private void deleteProjectsFromWorkspace() {
//		for (IProject project : projects) {
//			if (!project.exists()) {
//				return;
//			}
//
//			try {
//				System.out.println("deleting: " + project);
//				if (project.getName().toLowerCase().contains("miner")
//						|| project.getName().toLowerCase().contains("gef")) {
//					continue;
//				}
//
//				project.delete(true, null);
//
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
//		projects.clear();
//		projects = null;
//	}

	//--------+
	// Utils  |
	//--------+
	
	public boolean isProjectFolder(File folder) {
		if (folder == null || !folder.isDirectory()) {
			return false;
		}

		File[] folderFiles = folder.listFiles();
		if (folderFiles == null) {
			return false;
		}

		for (int i = 0; i < folderFiles.length; i++) {
			if (folderFiles[i].getName().equals(".project")) {
				return true;
			}
		}
		return false;
	}

//	@Override
//	public void threadComplete(Runnable runner, List<IProject> projects) {
//		exploreProjects();
//		deleteProjectsFromWorkspace();
//		gitHelper.deleteParentFolder();
//	}

}
