package bbmri.facade.impl;

import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 9.12.13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class FacadeUtils extends BasicFacade {

    public static int recursiveDeleteFolder(String path, ValidationErrors errors) {
        File dir = new File(path);

        // Exists?
        if (!dir.exists()) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.folderNotFound"));
            return NOT_SUCCESS;
        }

        // Directory?
        if (!dir.isDirectory()) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.notADirectory"));
            return NOT_SUCCESS;
        }
        try {

            FileUtils.deleteDirectory(dir);

        } catch (IOException ex) {

            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.deleteFolderFailed"));
            return NOT_SUCCESS;

        }

        return SUCCESS;
    }

    public static int deleteFile(String path, ValidationErrors errors) {

        File file = new File(path);

        // Exists?
        if (!file.exists()) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.fileNotFound"));
            return NOT_SUCCESS;
        }

        boolean success = file.delete();

        // Truly deleted?
        if (!success) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.deleteFailed"));
            return NOT_SUCCESS;
        }
        return SUCCESS;
    }

    /* If file is the last in folder then delete also parent folder*/
    public static int deleteFileAndParentFolder(String path, ValidationErrors errors) {

        if (deleteFile(path, errors) != 0) {
            return NOT_SUCCESS;
        }

        File file = new File(path);
        File parent = file.getParentFile();

        // Correct path to parent directory?
        if (!parent.exists()) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.folderNotFound"));
            return NOT_SUCCESS;
        }
        // It is truly a directory?
        if (!parent.isDirectory()) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.notADirectory"));
            return NOT_SUCCESS;
        }

        // If empty - delete it!
        if (parent.list().length < 1) {
            boolean success = parent.delete();
            if (!success) {
                errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.parentFolderDeleteFailed"));
            }
            return NOT_SUCCESS;
        }
        return SUCCESS;
    }

    public static int createFolder(String path, ValidationErrors errors) {

        File dir = new File(path);

        if (!dir.exists()) {
            boolean success = dir.mkdir();
            if (!success) {
                errors.addGlobalError(new LocalizableError("bbmri.facade.impl.FacadeUtils.createFolderFailed"));
                return NOT_SUCCESS;
            }
        }
        return SUCCESS;
    }

    public static boolean folderExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return false;

        }

        return dir.isDirectory();
    }
}
