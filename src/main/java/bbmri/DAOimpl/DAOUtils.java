package bbmri.DAOimpl;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.12.12
 * Time: 23:10
 * To change this template use File | Settings | File Templates.
 */
public class DAOUtils {
    private DAOUtils() throws InstantiationException {
        throw new InstantiationException("This code should never be reached");
    }

    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object to create can "
                    + "not be a null object");
        }
    }
}
