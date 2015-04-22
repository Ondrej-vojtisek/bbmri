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

public class MaterialType implements Serializable {

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_SAMPLE = "sample";

    public static final String UNKNOWN_TYPE = "Unknown type";

    private int id;
    private String nameEnglish;
    private String name;
    private String description;

    private Set<Sample> sample = new HashSet<Sample>();
    private Set<BiobankMaterialType> biobankMaterialType = new HashSet<BiobankMaterialType>();

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }

    public void setSample(Set<Sample> value) {
        this.sample = value;
    }

    public Set<Sample> getSample() {
        return sample;
    }

    public Set<BiobankMaterialType> getBiobankMaterialType() {
        return biobankMaterialType;
    }

    public void setBiobankMaterialType(Set<BiobankMaterialType> biobankMaterialType) {
        this.biobankMaterialType = biobankMaterialType;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        // Necessary due to javassist
        // getClass is not equals: getClass() != o.getClass()
        // class cz.bbmri.entity.MaterialType
        // class cz.bbmri.entity.MaterialType_$$_javassist_36
        if (!getClass().isAssignableFrom(o.getClass())) {
            System.err.println("Not assignableFrom");

            return false;
        }

        MaterialType that = (MaterialType) o;

        // there can't be id == that.id
        // that class is sort of a facade (bcs of javassist) to real class. that.id is null but getId() is valid
        return id == that.getId();

    }

}
