package io.core;

import io.cleanup.CleanUp;
import io.generator.StringGenerator;
import io.metrics.FileTimer;
import io.model.StringGeneratorInput;
import io.metrics.StringGeneratorTimer;
import io.os.OperatingSystemDetails;
import io.reader.ReadFromFile;
import io.resultwriter.CSVWriter;
import io.writer.WriteToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputOutputComparatorApp {

    private static final String RESOURCE_SAMPLE_FOLDER = "./src/main/resources/sample";
    private static final String RESULT_FOLDER = "./src/main/resources/results";

    private static final StringGenerator stringGenerator = new StringGenerator();
    private static final WriteToFile writeToFile = new WriteToFile();
    private static final ReadFromFile readFromFile = new ReadFromFile();
    private static final OperatingSystemDetails osDetails = OperatingSystemDetails.getOperatingSystemDetails();
    private static final CSVWriter csvWriter = new CSVWriter();

    /**
     * This is the entry place of the project. It does the following things.
     * 1. Generate List of Strings and pump those into files maintaining one:one (string:file) mapping, and capture metrics.
     * 2. Read those files into List of String inside application.
     * 3. Save those metrics result into CSV file for analysis.
     * 4. Read the details of current Operating System and also save that into another CSV file.
     * @param args - Arguments passed through CLI.
     */
    public static void main(String []args) throws IOException {

        osDetails.fetchOSDetails();
        final String resultingFolder = RESULT_FOLDER + "/" + OperatingSystemDetails.getUniqueOSDetails();

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

        final List<FileTimer> outputFileWithoutBufferTimers = new ArrayList<>();
        writeToFile.writeToFileWithoutBuffer(RESOURCE_SAMPLE_FOLDER, generatedStrings, outputFileWithoutBufferTimers);
        if (generatedStrings.size() == outputFileWithoutBufferTimers.size()) {
            for(int i=0; i<outputFileWithoutBufferTimers.size(); i++) {
                System.out.println("Output string of size => " + outputFileWithoutBufferTimers.get(i).getFileLen() + " took "
                        + (outputFileWithoutBufferTimers.get(i).getEndTime() - outputFileWithoutBufferTimers.get(i).getStartTime()) + " time to write into file.");
            }
        } else {
            System.err.println("Ideally both (Output Strings & Output Strings Timer) should have the same size.");
        }

        CleanUp.cleanUpFolder(RESOURCE_SAMPLE_FOLDER);

        final List<FileTimer> outputFileWithBufferTimers = new ArrayList<>();
        writeToFile.writeToFileWithBuffer(RESOURCE_SAMPLE_FOLDER, generatedStrings, outputFileWithBufferTimers);
        if (generatedStrings.size() == outputFileWithBufferTimers.size()) {
            for(int i=0; i<outputFileWithBufferTimers.size(); i++) {
                System.out.println("Output string of size => " + outputFileWithBufferTimers.get(i).getFileLen() + " took "
                        + (outputFileWithBufferTimers.get(i).getEndTime() - outputFileWithBufferTimers.get(i).getStartTime()) + " time to write into file using buffer.");
            }
        } else {
            System.err.println("Ideally both (Buffered Output Strings & Buffered Output Strings Timer) should have the same size.");
        }

        /* 2. Read those files into List of String inside Application.  */
        final List<FileTimer> inputFileWithoutBufferTimers = new ArrayList<>();
        final List<String> readFilesWithoutBuffer = readFromFile.readFromFileWithoutBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithoutBufferTimers);
        if (readFilesWithoutBuffer.size() == inputFileWithoutBufferTimers.size()) {
            for(int i=0; i<inputFileWithoutBufferTimers.size(); i++) {
                System.out.println("Input string of size => " + inputFileWithoutBufferTimers.get(i).getFileLen() + " took "
                        + (inputFileWithoutBufferTimers.get(i).getEndTime() - inputFileWithoutBufferTimers.get(i).getStartTime()) + " time to read from file.");
            }
        } else {
            System.err.println("Ideally both (Input Strings & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithBufferTimers = new ArrayList<>();
        final List<String> readFilesWithBuffer = readFromFile.readFromFileWithBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithBufferTimers);
        if (readFilesWithBuffer.size() == inputFileWithBufferTimers.size()) {
            for(int i=0; i<inputFileWithBufferTimers.size(); i++) {
                System.out.println("Input string of size => " + inputFileWithBufferTimers.get(i).getFileLen()  + " took "
                        + (inputFileWithBufferTimers.get(i).getEndTime() - inputFileWithBufferTimers.get(i).getStartTime()) + " time to read from file using buffer.");
            }
        } else {
            System.err.println("Ideally both () should have the same size.");
        }

        CleanUp.cleanUpFolder(RESOURCE_SAMPLE_FOLDER);

        final String stringGeneratorTimersCSVHeader = stringGeneratorTimers.get(0).getClassHeaderInCSV();
        final List<String> stringGeneratorTimersCSVOutput = new ArrayList<>();
        for(StringGeneratorTimer timer: stringGeneratorTimers) {
            stringGeneratorTimersCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String outputFileWithoutBufferTimerCSVHeader = outputFileWithoutBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> outputFileWithoutBufferTimerCSVOutput = new ArrayList<>();
        for(FileTimer timer : outputFileWithoutBufferTimers) {
            outputFileWithoutBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String outputFileWithBufferTimerCSVHeader = outputFileWithBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> outputFileWithBufferTimerCSVOutput = new ArrayList<>();
        for(FileTimer timer: outputFileWithBufferTimers) {
            outputFileWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithoutBufferTimerCSVHeader = inputFileWithoutBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithoutBufferTimerCSVOutput = new ArrayList<>();
        for(FileTimer timer: inputFileWithoutBufferTimers) {
            inputFileWithoutBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithBufferTimerCSVHeader = inputFileWithBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithBufferTimerCSVOutput = new ArrayList<>();
        for(FileTimer timer: inputFileWithBufferTimers) {
            inputFileWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        /* 3. Save those metrics result into CSV file for analysis.  */
        csvWriter.writeResult(resultingFolder, "StringGenerator-Metrics", stringGeneratorTimersCSVHeader, stringGeneratorTimersCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileOutputWithoutBuffer-Metrics", outputFileWithoutBufferTimerCSVHeader, outputFileWithoutBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileOutputWithBuffer-Metrics", outputFileWithBufferTimerCSVHeader, outputFileWithBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputWithoutBuffer-Metrics", inputFileWithoutBufferTimerCSVHeader, inputFileWithoutBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputWithBuffer-Metrics", inputFileWithBufferTimerCSVHeader, inputFileWithBufferTimerCSVOutput);

        /* 4. Read the details of current Operating System and also save that into another CSV file.  */
        final String osHeader = osDetails.getCSVHeaderOfOSDetails();
        final String osValues = osDetails.getCSVValuesofOSDetails();
        csvWriter.writeResult(resultingFolder,"OSDetails", osHeader, Arrays.asList(osValues));
    }

    private static StringGeneratorInput getStringGeneratorInput() {
        return StringGeneratorInput.Builder
                .newInstance()
                .minLen(1000)
                .maxLen(10000)
                .deltaLen(1000)
                .numberOfCopies(50)
                .build();
    }
}
