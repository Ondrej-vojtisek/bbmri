/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * <p/>
 * This is an automatic generated file. It will be regenerated every time
 * you generate persistence class.
 * <p/>
 * Modifying its content may cause the program not work, or your work may lost.
 * <p/>
 * Licensee: Masaryk University
 * License Type: Academic
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ConservationMethod extends CompositeKeyGroup implements Serializable {

    public static final String FOLDER = "medium";

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_KEY = "key";
    public static final String PROP_STORAGE_METHODOLOGY = "storage_methodology";

    private int id;
    private String name;
    private String key;
    private Set<StorageMethodology> storageMethodology = new HashSet<StorageMethodology>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<StorageMethodology> getStorageMethodology() {
        return storageMethodology;
    }

    public void setStorageMethodology(Set<StorageMethodology> storageMethodology) {
        this.storageMethodology = storageMethodology;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConservationMethod medium = (ConservationMethod) o;

        if (id != medium.id) return false;
        return name.equals(medium.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
