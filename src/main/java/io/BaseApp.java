package io;

import io.generator.StringGenerator;
import io.model.StringGeneratorInput;
import io.metrics.OutputFileTimer;
import io.metrics.StringGeneratorTimer;
import io.writer.WriteToFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseApp {

    private static final String RESOURCE_SAMPLE_FOLDER = "./src/main/resources/sample";
    private static final String NOT_TO_BE_DELETED_FILE = "GITIGNORE";

    private static final StringGenerator stringGenerator = new StringGenerator();
    private static final WriteToFile writeToFile = new WriteToFile();

    /**
     * This is the entry place of the project. It does the following things.
     * 1. Generate List of Strings and pump those into files maintaining one:one (string:file) mapping.
     * 2. Read those files into List of String inside Application.
     * 3.
     * 4.
     * @param args - Arguments passed through CLI.
     */
    public static void main(String []args) throws IOException {

        /* 1. Generate List of Strings and pump those into files maintaining one:one (string:file) mapping and capture metrics. */
        final StringGeneratorInput generatorInput = getStringGeneratorInput();
        final List<StringGeneratorTimer> stringGeneratorTimers = new ArrayList<StringGeneratorTimer>();
        final List<String> generatedStrings = stringGenerator.generateStrings(generatorInput, stringGeneratorTimers);
        if (generatedStrings.size() == stringGeneratorTimers.size()) {
            for(int i=0; i<stringGeneratorTimers.size(); i++) {
                System.out.println("String of size => " + stringGeneratorTimers.get(i).getStringLen() + " took "
                        +  (stringGeneratorTimers.get(i).getEndTime() - stringGeneratorTimers.get(i).getStartTime()) + " time to generate.");
            }
        } else {
            System.err.println("Ideally both (Generated Strings & Generated Strings Timer) should have the same size.");
        }

        final List<OutputFileTimer> outputFileWithoutBufferTimers = new ArrayList<>();
        writeToFile.writeToFileWithoutBuffer(generatedStrings, outputFileWithoutBufferTimers);
        if (generatedStrings.size() == outputFileWithoutBufferTimers.size()) {
            for(int i=0; i<outputFileWithoutBufferTimers.size(); i++) {
                System.out.println("Output string of size => " + outputFileWithoutBufferTimers.get(i).getFileLen() + " took "
                        + (outputFileWithoutBufferTimers.get(i).getEndTime() - outputFileWithoutBufferTimers.get(i).getStartTime()) + " time to write into file.");
            }
        } else {
            System.err.println("Ideally both (Output Strings & Output Strings Timer) should have the same size.");
        }

        cleanUpResourceFolder(RESOURCE_SAMPLE_FOLDER);

        final List<OutputFileTimer> outputFileWithBufferTimers = new ArrayList<>();
        writeToFile.writeToFileWithBuffer(generatedStrings, outputFileWithBufferTimers);
        if (generatedStrings.size() == outputFileWithBufferTimers.size()) {
            for(int i=0; i<outputFileWithBufferTimers.size(); i++) {
                System.out.println("Output string of size => " + outputFileWithBufferTimers.get(i).getFileLen() + " took "
                        + (outputFileWithBufferTimers.get(i).getEndTime() - outputFileWithBufferTimers.get(i).getStartTime()) + " time to write into file using buffer.");
            }
        } else {
            System.err.println("Ideally both (Buffered Output Strings & Buffered Output Strings Timer) should have the same size.");
        }

    }

    private static void cleanUpResourceFolder(final String folderPath) {
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
                File file = new File(RESOURCE_SAMPLE_FOLDER + "/" + filesTobeDeleted[j]);
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

    private static StringGeneratorInput getStringGeneratorInput() {
        return StringGeneratorInput.Builder
                .newInstance()
                .minLen(10000)
                .maxLen(100000)
                .deltaLen(5000)
                .numberOfCopies(50).build();
    }
}
