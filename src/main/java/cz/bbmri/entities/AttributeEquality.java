package cz.bbmri.entities;

import cz.bbmri.entities.exception.DifferentEntityException;

/**
 * Defines method attributesEqual for entities
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface AttributeEquality<T> {


    /**
     * Checks if two instances of same object has also the same values stored in attributes. The ID must match
     * (both objects) -> must be equals according to equals.
     *
     * @param t - object t of type T which will be compared to this object
     * @return true/false   - true if objects are completely the same, false if they differ in any attribute
     * @throws cz.bbmri.entities.exception.DifferentEntityException - when comparing two objects with different ID
     */
    boolean attributeEquality(T t) throws DifferentEntityException;

}
