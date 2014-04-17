package cz.bbmri.converter;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.4.14
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */

import net.sourceforge.stripes.util.Base64;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Formatter;
import java.util.Locale;

/**
 * Password converter in order to store the passwords hashed.
 *
 * @author Sochi
 * @version 0.1
 */
public class PasswordTypeConverter implements TypeConverter<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public String convert(String input, Class<? extends String> name, Collection<ValidationError> errors) {
        logger.debug("HASHED PASSWORD: " + hash(input));
        return hash(input);
    }

    public String hex(String input) {
        // Create new instance of formatter
        Formatter formatter = new Formatter();

        // Load the prepared bytes
        byte[] hash = prepare(input);

        // Iterate through
        for (byte b : hash) {
            formatter.format("%02x", b);
        }

        // Return the complete string
        return formatter.toString();
    }

    /**
     * Hashes the given input using SHA-1 algorithm.
     *
     * @param input
     * @return hashed input
     */
    private byte[] prepare(String input) {
        try {
            // Create the instance and hash the given input
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Returns the hash based on the input given.
     *
     * @param password
     * @return hashed input as string
     */
    public String hash(String password) {
        // Return the result encoded
        return Base64.encodeBytes(prepare(password));
    }

    @Override
    public void setLocale(Locale locale) {
        // Nothing to be done here
    }
}
