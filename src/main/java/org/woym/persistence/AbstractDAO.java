package org.woym.persistence;

import javax.persistence.EntityManager;

import org.woym.exceptions.DatasetException;

/**
 * Eine abstrakte generische Klasse, die als Superklasse für alle anderen
 * Handler dient.
 * 
 * @author Adrian
 *
 * @param <E>
 *            - generische Klasse
 */
public abstract class AbstractDAO<E> {

	/**
	 * Eine Instanz des EntityManagers von {@linkplain DataBase}, die in den
	 * Subklassen verwendet werden kann.
	 */
	protected final EntityManager entityManager = DataBase.getEntityManager();

	/**
	 * Persistiert das übergebene Objekt in der Datenbank. Tritt dabei ein
	 * Fehler auf, wird eine {@linkplain DatasetException} geworfen.
	 * 
	 * @param pObject
	 *            - das zu persistierende Objekt
	 */
	public void persistObject(final E pObject) throws DatasetException {
		if (pObject == null) {
			throw new IllegalArgumentException("Parameter is null.");
		}
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(pObject);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new DatasetException("Error while persisting object: "
					+ e.getMessage());
		}
	}

	/**
	 * Aktualisiert das Objekt in der Datenbank, welches dem dem übergebenen
	 * entspricht. Tritt beim Merge ein Fehler auf, wird eine
	 * {@linkplain DatasetException} geworfen.
	 * 
	 * @param pObject
	 *            - das im System bereits aktualisierte, in der Datenbank zu
	 *            persistierende Objekt
	 * @throws DatasetException
	 */
	public void updateObject(final E pObject) throws DatasetException {
		if (pObject == null) {
			throw new IllegalArgumentException("Parameter is null.");
		}
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(pObject);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new DatasetException("Error while updating object: "
					+ e.getMessage());
		}
	}

	/**
	 * Löscht das Objekt aus der Datenbank, das dem übergebenen entspricht.
	 * Tritt beim Löschen ein Fehler auf, wird eine
	 * {@linkplain DatasetException} geworfen.
	 * 
	 * @param pObject
	 *            - das zu löschende Objekt
	 * @throws DatasetException
	 */
	public void deleteObject(final E pObject) throws DatasetException {
		if (pObject == null) {
			throw new IllegalArgumentException("Parameter is null.");
		}
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(pObject);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			throw new DatasetException("Error while updating objects: "
					+ e.getMessage());
		}
	}
}
