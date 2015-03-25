package cz.bbmri.entity;

import java.io.Serializable;

/**
 * Request defines which sample and how many pieces (aliquotes) is alocated with sample question (reservation or request)
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */


public class Request implements Serializable {

    /**
     * Default number of requested samples is this
     */
    public static final int IMPLICIT_REQUESTED_SAMPLES = 1;

    public static final String PROP_SAMPLE = "sample";
    public static final String PROP_ID = "id";
    public static final String PROP_NUMBER = "number";
    public static final String PROP_QUESTION = "question";
    public static final String PROP_RESERVATION = "reservation";
    public static final String PROP_WITHDRAW = "withdraw";

    private Sample sample;

    private long id;

    private Short number;

    private Question question;

    private Reservation reservation;

    private Withdraw withdraw;

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Withdraw getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Withdraw withdraw) {
        this.withdraw = withdraw;
    }
}
