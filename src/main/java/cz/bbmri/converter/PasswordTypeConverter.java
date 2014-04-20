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

/***
 * Excerpted from "Stripes: and Java Web Development is Fun Again",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
***/

/**
 * Password converter in order to store the passwords in database hashed.
 * SHA-256 algorithm is used as hash function.
 * <p/>
 * Password is used only for local testing only. There is used another authentication mechanism
 * in real application deployment (authentication based on federations).
 *
 */

public class PasswordTypeConverter implements TypeConverter<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public String convert(String input, Class<? extends String> cls,
                          Collection<ValidationError> errors) {
        return hash(input);
    }

    public String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            return Base64.encodeBytes(bytes);
        } catch (NoSuchAlgorithmException exc) {
            throw new IllegalArgumentException(exc);
        }
    }

    @Override
    public void setLocale(Locale locale) {
    }
}
