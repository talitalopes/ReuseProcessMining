/**
 */
package minerv1.impl;

import minerv1.FrameworkApplication;
import minerv1.FrameworkProcess;
import minerv1.Minerv1Factory;
import minerv1.Minerv1Package;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Minerv1PackageImpl extends EPackageImpl implements Minerv1Package {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass frameworkProcessEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass frameworkApplicationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see minerv1.Minerv1Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private Minerv1PackageImpl() {
		super(eNS_URI, Minerv1Factory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link Minerv1Package#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static Minerv1Package init() {
		if (isInited) return (Minerv1Package)EPackage.Registry.INSTANCE.getEPackage(Minerv1Package.eNS_URI);

		// Obtain or create and register package
		Minerv1PackageImpl theMinerv1Package = (Minerv1PackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof Minerv1PackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new Minerv1PackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMinerv1Package.createPackageContents();

		// Initialize created meta-data
		theMinerv1Package.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMinerv1Package.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(Minerv1Package.eNS_URI, theMinerv1Package);
		return theMinerv1Package;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFrameworkProcess() {
		return frameworkProcessEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFrameworkProcess_Name() {
		return (EAttribute)frameworkProcessEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFrameworkApplication() {
		return frameworkApplicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFrameworkApplication_Name() {
		return (EAttribute)frameworkApplicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFrameworkApplication_RepositoryUrl() {
		return (EAttribute)frameworkApplicationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Minerv1Factory getMinerv1Factory() {
		return (Minerv1Factory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		frameworkProcessEClass = createEClass(FRAMEWORK_PROCESS);
		createEAttribute(frameworkProcessEClass, FRAMEWORK_PROCESS__NAME);

		frameworkApplicationEClass = createEClass(FRAMEWORK_APPLICATION);
		createEAttribute(frameworkApplicationEClass, FRAMEWORK_APPLICATION__NAME);
		createEAttribute(frameworkApplicationEClass, FRAMEWORK_APPLICATION__REPOSITORY_URL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(frameworkProcessEClass, FrameworkProcess.class, "FrameworkProcess", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFrameworkProcess_Name(), ecorePackage.getEString(), "name", null, 0, 1, FrameworkProcess.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(frameworkApplicationEClass, FrameworkApplication.class, "FrameworkApplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFrameworkApplication_Name(), ecorePackage.getEString(), "name", null, 0, 1, FrameworkApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFrameworkApplication_RepositoryUrl(), ecorePackage.getEString(), "repositoryUrl", null, 0, 1, FrameworkApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //Minerv1PackageImpl
