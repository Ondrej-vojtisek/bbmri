package bbmri.serviceImpl;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.8.13
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
public class BasicServiceImpl {

    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object");
        }
    }
}
