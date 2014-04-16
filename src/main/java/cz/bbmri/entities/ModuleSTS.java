package cz.bbmri.entities;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.14
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class ModuleSTS extends Module {

    private static final long serialVersionUID = 1L;

    public String getType(){
        return "ModuleSTS";
    }
}
