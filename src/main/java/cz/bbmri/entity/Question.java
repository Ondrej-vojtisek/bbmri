/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * <p/>
 * This is an automatic generated file. It will be regenerated every time
 * you generate persistence class.
 * <p/>
 * Modifying its content may cause the program not work, or your work may lost.
 * <p/>
 * Licensee: Masaryk University
 * License Type: Academic
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

public class Question implements Serializable {

    public static final String PROP_ID = "id";
    public static final String PROP_CREATED = "created";
    public static final String PROP_LAST_MODIFICATION = "lastModification";
    public static final String PROP_SPECIFICATION = "specification";
    public static final String PROP_PROJECT = "project";
    public static final String PROP_QUESTION_STATE = "questionState";
    public static final String PROP_REQUEST = "request";
    public static final String PROP_BIOBANK = "biobank";

    private long id;
    private Date created = new Date();
    private Date lastModification = new Date();
    private String specification;
    private Project project;
    private Biobank biobank;
    private QuestionState questionState = QuestionState.NEW;

    private Set<Request> request = new HashSet<Request>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public QuestionState getQuestionState() {
        return questionState;
    }

    public void setQuestionState(QuestionState questionState) {
        this.questionState = questionState;
    }

    public Set<Request> getRequest() {
        return request;
    }

    public void setRequest(Set<Request> request) {
        this.request = request;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public boolean getIsNew() {
        return questionState.equals(QuestionState.NEW);
    }

    public boolean getIsApproved() {
        return questionState.equals(QuestionState.APPROVED);
    }

    public boolean getIsDenied() {
        return questionState.equals(QuestionState.DENIED);
    }

    public boolean getIsClosed() {
        return questionState.equals(QuestionState.CLOSED);
    }

    public boolean getIsAgreed() {
        return questionState.equals(QuestionState.AGREED);
    }

    public boolean getIsDelivered() {
        return questionState.equals(QuestionState.DELIVERED);
    }

}
