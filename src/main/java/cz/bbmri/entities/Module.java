package cz.bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.14
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "module")
@Entity
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne
    private Patient patient;

    @OneToMany(mappedBy = "module")
    private List<Sample> samples = new ArrayList<Sample>();
}
