package cz.bbmri.io;

import cz.bbmri.entity.enumeration.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class FileImportResult {


    private List<InstanceImportResult> instanceImportResults = new ArrayList<InstanceImportResult>();


    /**
     * Result of file import
     */
    private Status status;

    private String path;

    public List<InstanceImportResult> getInstanceImportResults() {
        return instanceImportResults;
    }

    public void setInstanceImportResults(List<InstanceImportResult> instanceImportResults) {
        this.instanceImportResults = instanceImportResults;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("File import result:\n");
        sb.append("\tStatus: " + status + "\n");
        sb.append("\tPath: " + path + "\n");
        sb.append("\tInstance import results:\n");
        for(InstanceImportResult importResult : instanceImportResults){
            sb.append("\t\t" + importResult + "\n");
        }

        return sb.toString();
    }
}
