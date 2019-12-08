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
    private static final long MINIMUM_STRING_LENGTH = 100;
    private static final long MAXIMUM_STRING_LENGTH = 200000;
    private static final long DELTA_OF_STRING_LENGTH = 100;
    private static final long MAXIMUM_NUMBER_OF_COPIES = 2000;

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
        final List<FileTimer> inputFileOnlyWithoutBufferTimers = new ArrayList<>();
        final List<String> readFilesOnlyWithoutBuffer = readFromFile.readFromFileOnlyWithoutBuffer(RESOURCE_SAMPLE_FOLDER, inputFileOnlyWithoutBufferTimers);
        if (readFilesOnlyWithoutBuffer.size() == inputFileOnlyWithoutBufferTimers.size()) {
            for(int i=0; i<inputFileOnlyWithoutBufferTimers.size(); i++) {
                System.out.println("Input string of size => " + inputFileOnlyWithoutBufferTimers.get(i).getFileLen() + " took "
                        + (inputFileOnlyWithoutBufferTimers.get(i).getEndTime() - inputFileOnlyWithoutBufferTimers.get(i).getStartTime()) + " time to read from file.");
            }
        } else {
            System.err.println("Ideally both (Input Strings & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithPlusOperatorWithoutBufferTimers = new ArrayList<>();
        final List<String> readFilesWithPlusOperatorWithoutBuffer = readFromFile.readFromFileWithPlusOperatorWithoutBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithPlusOperatorWithoutBufferTimers);
        if (readFilesWithPlusOperatorWithoutBuffer.size() == inputFileWithPlusOperatorWithoutBufferTimers.size()) {
            for (int i=0; i<inputFileWithPlusOperatorWithoutBufferTimers.size(); i++) {
                System.out.println(String.format("Input of string of size => %d took %d time to read from file.",
                        inputFileWithPlusOperatorWithoutBufferTimers.get(i).getFileLen(),
                        (inputFileWithPlusOperatorWithoutBufferTimers.get(i).getEndTime() - inputFileWithPlusOperatorWithoutBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input Strings & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithConcatWithoutBufferTimers = new ArrayList<>();
        final List<String> readFilesWithConcatWithoutBuffer = readFromFile.readFromFileWithConcatWithoutBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithConcatWithoutBufferTimers);
        if (readFilesWithConcatWithoutBuffer.size() == inputFileWithConcatWithoutBufferTimers.size()) {
            for (int i=0; i<inputFileWithConcatWithoutBufferTimers.size(); i++) {
               System.out.println(String.format("Input of string of size => %d took %d time to read from file.",
                       inputFileWithConcatWithoutBufferTimers.get(i).getFileLen(),
                       (inputFileWithConcatWithoutBufferTimers.get(i).getEndTime() - inputFileWithConcatWithoutBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input Strings & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithStringBufferWithoutBufferTimers = new ArrayList<>();
        final List<String> readFilesWithStringBufferWithoutBuffer = readFromFile.readFromFileWithStringBufferWithoutBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithStringBufferWithoutBufferTimers);
        if (readFilesWithStringBufferWithoutBuffer.size() == inputFileWithStringBufferWithoutBufferTimers.size()) {
            for (int i=0; i<inputFileWithStringBufferWithoutBufferTimers.size(); i++) {
                System.out.println(String.format("Input of string of size => %d took %d time to read from file.",
                        inputFileWithStringBufferWithoutBufferTimers.get(i).getFileLen(),
                        (inputFileWithStringBufferWithoutBufferTimers.get(i).getEndTime() - inputFileWithStringBufferWithoutBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input Strings & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithStringBuilderWithoutBufferTimers = new ArrayList<>();
        final List<String> readFilesWithStringBuilderWithoutBuffer = readFromFile.readFromFileWithStringBuilderWithoutBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithStringBuilderWithoutBufferTimers);
        if (readFilesWithStringBuilderWithoutBuffer.size() == inputFileWithStringBuilderWithoutBufferTimers.size()) {
            for (int i=0; i<inputFileWithStringBuilderWithoutBufferTimers.size(); i++) {
                System.out.println(String.format("Input of string of size => %d took %d time to read from file.",
                        inputFileWithStringBuilderWithoutBufferTimers.get(i).getFileLen(),
                        (inputFileWithStringBuilderWithoutBufferTimers.get(i).getEndTime() - inputFileWithStringBuilderWithoutBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input Strings & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileOnlyWithBufferTimers = new ArrayList<>();
        final List<String> readFilesOnlyWithBuffer = readFromFile.readFromFileOnlyWithBuffer(RESOURCE_SAMPLE_FOLDER, inputFileOnlyWithBufferTimers);
        if (readFilesOnlyWithBuffer.size() == inputFileOnlyWithBufferTimers.size()) {
            for(int i=0; i<inputFileOnlyWithBufferTimers.size(); i++) {
                System.out.println("Input string of size => " + inputFileOnlyWithBufferTimers.get(i).getFileLen()  + " took "
                        + (inputFileOnlyWithBufferTimers.get(i).getEndTime() - inputFileOnlyWithBufferTimers.get(i).getStartTime()) + " time to read from file using buffer.");
            }
        } else {
            System.err.println("Ideally both (Input String & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithPlusOperatorWithBufferTimers = new ArrayList<>();
        final List<String> readFilesWithPlusOperatorWithBuffer = readFromFile.readFromFileWithPlusOperatorWithBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithPlusOperatorWithBufferTimers);
        if (readFilesWithPlusOperatorWithBuffer.size() == inputFileWithPlusOperatorWithBufferTimers.size()) {
            for (int i=0; i<inputFileWithPlusOperatorWithBufferTimers.size(); i++) {
                System.out.println(String.format("Input string of size => %d took %d time to read from file using buffer.",
                        inputFileWithPlusOperatorWithBufferTimers.get(i).getFileLen(),
                        (inputFileWithPlusOperatorWithBufferTimers.get(i).getEndTime() - inputFileWithPlusOperatorWithBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input String & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithConcatWithBufferTimers = new ArrayList<>();
        final List<String> readFilesWithConcatWithBuffer = readFromFile.readFromFileWithConcatWithBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithConcatWithBufferTimers);
        if (readFilesWithConcatWithBuffer.size() == inputFileWithConcatWithBufferTimers.size()) {
            for (int i=0; i<inputFileWithConcatWithBufferTimers.size(); i++) {
                System.out.println(String.format("Input string of size => %d took %d time to read from file using buffer.",
                        inputFileWithConcatWithBufferTimers.get(i).getFileLen(),
                        (inputFileWithConcatWithBufferTimers.get(i).getEndTime() - inputFileWithConcatWithBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input String & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithStringBufferWithBufferTimers = new ArrayList<>();
        final List<String> readFilesWithStringBufferWithBuffer = readFromFile.readFromFileWithStringBufferWithBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithStringBufferWithBufferTimers);
        if (readFilesWithStringBufferWithBuffer.size() == inputFileWithStringBufferWithBufferTimers.size()) {
            for (int i=0; i<inputFileWithStringBufferWithBufferTimers.size(); i++) {
                System.out.println(String.format("Input string of size => %d took %d time to read from file using buffer.",
                        inputFileWithStringBufferWithBufferTimers.get(i).getFileLen(),
                        (inputFileWithStringBufferWithBufferTimers.get(i).getEndTime() - inputFileWithStringBufferWithBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input String & Input String Timer) should have the same size.");
        }

        final List<FileTimer> inputFileWithStringBuilderWithBufferTimers = new ArrayList<>();
        final List<String> readFilesWithStringBuilderWithBuffer = readFromFile.readFromFileWithStringBuilderWithBuffer(RESOURCE_SAMPLE_FOLDER, inputFileWithStringBuilderWithBufferTimers);
        if (readFilesWithStringBuilderWithBuffer.size() == inputFileWithStringBuilderWithBufferTimers.size()) {
            for (int i=0; i<inputFileWithStringBuilderWithBufferTimers.size(); i++) {
                System.out.println(String.format("Input string of size => %d took %d time to read from file using buffer.",
                        inputFileWithStringBuilderWithBufferTimers.get(i).getFileLen(),
                        (inputFileWithStringBuilderWithBufferTimers.get(i).getEndTime() - inputFileWithStringBuilderWithBufferTimers.get(i).getStartTime())));
            }
        } else {
            System.err.println("Ideally both (Input String & Input String Timer) should have the same size.");
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

        final String inputFileOnlyWithoutBufferTimerCSVHeader = inputFileOnlyWithoutBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileOnlyWithoutBufferTimerCSVOutput = new ArrayList<>();
        for(FileTimer timer: inputFileOnlyWithoutBufferTimers) {
            inputFileOnlyWithoutBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithPlusOperatorWithoutBufferTimerCSVHeader = inputFileWithPlusOperatorWithoutBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithPlusOperatorWithoutBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithPlusOperatorWithoutBufferTimers) {
            inputFileWithPlusOperatorWithoutBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithConcatWithoutBufferTimerCSVHeader = inputFileWithConcatWithoutBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithConcatWithoutBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithConcatWithoutBufferTimers) {
            inputFileWithConcatWithoutBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithStringBufferWithoutBufferTimerCSVHeader = inputFileWithStringBufferWithoutBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithStringBufferWithoutBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithStringBufferWithoutBufferTimers) {
            inputFileWithStringBufferWithoutBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithStringBuilderWithoutBufferTimerCSVHeader = inputFileWithStringBuilderWithoutBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithStringBuilderWithoutBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithStringBuilderWithoutBufferTimers) {
            inputFileWithStringBuilderWithoutBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileOnlyWithBufferTimerCSVHeader = inputFileOnlyWithBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileOnlyWithBufferTimerCSVOutput = new ArrayList<>();
        for(FileTimer timer: inputFileOnlyWithBufferTimers) {
            inputFileOnlyWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithPlusOperatorWithBufferTimerCSVHeader = inputFileWithPlusOperatorWithBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithPlusOperatorWithBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithPlusOperatorWithBufferTimers) {
            inputFileWithPlusOperatorWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithConcatWithBufferTimerCSVHeader = inputFileWithConcatWithBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithConcatWithBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithConcatWithBufferTimers) {
            inputFileWithConcatWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithStringBufferWithBufferTimerCSVHeader = inputFileWithStringBufferWithBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithStringBufferWithBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithStringBufferWithBufferTimers) {
            inputFileWithStringBufferWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        final String inputFileWithStringBuilderWithBufferTimerCSVHeader = inputFileWithStringBuilderWithBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithStringBuilderWithBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputFileWithStringBuilderWithBufferTimers) {
            inputFileWithStringBuilderWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        /* 3. Save those metrics result into CSV file for analysis.  */
        csvWriter.writeResult(resultingFolder, "StringGenerator-Metrics", stringGeneratorTimersCSVHeader, stringGeneratorTimersCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileOutputWithoutBuffer-Metrics", outputFileWithoutBufferTimerCSVHeader, outputFileWithoutBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileOutputWithBuffer-Metrics", outputFileWithBufferTimerCSVHeader, outputFileWithBufferTimerCSVOutput);

        csvWriter.writeResult(resultingFolder, "FileInputReadOnlyWithoutBuffer-Metrics", inputFileOnlyWithoutBufferTimerCSVHeader, inputFileOnlyWithoutBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputPlusOperationReadWithoutBuffer-Metrics", inputFileWithPlusOperatorWithoutBufferTimerCSVHeader, inputFileWithPlusOperatorWithoutBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputConcatReadWithoutBuffer-Metrics", inputFileWithConcatWithoutBufferTimerCSVHeader, inputFileWithConcatWithoutBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputStringBufferWithoutBuffer-Metrics", inputFileWithStringBufferWithoutBufferTimerCSVHeader, inputFileWithStringBufferWithoutBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputStringBuilderWithoutBuffer-Metrics", inputFileWithStringBuilderWithoutBufferTimerCSVHeader, inputFileWithStringBuilderWithoutBufferTimerCSVOutput);

        csvWriter.writeResult(resultingFolder, "FileInputReadOnlyWithBuffer-Metrics", inputFileOnlyWithBufferTimerCSVHeader, inputFileOnlyWithBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputPlusOperationReadWithBuffer-Metrics", inputFileWithPlusOperatorWithBufferTimerCSVHeader, inputFileWithPlusOperatorWithBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputConcatReadWithBuffer-Metrics", inputFileWithConcatWithBufferTimerCSVHeader, inputFileWithConcatWithBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputStringBufferWithBuffer-Metrics", inputFileWithStringBufferWithBufferTimerCSVHeader, inputFileWithStringBufferWithBufferTimerCSVOutput);
        csvWriter.writeResult(resultingFolder, "FileInputStringBuilderWithBuffer-Metrics", inputFileWithStringBuilderWithBufferTimerCSVHeader, inputFileWithStringBuilderWithBufferTimerCSVOutput);

        /* 4. Read the details of current Operating System and also save that into another CSV file.  */
        final String osHeader = osDetails.getCSVHeaderOfOSDetails();
        final String osValues = osDetails.getCSVValuesofOSDetails();
        csvWriter.writeResult(resultingFolder,"OSDetails", osHeader, Arrays.asList(osValues));
    }

    private static StringGeneratorInput getStringGeneratorInput() {
        return StringGeneratorInput.Builder
                .newInstance()
                .minLen(MINIMUM_STRING_LENGTH)
                .maxLen(MAXIMUM_STRING_LENGTH)
                .deltaLen(DELTA_OF_STRING_LENGTH)
                .numberOfCopies(MAXIMUM_NUMBER_OF_COPIES)
                .build();
    }
}
