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
import java.util.HashSet;
import java.util.Set;

public class QuestionState implements Serializable {

    public static final String FOLDER = "questionState";

    public QuestionState() {
    }

    public QuestionState(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final QuestionState NEW = new QuestionState(1, "new");
    public static final QuestionState APPROVED = new QuestionState(2, "approved");
    public static final QuestionState DENIED = new QuestionState(3, "denied");
    public static final QuestionState CLOSED = new QuestionState(4, "closed");
    public static final QuestionState AGREED = new QuestionState(5, "agreed");
    public static final QuestionState DELIVERED = new QuestionState(6, "delivered");

    public static final String PROP_ID = "id";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_QUESTION = "question";

    private int id;
    private String name;
	
    private Set<Question> question = new HashSet<Question>();

	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}

    public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}

    public Set<Question> getQuestion() {
        return question;
    }

    public void setQuestion(Set<Question> question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionState)) return false;

        QuestionState that = (QuestionState) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
