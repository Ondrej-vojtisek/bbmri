package cz.bbmri.entity;

import java.io.Serializable;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class BiobankMaterialType extends CompositeKeyGroup implements Serializable {

    public static final String PROP_BIOBANK = "biobank";
    public static final String PROP_MATERIAL_TYPE = "materialType";
    public static final String PROP_KEY = "key";

    private Biobank biobank;
    private int biobankId;
    private MaterialType materialType;
    private int materialTypeId;
    private String key;

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        if(biobank != null){
            this.biobankId = biobank.getId();
        }
        this.biobank = biobank;
    }

    public int getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(int biobankId) {
        this.biobankId = biobankId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        if(materialType != null){
            this.materialTypeId = materialType.getId();
        }
        this.materialType = materialType;
    }

    public int getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(int materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean equals(Object aObj) {
        if (aObj == this)
            return true;
        if (!(aObj instanceof BiobankMaterialType))
            return false;
        BiobankMaterialType biobankmaterialtype = (BiobankMaterialType) aObj;
        if (getBiobank() == null) {
            if (biobankmaterialtype.getBiobank() != null)
                return false;
        } else if (!getBiobank().equals(biobankmaterialtype.getBiobank()))
            return false;
        if (getMaterialType() == null) {
            if (biobankmaterialtype.getMaterialType() != null)
                return false;
        } else if (!getMaterialType().equals(biobankmaterialtype.getMaterialType()))
            return false;
        return true;
    }

    public int hashCode() {
        int hashcode = 0;
        if (getBiobank() != null) {
            hashcode = hashcode + getBiobank().getId();
        }
        if (getMaterialType() != null) {
            hashcode = hashcode + getMaterialType().getId();
        }
        return hashcode;
    }
}
