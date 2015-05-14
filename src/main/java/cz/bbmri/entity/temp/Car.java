package cz.bbmri.entity.temp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class Car extends HashMap<String, List<String>> {
    public Car() {
        put("Acura", Arrays.asList("CSX", "MDX", "TL", "TSX"));
        put("Ford", Arrays.asList("Escape", "Explorer", "Focus", "Mustang"));
        put("Honda", Arrays.asList("Accord", "Civic", "CR-V", "S2000"));
        put("Porsche", Arrays.asList("911 Carrera", "Boxster"));
    }
}
