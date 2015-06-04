package cz.bbmri.dao;

import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.BiobankMaterialType;
import cz.bbmri.entity.MaterialType;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface BiobankMaterialTypeDAO extends AbstractCompositeDAO<BiobankMaterialType> {

    MaterialType get(Biobank biobank, String key);

    List<BiobankMaterialType> get(Biobank biobank, MaterialType materialType);
}
