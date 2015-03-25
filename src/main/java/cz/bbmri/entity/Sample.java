/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Sample implements Serializable {

    public static final String PROP_PATIENT = "patient";
   	public static final String PROP_ID = "id";
   	public static final String PROP_INSTITUTIONAL_ID = "institutionalId";
   	public static final String PROP_TAKING_DATE = "takingDate";
   	public static final String PROP_RETRIEVED = "retrieved";
   	public static final String PROP_MATERIAL_TYPE = "materialType";
   	public static final String PROP_COLLECTION = "collection";
   	public static final String PROP_QUANTITY = "quantity";
   	public static final String PROP_TNM = "tnm";
   	public static final String PROP_PTNM = "ptnm";
   	public static final String PROP_MORPHOLOGY = "morphology";
   	public static final String PROP_BIOPTICAL_REPORT = "biopticalReport";
   	public static final String PROP_DIAGNOSIS = "diagnosis";
   	public static final String PROP_POSITION = "position";
   	public static final String PROP_REQUEST = "request";

	private Patient patient;
    private long id;
    private String institutionalId;
    private Date takingDate;
    private Retrieved retrieved;
    private MaterialType materialType;
    private Collection collection;
    private Quantity quantity;
    private Tnm tnm;
    private Ptnm ptnm;
    private Morphology morphology;
    private BiopticalReport biopticalReport;
    private Diagnosis diagnosis;
    private Set<Position> position = new HashSet<Position>();
    private Set<Request> request = new HashSet<Request>();

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInstitutionalId() {
        return institutionalId;
    }

    public void setInstitutionalId(String institutionalId) {
        this.institutionalId = institutionalId;
    }

    public Date getTakingDate() {
        return takingDate;
    }

    public void setTakingDate(Date takingDate) {
        this.takingDate = takingDate;
    }

    public Retrieved getRetrieved() {
        return retrieved;
    }

    public void setRetrieved(Retrieved retrieved) {
        this.retrieved = retrieved;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Tnm getTnm() {
        return tnm;
    }

    public void setTnm(Tnm tnm) {
        this.tnm = tnm;
    }

    public Ptnm getPtnm() {
        return ptnm;
    }

    public void setPtnm(Ptnm ptnm) {
        this.ptnm = ptnm;
    }

    public Morphology getMorphology() {
        return morphology;
    }

    public void setMorphology(Morphology morphology) {
        this.morphology = morphology;
    }

    public BiopticalReport getBiopticalReport() {
        return biopticalReport;
    }

    public void setBiopticalReport(BiopticalReport biopticalReport) {
        this.biopticalReport = biopticalReport;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Set<Position> getPosition() {
        return position;
    }

    public void setPosition(Set<Position> position) {
        this.position = position;
    }

    public Set<Request> getRequest() {
        return request;
    }

    public void setRequest(Set<Request> request) {
        this.request = request;
    }
}
