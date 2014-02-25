package cz.bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.2.14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class SampleQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

     @Id
     @GeneratedValue(strategy = GenerationType.TABLE)
     @Column(name = "ID", nullable = false)
     private Long id;
}
