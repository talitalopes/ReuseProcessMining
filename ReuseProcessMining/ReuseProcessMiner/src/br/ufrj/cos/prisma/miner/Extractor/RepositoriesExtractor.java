package br.ufrj.cos.prisma.miner.Extractor;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import minerv1.Activity;
import minerv1.Commit;
import minerv1.FrameworkApplication;
import minerv1.FrameworkProcess;
import minerv1.Minerv1Factory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import br.ufrj.cos.prisma.helpers.GitRepositoryHelper;
import br.ufrj.cos.prisma.helpers.ProjectHelper;
import br.ufrj.cos.prisma.helpers.RepositoriesHelper;
import br.ufrj.cos.prisma.miner.Extractor.model.JDTHelper;
import br.ufrj.cos.prisma.miner.util.Constants;
import br.ufrj.cos.prisma.miner.util.CustomSearchRequestor;
import br.ufrj.cos.prisma.miner.util.Log;
import br.ufrj.cos.prisma.model.GithubRepository;

public class RepositoriesExtractor {

	private ProjectHelper projectHelper;
	private FrameworkProcess process;
	private JDTHelper jdtHelper;
	private Set<String> processActivities;

	private static RepositoriesExtractor instance;

	private static void instantiate(FrameworkProcess process) {
		instance = new RepositoriesExtractor(process);
	}

	private RepositoriesExtractor(FrameworkProcess process) {
		this.process = process;
		this.jdtHelper = new JDTHelper(process.getName());
		this.projectHelper = new ProjectHelper();
		this.processActivities = new HashSet<String>();
	}

	public static RepositoriesExtractor getInstance() {
		return instance;
	}

	public static void start(FrameworkProcess process, Shell shell) {
		RepositoriesExtractor.instantiate(process);

		if (process == null) {
			Log.i(Constants.ERROR_KEY, Constants.PROCESS_NOT_EXISTS);
			return;
		}

		if (process.getName() == null) {
			Log.i(Constants.ERROR_KEY, Constants.ERROR_LOADING_FRAMEWORK);
			return;
		}

		RepositoriesExtractor.log(String.format(
				"Extracting reuse actions related to %s framework",
				process.getName()));

		RepositoriesExtractor.getInstance().manageFrameworkApplications();

	}

	private void manageFrameworkApplications() {
		List<GithubRepository> repositories = listRepositories();

		log(String.format("%d repositories found",
				repositories.size()));

		// A repository corresponds to a framework application (process
		// instance)
		for (GithubRepository repo : repositories) {
			FrameworkApplication app = Minerv1Factory.eINSTANCE
					.createFrameworkApplication();
			app.setName(repo.getName());
			app.setRepositoryUrl(repo.getUrl());
			process.getApplications().add(app);

			GitRepositoryHelper helper = new GitRepositoryHelper(repo);
			List<RevCommit> applications = helper.getCommitsHistory();

			log(String.format(
					"%d commits found for application %s", applications.size(),
					repo.getName()));

			for (RevCommit c : applications) {
				Commit commit = Minerv1Factory.eINSTANCE.createCommit();
				commit.setName(c.getName());
				commit.setId(c.getId().getName());
				app.getCommits().add(commit);

				helper.cloneFromCommit(c);

				log("Importing projects into folder: "
						+ repo.getRepoFile());
				importProjectIntoWorkspace(repo.getRepoFile());

				exploreProjectsInWorkspace(process);

				log("Deleting projects from workspace");
				deleteApplicationProjectsFromWorkspace();
			}

			// MinerCommit mCommit = new MinerCommit(c.getId().getName(),
			// c.getCommitTime());
			//
			// System.out.println("Cloning repo in " + repo.getLocalDir());
			// helper.cloneFromCommit(c);
			//
			// System.out.println("Importing projects into folder: " +
			// repo.getRepoFile());
			// importProjectIntoWorkspace(repo.getRepoFile());
			//
			// List<IProject> projects = new ArrayList<IProject>();
			// IProject[] projectsArray = jdtHelper.getAllProjectsInWorkspace();
			// for (int i = 0; i < projectsArray.length; i++) {
			// if (projectsArray[i].getName().toLowerCase().contains("miner")) {
			// continue;
			// }
			// projects.add(projectsArray[i]);
			// }
			// app.setWorkspaceProjects(projects);
			//
			// System.out.println("Mining RA");
			// mineReuseActionsFromCommit(mCommit);
			//
			// System.out.println("Deleting projects from workspace");
			// deleteApplicationProjectsFromWorkspace();
			//
			// app.addCommit(mCommit);
			// mProcess.addApplication(app);
			// }
		}
	}

	/**
	 * This method imports project inside a given folder to the workspace.
	 * 
	 * @param localDir
	 *            the location of the projects
	 * **/
	private void importProjectIntoWorkspace(File repoDir) {
		this.projectHelper.importAllProjectsInsideRepoFolder(repoDir);
	}

	/**
	 * This method removes a project from the workspace.
	 * 
	 * @param ?
	 * **/
	private void deleteApplicationProjectsFromWorkspace() {
		this.projectHelper.deleteProjectsFromWorkspace();
	}

	private List<GithubRepository> listRepositories() {
		return RepositoriesHelper.listRepositories("JJTV5_gef");
	}

	private void exploreProjectsInWorkspace(FrameworkProcess process) {
		JDTHelper jdtHelper = new JDTHelper(process.getName());
		IProject[] projects = jdtHelper.getAllProjectsInWorkspace();

		for (int i = 0; i < projects.length; i++) {
			if (projects[i].getName().toLowerCase().equals("miner")
					|| projects[i].getName().toLowerCase().contains("eclipse")) {
				continue;
			}

			log(">>>>>Project:  " + projects[i].getName());
			exploreProject(projects[i]);
		}
	}

	private void exploreProject(IProject project) {
		IJavaProject javaProject = openProject(project);
		if (javaProject == null) {
			return;
		}
		
		jdtHelper.setCurrentFrameworkApplicationProject(javaProject);
		IPackageFragment[] packages = null;
		try {
			packages = javaProject.getPackageFragments();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		if (packages == null) {
			return;
		}

		for (IPackageFragment mPackage : packages) {
			explorePackage(mPackage);
		}

	}

	private void explorePackage(IPackageFragment p) {
		try {
			if (p.getKind() != IPackageFragmentRoot.K_SOURCE) {
				return;
			}
		} catch (JavaModelException e1) {
			e1.printStackTrace();
			return;
		}

		ICompilationUnit[] units = null;
		try {
			units = p.getCompilationUnits();
		} catch (JavaModelException e) {
			e.printStackTrace();
			return;
		}

		try {
			for (ICompilationUnit unit : units) {

				for (IType type : unit.getAllTypes()) {
					if (type.isClass()) {
						extractClassesAndMethods(type);
					} else if (type.isInterface()) {
						continue; // TODO: get interfaces
					}
				}

			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	private void extractClassesAndMethods(IType type) {
		// Check is superclass belongs to framework.
		IType superClassFW;
		try {
			superClassFW = JDTHelper.isFrameworkClass(
					this.jdtHelper.getFrameworkProject(), type);
			if (superClassFW == null) {
				return;
			}
			
			if (!this.processActivities.contains(superClassFW.getFullyQualifiedName())) {
				this.processActivities.add(superClassFW.getFullyQualifiedName());
				Activity a = Minerv1Factory.eINSTANCE.createActivity();
				a.setName(superClassFW.getFullyQualifiedName());
				this.process.getActivities().add(a);
			}
			
			SearchMatch mMatch = callHierarchy(
					jdtHelper.getCurrentFrameworkApplicationProject(),
					type.getElementName());
			if (mMatch != null) {
				IType dependentIType = jdtHelper
						.getCurrentFrameworkApplicationProject().findType(
								mMatch.getResource().getName());
				extractClassesAndMethods(dependentIType);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	private static SearchMatch callHierarchy(IJavaProject javaProject,
			String searchKeyword) throws CoreException {
		SearchPattern pattern = SearchPattern.createPattern(searchKeyword,
				IJavaSearchConstants.CLASS, IJavaSearchConstants.REFERENCES,
				SearchPattern.R_CASE_SENSITIVE);

		if (pattern == null) {
			return null;
		}

		boolean includeReferencedProjects = false;
		IJavaElement[] javaProjects = new IJavaElement[] { javaProject };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(
				javaProjects, includeReferencedProjects); // <--- ????

		SearchRequestor requestor = CustomSearchRequestor.getInstance();

		SearchEngine searchEngine = new SearchEngine();
		SearchParticipant[] searchParticipant = new SearchParticipant[] { SearchEngine
				.getDefaultSearchParticipant() };

		try {
			searchEngine.search(pattern, searchParticipant, scope, requestor,
					null);
		} catch (Exception e) {
			return null;
		}

		return ((CustomSearchRequestor) requestor).getMatch();
	}
	
	private IJavaProject openProject(IProject project) {
		try {
			project.open(null);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}

		IJavaProject javaProject = JavaCore.create(project);
		if (!JDTHelper.isJavaProjectValid(javaProject)) {
			return null;
		}
		return javaProject;
	}

	private static MessageConsole findConsole() {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++) {
			if (Constants.CONSOLE_NAME.equals(existing[i].getName())) {
				return (MessageConsole) existing[i];
			}
		}
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(Constants.CONSOLE_NAME, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}

	private static void log(String message) {
		MessageConsole myConsole = findConsole();
		MessageConsoleStream out = myConsole.newMessageStream();
		out.println(message);
	}
}
