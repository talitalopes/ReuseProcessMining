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

	public void importAllProjectsInsideRepoFolder(File repoFolder) {
		List<File> firstLevelDirs = listFirstLevelFolders(repoFolder);

		for (File folder : firstLevelDirs) {
			String projectPath = getProjectFolderPath(folder);
			if (projectPath == null) {
				continue;
			}
			importProjectIntoWorkspaceUsingProjectPath(projectPath);
		}
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

				} catch (CoreException e) {
					e.printStackTrace();
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
		for (IProject project : this.javaProjects) {

			if (!project.exists()) {
				return;
			}

			try {
				if (project.getName().toLowerCase().contains("miner")
						|| project.getName().toLowerCase().contains("gef")) {
					continue;
				}

				project.delete(false, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}

		}
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
