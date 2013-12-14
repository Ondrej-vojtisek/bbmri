package cz.bbmri.entities.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.7.13
 * Time: 0:15
 * To change this template use File | Settings | File Templates.
 */
public enum SampleRetrieval {
        PREOPERATIONAL("preoperational"),
        OPERATIONAL("operational"),
        POST("post"),
        UNKNOWN("unknown");

        private String sampleRetrieval;

        private SampleRetrieval(String sampleRetrieval) {
            this.sampleRetrieval = sampleRetrieval;
        }

        @Override
        public String toString() {
            return this.sampleRetrieval;
        }
}
