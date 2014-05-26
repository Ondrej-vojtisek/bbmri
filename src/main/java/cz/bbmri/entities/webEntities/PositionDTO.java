package cz.bbmri.entities.webEntities;

/**
 * When parsing monitoring data from biobank it is easier to work with sampleId as string instead of Sample as entity.
 * Everything else is same like in Position
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PositionDTO {

    /**
     * Row of position in box
     */
    private Integer row;

    /**
     * Column of position
     */
    private Integer column;

    /**
     * Sequential position in box
     */
    private Integer sequentialPosition;

    /**
     * Identification of sample. Original ID from hospital NOT the internal used inside BBMRI.
     */
    private String sampleId;

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

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    @Override
    public String toString() {
        return "PositionDTO{" +
                "row=" + row +
                ", column=" + column +
                ", sequentialPosition=" + sequentialPosition +
                ", sampleId='" + sampleId + '\'' +
                '}';
    }
}
