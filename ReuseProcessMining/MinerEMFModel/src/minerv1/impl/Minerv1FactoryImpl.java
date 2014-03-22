/**
 */
package minerv1.impl;

import minerv1.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Minerv1FactoryImpl extends EFactoryImpl implements Minerv1Factory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Minerv1Factory init() {
		try {
			Minerv1Factory theMinerv1Factory = (Minerv1Factory)EPackage.Registry.INSTANCE.getEFactory(Minerv1Package.eNS_URI);
			if (theMinerv1Factory != null) {
				return theMinerv1Factory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Minerv1FactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Minerv1FactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case Minerv1Package.FRAMEWORK_PROCESS: return createFrameworkProcess();
			case Minerv1Package.FRAMEWORK_APPLICATION: return createFrameworkApplication();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrameworkProcess createFrameworkProcess() {
		FrameworkProcessImpl frameworkProcess = new FrameworkProcessImpl();
		return frameworkProcess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FrameworkApplication createFrameworkApplication() {
		FrameworkApplicationImpl frameworkApplication = new FrameworkApplicationImpl();
		return frameworkApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Minerv1Package getMinerv1Package() {
		return (Minerv1Package)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Minerv1Package getPackage() {
		return Minerv1Package.eINSTANCE;
	}

} //Minerv1FactoryImpl
