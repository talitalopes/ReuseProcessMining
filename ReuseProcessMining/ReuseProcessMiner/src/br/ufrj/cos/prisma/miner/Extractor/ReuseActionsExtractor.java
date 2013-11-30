package br.ufrj.cos.prisma.miner.Extractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.widgets.Shell;

import br.ufrj.cos.prisma.miner.util.Constants;
import br.ufrj.cos.prisma.miner.util.Log;
import br.ufrj.cos.prisma.model.miner.Activity;
import br.ufrj.cos.prisma.model.miner.ActivityType;
import br.ufrj.cos.prisma.model.miner.Commit;
import br.ufrj.cos.prisma.model.miner.Event;
import br.ufrj.cos.prisma.model.miner.MinerFactory;
import br.ufrj.cos.prisma.model.miner.MinerPackage;
import br.ufrj.cos.prisma.model.miner.Process;
import br.ufrj.cos.prisma.model.miner.ProcessInstance;

/**
 * Reuse Actions Extractor. This class is responsible for extracting reuse
 * actions from aplications code. All applications are related to a single
 * framework and the reuse actions corresponds to actions executed to build the
 * application from the framework.
 * **/
public class ReuseActionsExtractor {
	private static IJavaProject frameworkProject;
	private static Process mProcess;
	private static Map<String, Activity> processActivities;
	private static List<String> eventsOrder;

	public static void start(Process process, Shell shell) {
		if (process == null) {
			Log.i(Constants.ERROR_KEY, Constants.PROCESS_NOT_EXISTS);
			return;
		}

		if (process.getName() == null) {
			Log.i(Constants.ERROR_KEY, Constants.ERROR_LOADING_FRAMEWORK);
			return;
		}

		mProcess = process;
		processActivities = new HashMap<String, Activity>();
		eventsOrder = new ArrayList<String>();
		frameworkProject = getFrameworkProject(process.getName());

		if (frameworkProject == null) {
			Log.i(Constants.ERROR_FRAMEWORK_NOT_EXISTS);
			return;
		}

		Log.i(String.format("%s: %s", Constants.FRAMEWORK_PROJECT_NAME_KEY,
				frameworkProject.getElementName()));

		ArrayList<ProcessInstance> applications;
		try {
			// Add process instances to the process
			applications = getFrameworkApplicationsCommits();
			process.getInstances().addAll(applications);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<ProcessInstance> getFrameworkApplicationsCommits()
			throws CoreException {
		HashMap<String, ProcessInstance> applicationsMap = new HashMap<String, ProcessInstance>();
		ArrayList<ProcessInstance> applications = new ArrayList<ProcessInstance>();

		// Get the root of the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();

		// Get all projects in the workspace
		IProject[] projects = root.getProjects();

		// Loop over all projects
		for (IProject project : projects) {
			project.open(null);
			if (!project.isNatureEnabled(Constants.JAVA_NATURE)
					|| (project.getName().equals(frameworkProject
							.getElementName()))) {
				continue;
			}

			IJavaProject javaProject = JavaCore.create(project);
			if (javaProject.getElementName().split("\\.").length == 0) {
				continue;
			}

			String[] keys = javaProject.getElementName().split("\\.");
			String name = "";
			for (int i = 0; i < keys.length - 2; i++) {
				name = name + "." + keys[i];
			}
			String applicationName = name.replaceFirst("\\.", "");

			ProcessInstance application = null;
			if (applicationsMap.containsKey(applicationName)) {
				application = applicationsMap.get(applicationName);
			} else {
				application = MinerFactory.eINSTANCE.createProcessInstance();
				application.eSet(MinerPackage.eINSTANCE.getProcess_Name(),
						applicationName);
			}
			
			Commit c = createCommit(keys);
			application.getCommits().add(c);
			applicationsMap.put(applicationName, application);

			// Explore the files in this project
			for (IPackageFragment mPackage : javaProject.getPackageFragments()) {
				explorePackage(mPackage, c);
			}
		}

		for (String name : applicationsMap.keySet()) {
			applications.add(applicationsMap.get(name));
		}

		return applications;
	}

	private static Commit createCommit(String[] keys) {
		Commit c = MinerFactory.eINSTANCE.createCommit();
		c.eSet(MinerPackage.eINSTANCE.getCommit_Id(), keys[keys.length - 1]);
		c.eSet(MinerPackage.eINSTANCE.getCommit_Date(),
				getCommitDate(keys[keys.length - 2]));
		addExistingEventsToCommit(c);

		return c;
	}

	private static Date getCommitDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date commitDate = null;
		try {
			commitDate = formatter.parse(date);
		} catch (ParseException e) {
			commitDate = Calendar.getInstance().getTime();
		}
		return commitDate;
	}

	private static void addExistingEventsToCommit(Commit c) {
		for (String eventName : eventsOrder) {
			String[] eventNameParts = eventName.split("\\+");
			String eventKey = "";
			if (eventNameParts.length == 2) {
				eventKey = eventNameParts[0];
			} else if (eventNameParts.length == 3) {
				eventKey = String.format("%s+%s", eventNameParts[0], eventNameParts[2]);
			}

			Activity activity = processActivities.get(eventKey);
			if (activity == null) {
				System.out.println("No activity: " + eventName + ": " + eventKey);
				continue;
			}

			Event e = MinerFactory.eINSTANCE.createEvent();
			e.setActivity(activity);
			e.setDate(c.getDate());
			e.setLifecycleStatus("COMPLETE");
			c.getEvents().add(e);
			
			System.out.println("Adding: " + eventName);
		}
		System.out.println("\n");
	}

	private static IJavaProject getFrameworkProject(String name) {
		// Get the root of the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IJavaProject frameworkProject = null;

		try {
			IProject fProject = root.getProject(name);
			if (fProject.exists()) {
				fProject.open(null);
				frameworkProject = JavaCore.create(root.getProject(name));
			}

		} catch (CoreException e) {
			e.printStackTrace();
		}

		return frameworkProject;
	}

	/*
	 * private SearchMatch callHierarchy(IJavaProject javaProject, String
	 * searchKeyword) throws CoreException { SearchPattern pattern =
	 * SearchPattern.createPattern( searchKeyword, IJavaSearchConstants.CLASS,
	 * IJavaSearchConstants.REFERENCES, SearchPattern.R_CASE_SENSITIVE );
	 * 
	 * boolean includeReferencedProjects = false; IJavaElement[] javaProjects =
	 * new IJavaElement[] {javaProject}; IJavaSearchScope scope =
	 * SearchEngine.createJavaSearchScope(javaProjects,
	 * includeReferencedProjects); // <--- ????
	 * 
	 * SearchRequestor requestor = CustomSearchRequestor.getInstance();
	 * 
	 * SearchEngine searchEngine = new SearchEngine(); searchEngine.search(
	 * pattern, new SearchParticipant[] {
	 * SearchEngine.getDefaultSearchParticipant()}, scope, requestor, null);
	 * 
	 * return ((CustomSearchRequestor)requestor).getMatch(); }
	 */

	public static void explorePackage(IPackageFragment p, Commit c)
			throws JavaModelException {
		if (p.getKind() != IPackageFragmentRoot.K_SOURCE)
			return;

		ICompilationUnit[] units = p.getCompilationUnits();

		for (ICompilationUnit unit : units) {
			for (IType type : unit.getAllTypes()) {
				if (type.isClass()) {
					extractClassAndMethods(type, c);
				} else if (type.isInterface()) {
					continue; // TODO: get interfaces
				}
			}
		}
	}

	private static void extractClassAndMethods(IType type, Commit c)
			throws JavaModelException {
		// Check is superclass belongs to framework.
		IType superClassFW = isFrameworkClass(frameworkProject, type);
		if (superClassFW == null) {
			return;
		}

		Activity classExtensionActivity = getClassActivity(superClassFW, type);
		String activityNameKey = String.format("%s+%s",
				superClassFW.getFullyQualifiedName(), type.getElementName());

		if (eventsOrder.contains(activityNameKey)) {
			System.out.println("contains: " + activityNameKey);
			return;
		}

		System.out.println("activityNameKey: "
				+ classExtensionActivity.getName());
		Event classExtensionEvent = MinerFactory.eINSTANCE.createEvent();
		classExtensionEvent.setActivity(classExtensionActivity);
		classExtensionEvent.setDate(c.getDate());
		classExtensionEvent.setLifecycleStatus("COMPLETE");

		c.getEvents().add(classExtensionEvent);
		eventsOrder.add(activityNameKey);
		System.out.println("Adding: " + activityNameKey);

		getClassMethods(superClassFW, type, c);

		/*
		 * try { log(String.format("Type: %s", type.getElementName()));
		 * SearchMatch mMatch = callHierarchy(c.getProject(),
		 * type.getElementName()); if (mMatch == null)
		 * log(String.format("Search: %s", "Not found"));
		 * log(String.format("Search: %s", mMatch.getElement())); } catch
		 * (CoreException e) { e.printStackTrace(); }
		 * 
		 * c.getReuseActions().add(classRA); ArrayList<RAMethod> fwMethods =
		 * classRA.getFrameworkMethods(); for (int m = 0; m < fwMethods.size();
		 * m++) { c.getReuseActions().add(fwMethods.get(m));
		 * 
		 * if (m == fwMethods.size()-1) nextPosition =
		 * fwMethods.get(m).getPosition(); }
		 * 
		 * nextPosition += 1;
		 */
	}

	private static void getClassMethods(IType superClassFW, IType type, Commit c)
			throws JavaModelException {
		IMethod[] methods = type.getMethods();

		for (IMethod method : methods) {
			boolean isExtension = superClassFW.findMethods(method) != null;
			if (!isExtension) {
				continue;
			}
			Activity methodExtensionActivity = getMethodActivity(superClassFW,
					type, method);

			// Super class + method + class name
			String methodKey = String.format("%s+%s+%s",
					superClassFW.getElementName(), type.getElementName(),
					method.getElementName());
			if (eventsOrder.contains(methodKey)) {
				System.out.println("contains: " + methodKey);
				continue;
			}

			Event methodExtensionEvent = MinerFactory.eINSTANCE.createEvent();
			methodExtensionEvent.setActivity(methodExtensionActivity);
			methodExtensionEvent.setDate(c.getDate());
			methodExtensionEvent.setLifecycleStatus("COMPLETE");
			c.getEvents().add(methodExtensionEvent);

			eventsOrder.add(methodKey);
			System.out.println("Adding: " + methodKey);
		}
	}

	private static Activity getClassActivity(IType superClassFW, IType type) {
		Activity classExtensionActivity = null;
		String activityKey = superClassFW.getFullyQualifiedName();

		if (processActivities.containsKey(activityKey)) {
			return processActivities.get(activityKey);
		}

		classExtensionActivity = MinerFactory.eINSTANCE.createActivity();
		classExtensionActivity.setId(activityKey);
		classExtensionActivity.setType(ActivityType.CLASS_EXTENSION);
		classExtensionActivity.setName(superClassFW.getFullyQualifiedName());
		classExtensionActivity.setAppClass(type.getElementName());
		mProcess.getActivities().add(classExtensionActivity);
		processActivities.put(activityKey, classExtensionActivity);

		return classExtensionActivity;
	}

	private static Activity getMethodActivity(IType superClassFW, IType type,
			IMethod method) throws JavaModelException {
		Activity methodExtensionActivity = null;
		String methodKey = String.format("%s+%s",
				superClassFW.getElementName(), method.getElementName());

		if (processActivities.containsKey(methodKey)) {
			return processActivities.get(methodKey);
		}

		methodExtensionActivity = MinerFactory.eINSTANCE.createActivity();
		methodExtensionActivity.setId(methodKey);

		if (method.getSource().contains(
				"super." + method.getElementName() + "()")) {
			methodExtensionActivity.setType(ActivityType.METHOD_EXTENSION);
		} else {
			methodExtensionActivity.setType(ActivityType.OVERRIDES_METHOD);
		}

		methodExtensionActivity.setName(method.getElementName());
		methodExtensionActivity.setAppClass(type.getElementName());
		mProcess.getActivities().add(methodExtensionActivity);
		processActivities.put(methodKey, methodExtensionActivity);

		return methodExtensionActivity;
	}

	public static IType isFrameworkClass(IJavaProject framework, IType type)
			throws JavaModelException {
		if (framework == null) {
			return null;
		}

		if (type == null || type.getSuperclassName() == null) {
			return null;
		}

		IType superClass = null;
		if (type.getCompilationUnit() == null) {
			return null;
		}

		IImportDeclaration[] imports = type.getCompilationUnit().getImports();
		for (int i = 0; i < imports.length; i++) {
			if (imports[i].getElementName() == null) {
				continue;
			}

			// TODO: generalize this code!!!
			if (imports[i].getElementName().contains(type.getSuperclassName())
					&& imports[i].getElementName().contains("gef")) {
				superClass = framework.findType(imports[i].getElementName());
				return superClass;
			}
		}

		return null;
	}

	/*
	 * private static void openDialog(String id, Shell shell) { // First see if
	 * this is a "new wizard". IWizardDescriptor descriptor =
	 * PlatformUI.getWorkbench().getImportWizardRegistry() .findWizard(id); //
	 * If not check if it is an "import wizard". if (descriptor == null) {
	 * descriptor = PlatformUI.getWorkbench().getImportWizardRegistry()
	 * .findWizard(id); } // Or maybe an export wizard if (descriptor == null) {
	 * descriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
	 * .findWizard(id); } try { // Then if we have a wizard, open it. if
	 * (descriptor != null) { IWizard wizard = descriptor.createWizard();
	 * WizardDialog wd = new WizardDialog(shell, wizard);
	 * wd.setTitle(wizard.getWindowTitle()); wd.open(); } } catch (CoreException
	 * e) { e.printStackTrace(); } }
	 */

}