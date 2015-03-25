package cz.bbmri.io;


import cz.bbmri.entity.Attachment;
import cz.bbmri.entity.constant.Constant;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Collections of method used in more than one service implementation. All set as static.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class FileUtils {

    /**
     * Create folders for all given paths, where folder isn't alredy present
     *
     * @param errors - in case of error, error messages will be stored into errors
     * @param paths  - paths of new folders
     * @return SUCCESS/NOT_SUCCESS
     */
    public static int createFolders(ValidationErrors errors, String... paths) {
        for (String s : paths) {
            // if doesn't exist

//            System.err.println("PATH: " + s);

            if (!FileUtils.folderExists(s)) {
                // create
                if (createFolder(s, errors) != Constant.SUCCESS) {
                    // if failed
                    return Constant.NOT_SUCCESS;
                }
            }
        }
        return Constant.SUCCESS;
    }

    /**
     * Delete folder at given path recursively
     *
     * @param path - path to folder
     * @param errors - in case of error, error messages will be stored into errors
     * @return SUCCESS/NOT_SUCCESS
     */
    public static int recursiveDeleteFolder(String path, ValidationErrors errors) {
        File dir = new File(path);

        // Exists?
        if (!dir.exists()) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.folderNotFound"));
            return Constant.NOT_SUCCESS;
        }

        // Directory?
        if (!dir.isDirectory()) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.notADirectory"));
            return Constant.NOT_SUCCESS;
        }
        try {

            org.apache.commons.io.FileUtils.deleteDirectory(dir);

        } catch (IOException ex) {

            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.deleteFolderFailed"));
            return Constant.NOT_SUCCESS;

        }

        return Constant.SUCCESS;
    }

    /**
     * Delete single file
     *
     * @param file - path to file
     * @return SUCCESS/NOT_SUCCESS
     */
    public static int deleteFile(File file) {

        // Exists?
        if (!file.exists()) {
            return Constant.NOT_SUCCESS;
        }

        boolean success = file.delete();

        // Truly deleted?
        if (!success) {
            return Constant.NOT_SUCCESS;
        }
        return Constant.SUCCESS;
    }

    /**
     * Delete single file
     *
     * @param path - path to file
     * @param errors - in case of error, error messages will be stored into errors
     * @return SUCCESS/NOT_SUCCESS
     */
    private static int deleteFile(String path, ValidationErrors errors) {

        File file = new File(path);

        // Exists?
        if (!file.exists()) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.fileNotFound"));
            return Constant.NOT_SUCCESS;
        }

        boolean success = file.delete();

        // Truly deleted?
        if (!success) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.deleteFailed"));
            return Constant.NOT_SUCCESS;
        }
        return Constant.SUCCESS;
    }

    /* If file is the last in folder then delete also parent folder*/

    /**
     * Delete file and delete parent folder if the file was the last one in the folder
     *
     * @param path - path to file
     * @param errors - in case of error, error messages will be stored into errors
     * @return SUCCESS/NOT_SUCCESS
     */
    public static int deleteFileAndParentFolder(String path, ValidationErrors errors) {

        if (deleteFile(path, errors) != 0) {
            return Constant.NOT_SUCCESS;
        }

        File file = new File(path);
        File parent = file.getParentFile();

        // Correct path to parent directory?
        if (!parent.exists()) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.folderNotFound"));
            return Constant.NOT_SUCCESS;
        }
        // It is truly a directory?
        if (!parent.isDirectory()) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.notADirectory"));
            return Constant.NOT_SUCCESS;
        }

        // If empty - delete it!
        if (parent.list().length < 1) {
            boolean success = parent.delete();
            if (!success) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.parentFolderDeleteFailed"));
            }
            return Constant.NOT_SUCCESS;
        }
        return Constant.SUCCESS;
    }

    /**
     * Create single folder
     *
     * @param path - path to new folder
     * @param errors - in case of error, error messages will be stored into errors
     * @return SUCCESS/NOT_SUCCESS
     */
    private static int createFolder(String path, ValidationErrors errors) {

        File dir = new File(path);

        if (!dir.exists()) {
            boolean success = dir.mkdir();
            if (!success) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.createFolderFailed"));
                return Constant.NOT_SUCCESS;
            }
        }
        return Constant.SUCCESS;
    }

    /**
     * Return all files in folder
     *
     * @param path - path to folder
     * @return list of files
     */
    public static List<File> getFiles(String path) {

        File dir = new File(path);
        List<File> files = new ArrayList<File>();
        if (!dir.exists()) {
            System.err.println("dir doesnt exist");
            // not success
            return files;
        }

        if (!dir.isDirectory()) {
            System.err.println("not directory");
            // not success
            return files;
        }

        for (File file : dir.listFiles()) {
            // only files not directories
            if (file.isFile()) {

                files.add(file);
            }
        }

        return files;
    }

    /**
     * Copy file from source to destination
     *
     * @param source - path to source file
     * @param destination - path to new destination
     * @return SUCCESS/NOT_SUCCESS
     */
    public static int copyFile(File source, String destination) {

        File destinationFile = new File(destination + File.separator + source.getName());

        if (!source.exists()) {
            System.err.println("dir doesn't exist");

            // not success
            return Constant.NOT_SUCCESS;
        }
        try {
            org.apache.commons.io.FileUtils.copyFile(source, destinationFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("JavaIOException during copy File");
            return Constant.NOT_SUCCESS;
        }

        return Constant.SUCCESS;
    }

    /**
     * Check if folder exist or not
     *
     * @param path - path to folder
     * @return true if folder exists
     */
    private static boolean folderExists(String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            return false;

        }

        return dir.isDirectory();
    }

}
