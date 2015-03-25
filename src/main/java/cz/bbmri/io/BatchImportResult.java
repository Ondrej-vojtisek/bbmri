package cz.bbmri.io;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class BatchImportResult {

    private List<InstanceImportResult> instanceImportResults = new ArrayList<InstanceImportResult>();

    public List<InstanceImportResult> getInstanceImportResults() {
        return instanceImportResults;
    }

    public void setInstanceImportResults(List<InstanceImportResult> instanceImportResults) {
        this.instanceImportResults = instanceImportResults;
    }

//    private int getStatistics(Status status, String objectName) {
//
//        int number = 0;
//
//        for (InstanceImportResult importResult : instanceImportResults) {
//            if (importResult.getObjectName().equals(objectName) &&
//                    importResult.getStatus().equals(status)) {
//                number++;
//            }
//        }
//
//        return number;
//    }

//    public int getNumberOfErrors(String object) {
//        return getStatistics(Status.ERROR, object);
//    }
//
//    public int getNumberOfAdded(String object) {
//        return getStatistics(Status.ADDED_NEW, object);
//    }
//
//    public int getNumberOfChanged(String object) {
//        return getStatistics(Status.CHANGED_CURRENT, object);
//    }
//
//    public int getNumberOfUnchanged(String object) {
//        return getStatistics(Status.UNCHANGED_CURRENT, object);
//    }


//    @Override
//    public String toString() {
//        StringBuilder str = new StringBuilder();
//        str.append("IMPORT RESULTS\n");
//        str.append("PATIENTS\n");
//        str.append(
//                "\tNEW: " + getNumberOfAdded(Patient.class.toString()) + "\n" +
//                        "\tUPDATED: " + getNumberOfChanged(Patient.class.toString()) + "\n" +
//                        "\tUNCHANGED: " + getNumberOfUnchanged(Patient.class.toString()) + "\n" +
//                        "\tERROR: " + getNumberOfErrors(Patient.class.toString()) + "\n" +
//                        "\tSingle objects="
//        );
//        for (InstanceImportResult instanceImportResult : instanceImportResults) {
//            if (instanceImportResult.getObjectName().equals(Patient.class.toString())) {
//                str.append(instanceImportResult);
//            }
//        }
//        str.append("\n");
//
//        str.append("SAMPLES\n");
//        str.append(
//                "\tNEW: " + getNumberOfAdded(Sample.class.toString()) + "\n" +
//                        "\tUPDATED: " + getNumberOfChanged(Sample.class.toString()) + "\n" +
//                        "\tUNCHANGED: " + getNumberOfUnchanged(Sample.class.toString()) + "\n" +
//                        "\tERROR: " + getNumberOfErrors(Sample.class.toString()) + "\n" +
//                        "\tSingle objects="
//        );
//        for (InstanceImportResult instanceImportResult : instanceImportResults) {
//            if (instanceImportResult.getObjectName().equals(Sample.class.toString())) {
//                str.append(instanceImportResult);
//            }
//        }
//        str.append("\n");
//
//        return str.toString();
//    }
}
