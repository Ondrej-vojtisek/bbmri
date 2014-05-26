package cz.bbmri.entities.sample.field;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Abstract definition of TNM classification
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Embeddable
abstract public class TNMclassification  implements Serializable {

    private static final long serialVersionUID = 1L;

       @Column(name="tnm", length = 7)
       private String classification;

       public String getClassification() {
           return classification;
       }

       public void setClassification(String classification) {
           this.classification = classification;
       }

       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (!(o instanceof TNMclassification)) return false;

           TNMclassification tnm = (TNM) o;

           if (classification != null ? !classification.equals(tnm.classification) : tnm.classification != null)
               return false;

           return true;
       }

       @Override
       public int hashCode() {
           return classification != null ? classification.hashCode() : 0;
       }

       @Override
       public String toString() {
           return classification;
       }
}
