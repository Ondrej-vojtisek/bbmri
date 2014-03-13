package cz.bbmri.entities.comparator.sample;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.field.MaterialType;
import cz.bbmri.entities.sample.field.SampleIdentification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class MaterialTypeComparator implements Comparator<Sample> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

     public int compare(Sample sample1, Sample sample2) {

         MaterialType atr1 = sample1.getMaterialType();
         MaterialType atr2 = sample2.getMaterialType();


         if (atr1 == null) {
             if (atr2 == null) {
                 return 0;
             } else {
                 return Integer.MIN_VALUE;
             }
         }

         if (atr2 == null) {
             return Integer.MAX_VALUE;
         }

         if (atr1.getType() == null) {
             if (atr2.getType() == null) {
                 return 0;
             } else {
                 return Integer.MIN_VALUE;
             }
         }

         if (atr2.getType() == null) {
             return Integer.MAX_VALUE;
         }

         return atr1.getType().compareTo(atr2.getType());
     }
}
