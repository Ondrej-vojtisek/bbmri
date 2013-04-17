package bbmri.webEntities;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.4.13
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class SampleRequestWrapper {

    private Long id;
    private int numberOfRequested;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfRequested() {
        return numberOfRequested;
    }

    public void setNumberOfRequested(int numberOfRequested) {
        this.numberOfRequested = numberOfRequested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleRequestWrapper that = (SampleRequestWrapper) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
