/**
 */
package br.ufrj.cos.prisma.model.miner;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link br.ufrj.cos.prisma.model.miner.Activity#getId <em>Id</em>}</li>
 *   <li>{@link br.ufrj.cos.prisma.model.miner.Activity#getName <em>Name</em>}</li>
 *   <li>{@link br.ufrj.cos.prisma.model.miner.Activity#getType <em>Type</em>}</li>
 *   <li>{@link br.ufrj.cos.prisma.model.miner.Activity#getAppClass <em>App Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see br.ufrj.cos.prisma.model.miner.MinerPackage#getActivity()
 * @model
 * @generated
 */
public interface Activity extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see br.ufrj.cos.prisma.model.miner.MinerPackage#getActivity_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link br.ufrj.cos.prisma.model.miner.Activity#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see br.ufrj.cos.prisma.model.miner.MinerPackage#getActivity_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link br.ufrj.cos.prisma.model.miner.Activity#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link br.ufrj.cos.prisma.model.miner.ActivityType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see br.ufrj.cos.prisma.model.miner.ActivityType
	 * @see #setType(ActivityType)
	 * @see br.ufrj.cos.prisma.model.miner.MinerPackage#getActivity_Type()
	 * @model
	 * @generated
	 */
	ActivityType getType();

	/**
	 * Sets the value of the '{@link br.ufrj.cos.prisma.model.miner.Activity#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see br.ufrj.cos.prisma.model.miner.ActivityType
	 * @see #getType()
	 * @generated
	 */
	void setType(ActivityType value);

	/**
	 * Returns the value of the '<em><b>App Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>App Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>App Class</em>' attribute.
	 * @see #setAppClass(String)
	 * @see br.ufrj.cos.prisma.model.miner.MinerPackage#getActivity_AppClass()
	 * @model
	 * @generated
	 */
	String getAppClass();

	/**
	 * Sets the value of the '{@link br.ufrj.cos.prisma.model.miner.Activity#getAppClass <em>App Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>App Class</em>' attribute.
	 * @see #getAppClass()
	 * @generated
	 */
	void setAppClass(String value);

} // Activity
