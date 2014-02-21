package cz.bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.14
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "module_sts")
@Entity
public class ModuleSTS extends Module {

//    @OneToMany(mappedBy = "module")
//    private List<Sample> samples = new ArrayList<Sample>();



    public String getType(){
        return "ModuleSTS";
    }
}
