package cz.bbmri.service.impl;

import cz.bbmri.entities.constant.Constant;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class ServiceUtils extends BasicServiceImpl {


    public static int createFolders(ValidationErrors errors, String... paths) {
        for (String s : paths) {
            // if doesn't exist
            if (!ServiceUtils.folderExists(s)) {
                // create
                if (createFolder(s, errors) != Constant.SUCCESS) {
                    // if failed
                    return Constant.NOT_SUCCESS;
                }
            }
        }
        return Constant.SUCCESS;
    }

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

            FileUtils.deleteDirectory(dir);

        } catch (IOException ex) {

            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.FacadeUtils.deleteFolderFailed"));
            return Constant.NOT_SUCCESS;

        }

        return Constant.SUCCESS;
    }

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


    public static List<File> getFiles(String path) {

        System.out.println("Get Files of path: " + path);


        File dir = new File(path);
        List<File> files = new ArrayList<File>();
        if (!dir.exists()) {

            System.err.println("dir doesnt exist");

            // not success
            return files;
        }

        if (!dir.isDirectory()) {

            System.out.println("not directory");

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

    public static int copyFile(File source, String destination) {


        File destinationFile = new File(destination + File.separator + source.getName());

        System.out.println("Copy file - Source: " + source + " destination: " + destinationFile);

        if (!source.exists()) {
            System.err.println("dir doesn't exist");

            // not success
            return Constant.NOT_SUCCESS;
        }
        try {
            FileUtils.copyFile(source, destinationFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("JavaIOException during copy File");
            return Constant.NOT_SUCCESS;
        }

        return Constant.SUCCESS;
    }

    private static boolean folderExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return false;

        }

        return dir.isDirectory();
    }
}
