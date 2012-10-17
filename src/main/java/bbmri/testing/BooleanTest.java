package bbmri.testing;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.9.12
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class BooleanTest {

        /**
     * Boolean to String conversion
     */
    public void convertBooleanToString() {

        boolean theValue = false;
        //Do the boolean to String conversion
        String theValueAsString = new Boolean(theValue).toString();
        theValueAsString = "'" + theValueAsString + "'";
        System.out.println(theValueAsString);
    }

    /**
     * Starts the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new BooleanTest().convertBooleanToString();
    }

}
