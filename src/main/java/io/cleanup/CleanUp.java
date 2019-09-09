package io.cleanup;

import java.io.File;

public class CleanUp {

    private static final String NOT_TO_BE_DELETED_FILE = "GITIGNORE";

    private CleanUp () {

    }

    public static void cleanUpFolder(final String folderPath) {
        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            String [] fileNames = folder.list();
            String [] filesTobeDeleted = new String[fileNames.length];

            for(int i=0, j=0; i<fileNames.length; i++) {
                if(!NOT_TO_BE_DELETED_FILE.equals(fileNames[i])) {
                    filesTobeDeleted[j] = fileNames[i];
                    j++;
                }
            }

            for(int j=0; j<filesTobeDeleted.length; j++) {
                File file = new File(folderPath + "/" + filesTobeDeleted[j]);
                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println(file.getPath() + " is successfully deleted.");
                    } else {
                        System.err.println("Unable to delete : " + file.getPath());
                    }
                } else {
                    System.out.println("Unable to locate file : " + file.getPath());
                }
            }

        } else {
            System.err.println("Unfortunately, It is not a folder : " + folderPath);
        }
    }
}
