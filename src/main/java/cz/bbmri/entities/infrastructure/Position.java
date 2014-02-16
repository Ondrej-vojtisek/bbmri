package cz.bbmri.entities.infrastructure;

import javax.persistence.*;
import java.io.Serializable;
import cz.bbmri.entities.Sample;


/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "BoxPosition")
@Entity
public class Position implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    private Box box;

    @ManyToOne
    private Sample sample;

//    Matrix position
    @Column(name = "positionrow")
    private Integer row;

    @Column(name = "positioncolumn")
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
