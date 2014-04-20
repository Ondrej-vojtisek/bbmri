package cz.bbmri.entities;

import javax.persistence.Entity;

/**
 * Short term module of patient
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
public class ModuleSTS extends Module {

    private static final long serialVersionUID = 1L;

    public String getType(){
        return "ModuleSTS";
    }
}
