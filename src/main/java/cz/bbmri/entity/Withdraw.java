package cz.bbmri.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class Withdraw implements Serializable {

    public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_ID = "id";
   	public static final String PROP_CREATED = "created";
   	public static final String PROP_REQUEST = "request";

    private Biobank biobank;
    private int id;
    private Date created;
    private Set<Request> request = new HashSet<Request>();

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Request> getRequest() {
        return request;
    }

    public void setRequest(Set<Request> request) {
        this.request = request;
    }
}
