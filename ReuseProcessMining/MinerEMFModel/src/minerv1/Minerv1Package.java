/**
 */
package minerv1;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see minerv1.Minerv1Factory
 * @model kind="package"
 * @generated
 */
public interface Minerv1Package extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "minerv1";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://prisma.cos.ufrj.br/processreuseminerv1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "br.ufrj.cos.prisma.miner.v1";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Minerv1Package eINSTANCE = minerv1.impl.Minerv1PackageImpl.init();

	/**
	 * The meta object id for the '{@link minerv1.impl.FrameworkProcessImpl <em>Framework Process</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see minerv1.impl.FrameworkProcessImpl
	 * @see minerv1.impl.Minerv1PackageImpl#getFrameworkProcess()
	 * @generated
	 */
	int FRAMEWORK_PROCESS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAMEWORK_PROCESS__NAME = 0;

	/**
	 * The number of structural features of the '<em>Framework Process</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAMEWORK_PROCESS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Framework Process</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAMEWORK_PROCESS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link minerv1.impl.FrameworkApplicationImpl <em>Framework Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see minerv1.impl.FrameworkApplicationImpl
	 * @see minerv1.impl.Minerv1PackageImpl#getFrameworkApplication()
	 * @generated
	 */
	int FRAMEWORK_APPLICATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAMEWORK_APPLICATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Repository Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAMEWORK_APPLICATION__REPOSITORY_URL = 1;

	/**
	 * The number of structural features of the '<em>Framework Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAMEWORK_APPLICATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Framework Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FRAMEWORK_APPLICATION_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link minerv1.FrameworkProcess <em>Framework Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Framework Process</em>'.
	 * @see minerv1.FrameworkProcess
	 * @generated
	 */
	EClass getFrameworkProcess();

	/**
	 * Returns the meta object for the attribute '{@link minerv1.FrameworkProcess#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see minerv1.FrameworkProcess#getName()
	 * @see #getFrameworkProcess()
	 * @generated
	 */
	EAttribute getFrameworkProcess_Name();

	/**
	 * Returns the meta object for class '{@link minerv1.FrameworkApplication <em>Framework Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Framework Application</em>'.
	 * @see minerv1.FrameworkApplication
	 * @generated
	 */
	EClass getFrameworkApplication();

	/**
	 * Returns the meta object for the attribute '{@link minerv1.FrameworkApplication#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see minerv1.FrameworkApplication#getName()
	 * @see #getFrameworkApplication()
	 * @generated
	 */
	EAttribute getFrameworkApplication_Name();

	/**
	 * Returns the meta object for the attribute '{@link minerv1.FrameworkApplication#getRepositoryUrl <em>Repository Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository Url</em>'.
	 * @see minerv1.FrameworkApplication#getRepositoryUrl()
	 * @see #getFrameworkApplication()
	 * @generated
	 */
	EAttribute getFrameworkApplication_RepositoryUrl();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Minerv1Factory getMinerv1Factory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link minerv1.impl.FrameworkProcessImpl <em>Framework Process</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see minerv1.impl.FrameworkProcessImpl
		 * @see minerv1.impl.Minerv1PackageImpl#getFrameworkProcess()
		 * @generated
		 */
		EClass FRAMEWORK_PROCESS = eINSTANCE.getFrameworkProcess();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FRAMEWORK_PROCESS__NAME = eINSTANCE.getFrameworkProcess_Name();

		/**
		 * The meta object literal for the '{@link minerv1.impl.FrameworkApplicationImpl <em>Framework Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see minerv1.impl.FrameworkApplicationImpl
		 * @see minerv1.impl.Minerv1PackageImpl#getFrameworkApplication()
		 * @generated
		 */
		EClass FRAMEWORK_APPLICATION = eINSTANCE.getFrameworkApplication();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FRAMEWORK_APPLICATION__NAME = eINSTANCE.getFrameworkApplication_Name();

		/**
		 * The meta object literal for the '<em><b>Repository Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FRAMEWORK_APPLICATION__REPOSITORY_URL = eINSTANCE.getFrameworkApplication_RepositoryUrl();

	}

} //Minerv1Package
