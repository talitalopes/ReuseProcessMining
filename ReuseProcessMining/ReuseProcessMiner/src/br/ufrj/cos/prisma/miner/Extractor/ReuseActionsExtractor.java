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

		try {
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

			// Add process instances to the process
			ArrayList<ProcessInstance> applications = getFrameworkApplicationsCommits();
			process.getInstances().addAll(applications);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<ProcessInstance> getFrameworkApplicationsCommits() {
		HashMap<String, ProcessInstance> applicationsMap = new HashMap<String, ProcessInstance>();
		ArrayList<ProcessInstance> applications = new ArrayList<ProcessInstance>();

		// Get the root of the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();

		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		try {
			// Loop over all projects
			for (IProject project : projects) {

				project.open(null);
				if (!project.isNatureEnabled(Constants.JAVA_NATURE)
						|| (project.getName().equals(frameworkProject
								.getElementName())))
					continue;

				IJavaProject javaProject = JavaCore.create(project);

				if (javaProject.getElementName().split("\\.").length > 0) {
					String[] keys = javaProject.getElementName().split("\\.");
					String name = "";
					for (int i = 0; i < keys.length - 2; i++) {
						name = name + "." + keys[i];
					}
					String applicationName = name.replaceFirst("\\.", "");

					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date commitDate = null;
					try {
						commitDate = formatter.parse(keys[keys.length - 2]);
					} catch (ParseException e) {
						commitDate = Calendar.getInstance().getTime();
					}

					Commit c = MinerFactory.eINSTANCE.createCommit();
					c.eSet(MinerPackage.eINSTANCE.getCommit_Id(),
							keys[keys.length - 1]);
					c.eSet(MinerPackage.eINSTANCE.getCommit_Date(), commitDate);

					// Add events already mined in ther right positions
					for (String eventName : eventsOrder) {
						Activity activity = processActivities.get(eventName);

						if (activity == null) {
							System.out.println("No activity for: " + eventName);
							continue;
						}

						Event e = MinerFactory.eINSTANCE.createEvent();
						e.setActivity(activity);
						e.setDate(c.getDate());
						e.setLifecycleStatus("COMPLETE");
						c.getEvents().add(e);
						System.out.println("pos: " + activity.getName());
					}

					ProcessInstance application = null;
					if (applicationsMap.containsKey(applicationName)) {
						application = applicationsMap.get(applicationName);
					} else {
						application = MinerFactory.eINSTANCE
								.createProcessInstance();
						application.eSet(
								MinerPackage.eINSTANCE.getProcess_Name(),
								applicationName);
					}

					application.getCommits().add(c);
					applicationsMap.put(applicationName, application);

					// Explore the files in this project
					for (IPackageFragment mPackage : javaProject
							.getPackageFragments()) {
						explorePackage(mPackage, c);
					}
				}
			}

			for (String name : applicationsMap.keySet()) {
				applications.add(applicationsMap.get(name));
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return applications;
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
				if (type.isClass())
					extractClassAndMethods(type, c);
				else if (type.isInterface())
					continue; // TODO: get interfaces
			}
		}
	}

	private static void extractClassAndMethods(IType type, Commit c) {
		try {
			// Check is superclass belongs to framework.
			IType superClassFW = isFrameworkClass(frameworkProject, type);
			if (superClassFW == null) {
				return;
			}

			Activity classExtensionActivity = getClassActivity(superClassFW,
					type);

			if (!eventsOrder.contains(classExtensionActivity.getName())) {
				Event classExtensionEvent = MinerFactory.eINSTANCE
						.createEvent();
				classExtensionEvent.setActivity(classExtensionActivity);
				classExtensionEvent.setDate(c.getDate());
				classExtensionEvent.setLifecycleStatus("COMPLETE");

				c.getEvents().add(classExtensionEvent);
				eventsOrder.add(classExtensionActivity.getName());
			}

			IMethod[] methods = type.getMethods();

			for (IMethod method : methods) {
				boolean isExtension = superClassFW.findMethods(method) != null;
				if (isExtension) {
					Activity methodExtensionActivity = getMethodActivity(
							superClassFW, type, method);
					String methodKey = String.format("%s+%s",
							superClassFW.getElementName(),
							method.getElementName());
					
					if (!eventsOrder.contains(methodKey)) {
						Event methodExtensionEvent = MinerFactory.eINSTANCE
								.createEvent();
						methodExtensionEvent
								.setActivity(methodExtensionActivity);
						methodExtensionEvent.setDate(c.getDate());
						methodExtensionEvent.setLifecycleStatus("COMPLETE");

						c.getEvents().add(methodExtensionEvent);

						eventsOrder.add(methodKey);
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

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

	private static Activity getClassActivity(IType superClassFW, IType type) {
		Activity classExtensionActivity = null;
		String activityKey = superClassFW.getFullyQualifiedName();

		if (!processActivities.containsKey(activityKey)) {
			classExtensionActivity = MinerFactory.eINSTANCE.createActivity();
			classExtensionActivity.setId(activityKey);
			classExtensionActivity.setType(ActivityType.CLASS_EXTENSION);
			classExtensionActivity
					.setName(superClassFW.getFullyQualifiedName());
			classExtensionActivity.setAppClass(type.getElementName());
			// classRA.setPosition(nextPosition);

			mProcess.getActivities().add(classExtensionActivity);
			processActivities.put(activityKey, classExtensionActivity);

		} else {
			classExtensionActivity = processActivities.get(activityKey);
		}

		return classExtensionActivity;
	}

	private static Activity getMethodActivity(IType superClassFW, IType type,
			IMethod method) {
		Activity methodExtensionActivity = null;

		try {
			String methodKey = String.format("%s+%s",
					superClassFW.getElementName(), method.getElementName());
			if (!processActivities.containsKey(methodKey)) {
				methodExtensionActivity = MinerFactory.eINSTANCE
						.createActivity();
				methodExtensionActivity.setId(methodKey);

				if (method.getSource().contains(
						"super." + method.getElementName() + "()")) {
					methodExtensionActivity
							.setType(ActivityType.METHOD_EXTENSION);
				} else {
					methodExtensionActivity
							.setType(ActivityType.OVERRIDES_METHOD);
				}

				methodExtensionActivity.setName(method.getElementName());
				methodExtensionActivity.setAppClass(type.getElementName());

				mProcess.getActivities().add(methodExtensionActivity);
				processActivities.put(methodKey, methodExtensionActivity);

			} else {
				methodExtensionActivity = processActivities.get(methodKey);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return methodExtensionActivity;
	}

	public static IType isFrameworkClass(IJavaProject framework, IType type) {
		try {
			if (type == null || type.getSuperclassName() == null)
				return null;

			if (framework == null)
				return null;

			IType superClass = null;
			if (type.getCompilationUnit() != null) {
				IImportDeclaration[] imports = type.getCompilationUnit()
						.getImports();
				for (int i = 0; i < imports.length; i++) {
					if (imports[i].getElementName() == null)
						continue;

					// TODO: generalize this code!!!
					if (imports[i].getElementName().contains(
							type.getSuperclassName())
							&& imports[i].getElementName().contains("gef")) {
						superClass = framework.findType(imports[i]
								.getElementName());
						return superClass;
					}
				}
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
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