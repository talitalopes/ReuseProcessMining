package br.ufrj.cos.prisma.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.ui.wizards.JavaCapabilityConfigurationPage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class ProjectHelper {

	List<IProject> javaProjects;

	public ProjectHelper() {
		javaProjects = new ArrayList<IProject>();
	}
	
	public List<IProject> getProjects() {
		return javaProjects;
	}

	public void findProjectsInRepositoryFolder(File repoFolder) {
		File[] folderFiles = repoFolder.listFiles();
		
		for (int i = 0; i < folderFiles.length; i++) {
			File file = folderFiles[i];
			
			if (file.getName().equals(".project")) {
				System.out.println("Project: " + file.getPath());
				importProjectIntoWorkspaceUsingProjectPath(file.getPath());
				return;
			}
			
//			if (file.isDirectory()) {
//				System.out.println("directory: " + file.getName());
//				findProjectsInRepositoryFolder(file);
//			}
		}
		return;
	}
	
	public void importAllProjectsInsideRepoFolder(File repoFolder) {
//		List<File> firstLevelDirs = listFirstLevelFolders(repoFolder);

//		File[] folderFiles = repoFolder.listFiles();
//		for (int i = 0; i < folderFiles.length; i++) {
//			System.out.println("file: " + folderFiles[i].getAbsolutePath());
//			if (folderFiles[i].getName().equals(".project")) {
//				String projectPath = getProjectFolderPath(folder);
//				return folderFiles[i].getPath();
//			}
//		}
		
//		for (File folder : firstLevelDirs) {
//			System.out.println("folder: " + folder.getAbsolutePath());
//			String projectPath = getProjectFolderPath(folder);
//			if (projectPath == null) {
//				continue;
//			}
//			System.out.println("projectPath: " + projectPath);
//			importProjectIntoWorkspaceUsingProjectPath(projectPath);
//		}
	}

	public void importProjectIntoWorkspaceUsingProjectPath(
			final String projectPath) {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();

		Runnable runnable = new Runnable() {
			public void run() {
				try {
					IPath projectDotProjectFile = new Path(projectPath);
					IProjectDescription projectDescription = workspace
							.loadProjectDescription(projectDotProjectFile);
					IProject project = workspace.getRoot().getProject(
							projectDescription.getName());
					JavaCapabilityConfigurationPage.createProject(project,
							projectDescription.getLocationURI(), null);

					javaProjects.add(project);
					System.out.println("javaProjects: " + javaProjects.size());
					
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("Couldn't import project: " + projectPath);
				}
			}
		};

		// and now get the workbench to do the work
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().syncExec(runnable);
	}

	// public void importProjectIntoWorkspace(String projectName) {
	// IProject project = ResourcesPlugin.getWorkspace().getRoot()
	// .getProject(projectName);
	// try {
	// project.create(null);
	// project.open(null);
	// IJavaProject javaProject = JavaCore.create(project);
	// this.javaProjects.add(e) = (!JDTHelper.isJavaProjectValid(javaProject)) ?
	// null
	// : javaProject;
	// } catch (CoreException e) {
	// System.out.println("Error importing project into workspace");
	// e.printStackTrace();
	// }
	// }

	public void deleteProjectsFromWorkspace() {
		List<IProject> projectsToDelete = new ArrayList<IProject>();
		projectsToDelete.addAll(this.javaProjects);
		this.javaProjects.clear();
		
		for (IProject project : projectsToDelete) {

			if (!project.exists()) {
				return;
			}

			try {
				System.out.println("deleting: " + project.getName());
				if (project.getName().toLowerCase().contains("miner")
						|| project.getName().toLowerCase().contains("gef")) {
					continue;
				}

				project.delete(true, null);
				
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		projectsToDelete.clear();
	}

	private List<File> listFirstLevelFolders(final File folder) {
		List<File> firstLevelFolders = new ArrayList<File>();

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				firstLevelFolders.add(fileEntry);
			}
		}

		return firstLevelFolders;
	}

	private String getProjectFolderPath(File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.getName().equals(".project")) {
				return fileEntry.getPath();
			}
		}
		return null;
	}

}
