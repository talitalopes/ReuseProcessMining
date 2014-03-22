/**
 */
package minerv1;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Framework Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link minerv1.FrameworkApplication#getName <em>Name</em>}</li>
 *   <li>{@link minerv1.FrameworkApplication#getRepositoryUrl <em>Repository Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see minerv1.Minerv1Package#getFrameworkApplication()
 * @model
 * @generated
 */
public interface FrameworkApplication extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see minerv1.Minerv1Package#getFrameworkApplication_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link minerv1.FrameworkApplication#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Repository Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository Url</em>' attribute.
	 * @see #setRepositoryUrl(String)
	 * @see minerv1.Minerv1Package#getFrameworkApplication_RepositoryUrl()
	 * @model
	 * @generated
	 */
	String getRepositoryUrl();

	/**
	 * Sets the value of the '{@link minerv1.FrameworkApplication#getRepositoryUrl <em>Repository Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository Url</em>' attribute.
	 * @see #getRepositoryUrl()
	 * @generated
	 */
	void setRepositoryUrl(String value);

} // FrameworkApplication
