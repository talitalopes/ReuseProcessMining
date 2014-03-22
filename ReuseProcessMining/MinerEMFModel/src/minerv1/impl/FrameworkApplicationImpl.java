/**
 */
package minerv1.impl;

import minerv1.FrameworkApplication;
import minerv1.Minerv1Package;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Framework Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link minerv1.impl.FrameworkApplicationImpl#getName <em>Name</em>}</li>
 *   <li>{@link minerv1.impl.FrameworkApplicationImpl#getRepositoryUrl <em>Repository Url</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FrameworkApplicationImpl extends MinimalEObjectImpl.Container implements FrameworkApplication {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRepositoryUrl() <em>Repository Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String REPOSITORY_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepositoryUrl() <em>Repository Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryUrl()
	 * @generated
	 * @ordered
	 */
	protected String repositoryUrl = REPOSITORY_URL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FrameworkApplicationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Minerv1Package.Literals.FRAMEWORK_APPLICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Minerv1Package.FRAMEWORK_APPLICATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepositoryUrl(String newRepositoryUrl) {
		String oldRepositoryUrl = repositoryUrl;
		repositoryUrl = newRepositoryUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Minerv1Package.FRAMEWORK_APPLICATION__REPOSITORY_URL, oldRepositoryUrl, repositoryUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Minerv1Package.FRAMEWORK_APPLICATION__NAME:
				return getName();
			case Minerv1Package.FRAMEWORK_APPLICATION__REPOSITORY_URL:
				return getRepositoryUrl();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Minerv1Package.FRAMEWORK_APPLICATION__NAME:
				setName((String)newValue);
				return;
			case Minerv1Package.FRAMEWORK_APPLICATION__REPOSITORY_URL:
				setRepositoryUrl((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Minerv1Package.FRAMEWORK_APPLICATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case Minerv1Package.FRAMEWORK_APPLICATION__REPOSITORY_URL:
				setRepositoryUrl(REPOSITORY_URL_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case Minerv1Package.FRAMEWORK_APPLICATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case Minerv1Package.FRAMEWORK_APPLICATION__REPOSITORY_URL:
				return REPOSITORY_URL_EDEFAULT == null ? repositoryUrl != null : !REPOSITORY_URL_EDEFAULT.equals(repositoryUrl);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", repositoryUrl: ");
		result.append(repositoryUrl);
		result.append(')');
		return result.toString();
	}

} //FrameworkApplicationImpl
