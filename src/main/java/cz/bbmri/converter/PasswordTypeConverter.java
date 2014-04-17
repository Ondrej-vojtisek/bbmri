package cz.bbmri.converter;


import net.sourceforge.stripes.util.Base64;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Locale;

/**
 * Password converter in order to store the passwords in database hashed.
 * SHA-256 algorithm is used as hash function.
 *
 * Password is used only for local testing only. There is used another authentication mechanism
 * in real application deployment (authentication based on federations).
 *
 * @author Jan Sochor (jan.sochor@icebolt.info) - THALAMOSS project thalamoss-data.ics.muni.cz
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PasswordTypeConverter implements TypeConverter<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public String convert(String input, Class<? extends String> name, Collection<ValidationError> errors) {
        return hash(input);
    }
    //TODO
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
    //TODO
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

    //TODO
    @Override
    public void setLocale(Locale locale) {
        // Nothing to be done here
    }
}
