package cz.bbmri.entities;

import javax.persistence.*;

/**
 * Request defines which sample and how many pieces (aliquotes) is alocated with sample question (reservation or request)
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Request {

    private static final long serialVersionUID = 1L;

    /**
     * Default number of requested samples is this
     */
    public static final int IMPLICIT_REQUESTED_SAMPLES = 1;

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
