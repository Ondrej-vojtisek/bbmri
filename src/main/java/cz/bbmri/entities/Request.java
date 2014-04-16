package cz.bbmri.entities;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */

@Table
@Entity
public class Request {

    public static final int IMPLICIT_REQUESTED_SAMPLES = 1;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    private Sample sample;

    private Integer numOfRequested;

    @ManyToOne
    private SampleQuestion sampleQuestion;

    public Request() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Integer getNumOfRequested() {
        return numOfRequested;
    }

    public void setNumOfRequested(Integer numOfRequested) {
        this.numOfRequested = numOfRequested;
    }

    public SampleQuestion getSampleQuestion() {
        return sampleQuestion;
    }

    public void setSampleQuestion(SampleQuestion sampleQuestion) {
        this.sampleQuestion = sampleQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (id != null ? !id.equals(request.id) : request.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", sample=" + sample +
                '}';
    }
}
