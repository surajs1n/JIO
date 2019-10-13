package io.core;

import io.cleanup.CleanUp;
import io.generator.StringGenerator;
import io.metrics.FileTimer;
import io.metrics.StringGeneratorTimer;
import io.model.BufferSizeInput;
import io.model.StringGeneratorInput;
import io.os.OperatingSystemDetails;
import io.reader.ReadFromFile;
import io.resultwriter.CSVWriter;
import io.writer.WriteToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BufferedInputVariationComparatorApp {

    private static final String RESOURCE_SAMPLE_FOLDER = "./src/main/resources/sample";

    private static final StringGenerator stringGenerator = new StringGenerator();
    private static final WriteToFile writeToFile = new WriteToFile();
    private static final ReadFromFile readFromFile = new ReadFromFile();
    private static final OperatingSystemDetails osDetails = OperatingSystemDetails.getOperatingSystemDetails();
    private static final CSVWriter csvWriter = new CSVWriter();


    public static void main(String []args) throws IOException {

        osDetails.fetchOSDetails();

        final StringGeneratorInput generatorInput = getStringGeneratorInput();
        final List<StringGeneratorTimer> stringGeneratorTimers = new ArrayList<>();
        final List<String> generatedStrings = stringGenerator.generateStrings(generatorInput, stringGeneratorTimers);
        if (generatedStrings.size() == stringGeneratorTimers.size()) {
            for(int i=0; i<stringGeneratorTimers.size(); i++) {
                System.out.println("String of size => " + stringGeneratorTimers.get(i).getStringLen() + " took "
                        + (stringGeneratorTimers.get(i).getEndTime() - stringGeneratorTimers.get(i).getStartTime()) + " time to generate.");
            }
        } else {
            System.err.println("Ideally both (Generated Strings & Generated Strings Timer) should have the same size.");
        }

        final List<FileTimer> outputFileTimers = new ArrayList<>();
        writeToFile.writeToFileWithBuffer(generatedStrings, outputFileTimers);
        if (generatedStrings.size() == outputFileTimers.size()) {
            for (int i=0; i<outputFileTimers.size(); i++) {
                System.out.println("Output string of size => " + outputFileTimers.get(i).getFileLen() + " took "
                        + (outputFileTimers.get(i).getEndTime() - outputFileTimers.get(i).getStartTime()) + " time to write into file.");
            }
        } else {
            System.err.println("Ideally both (Output Strings & Output Strings Timer) should have the same size.");
        }

        // Now do the main thing
        final List<FileTimer> inputBufferTimers = new ArrayList<>();
        final BufferSizeInput input = getBufferSizeInput();
        for (int bufferSize = input.getMinSize(); bufferSize <= input.getMaxSize(); bufferSize += input.getDeltaSize()) {
            final List<FileTimer> bufferTimers = new ArrayList<>();
            final List<String> readBufferedStrings = readFromFile.readFromFileWithBuffer(RESOURCE_SAMPLE_FOLDER, bufferSize, bufferTimers);
            if (readBufferedStrings.size() == bufferTimers.size()) {
                for (int i = 0; i < bufferTimers.size(); i++) {
                    System.out.println("Input string of size => " + bufferTimers.get(i).getFileLen() + " took "
                            + (bufferTimers.get(i).getEndTime() - bufferTimers.get(i).getStartTime()) + " time to read from file with "
                            + bufferTimers.get(i).getBufferSize() + " buffer size.");
                }

                inputBufferTimers.add(bufferTimers.get(0));
            } else {
                System.err.println("Ideally both (Input Strings & Input String Timer) should have the same size.");
            }
        }

        final String inputFileWithBufferTimerCSVHeader = inputBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputBufferTimers) {
            inputFileWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        csvWriter.writeResult("FileInputWithVaryingBufferSize-Metrics", inputFileWithBufferTimerCSVHeader, inputFileWithBufferTimerCSVOutput);

        final String osHeader = osDetails.getCSVHeaderOfOSDetails();
        final String osValues = osDetails.getCSVValuesofOSDetails();
        csvWriter.writeResult("OSDetails", osHeader, Arrays.asList(osValues));

        CleanUp.cleanUpFolder(RESOURCE_SAMPLE_FOLDER);
    }

    private static BufferSizeInput getBufferSizeInput() {
        return BufferSizeInput.Builder
                .newInstance()
                .minSize(1)
                .maxSize(10000)
                .deltaSize(1)
                .build();
    }

    /**
     * Always send only one input.
     * @return - 0bject of StringGeneratorInput.
     */
    private static StringGeneratorInput getStringGeneratorInput() {
        return StringGeneratorInput.Builder
                .newInstance()
                .minLen(10000000)
                .maxLen(10000000)
                .deltaLen(1)
                .numberOfCopies(1)
                .build();
    }
}
