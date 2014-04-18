package cz.bbmri.entities.infrastructure;

import cz.bbmri.entities.Sample;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Position represent one slot of box. One piece of sample (aliquote) can be stored at position.
 * Position can be identified in two ways - by its order - 1,2,3, .. or by its row and column conbination.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    private Box box;

    @ManyToOne
    private Sample sample;

//    Matrix position
    @Column
    private Integer row;

    @Column
    private Integer column;

//    Sequential position
    private Integer sequentialPosition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getSequentialPosition() {
        return sequentialPosition;
    }

    public void setSequentialPosition(Integer sequentialPosition) {
        this.sequentialPosition = sequentialPosition;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (!id.equals(position.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", row=" + row +
                ", column=" + column +
                ", sequentialPosition=" + sequentialPosition +
                '}';
    }
}
