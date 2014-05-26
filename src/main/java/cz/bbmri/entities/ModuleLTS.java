package cz.bbmri.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Long term module of patient
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Entity
public class ModuleLTS extends Module {

    private static final long serialVersionUID = 1L;

    public String getType(){
           return "ModuleLTS";
       }
}
