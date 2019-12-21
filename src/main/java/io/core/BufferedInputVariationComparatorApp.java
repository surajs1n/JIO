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

/**
 * This Application captures the metrics of reading the data from file using different size of buffers.
 */
public class BufferedInputVariationComparatorApp {

    private static final String RESOURCE_SAMPLE_FOLDER = "./src/main/resources/sample";
    private static final String RESULT_FOLDER = "./src/main/resources/results";
    private static final long HUNDRED_MEGA_BYTE = (100*1024*1024);
    private static final int MINIMUM_BUFFER_SIZE = 1;
    private static final int MAXIMUM_BUFFER_SIZE = 100000;
    private static final int DELTA_OF_BUFFER_SIZE = 1;

    private static final StringGenerator stringGenerator = new StringGenerator();
    private static final WriteToFile writeToFile = new WriteToFile();
    private static final ReadFromFile readFromFile = new ReadFromFile();
    private static final OperatingSystemDetails osDetails = OperatingSystemDetails.getOperatingSystemDetails();
    private static final CSVWriter csvWriter = new CSVWriter();

    /**
     * The main function does the following operations:
     * 1. Read the Operating System Details.
     * 2. Generate a random string of predefined size.
     * 3. Write generated string onto File.
     * 4. Loop through and reading the same again and again with different size of buffer.
     * 5. Get the Header and Values separated.
     * 6. Write Buffered FileOutput Varying.
     * 7. CleanUp the directory.
     *
     * @param args - Arguments captured through CLI.
     * @throws IOException
     */
    public static void main(String []args) throws IOException {

        /* 1. Read the Operating System Details. */
        osDetails.fetchOSDetails();
        final String resultingFolder = RESULT_FOLDER + "/" + OperatingSystemDetails.getUniqueOSDetails();

        /* 2. Generate a random string of predefined size. */
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

        /* 3. Write generated string onto File. */
        final List<FileTimer> outputFileTimers = new ArrayList<>();
        writeToFile.writeToFileWithBuffer(RESOURCE_SAMPLE_FOLDER, generatedStrings, outputFileTimers);
        if (generatedStrings.size() == outputFileTimers.size()) {
            for (int i=0; i<outputFileTimers.size(); i++) {
                System.out.println("Output string of size => " + outputFileTimers.get(i).getFileLen() + " took "
                        + (outputFileTimers.get(i).getEndTime() - outputFileTimers.get(i).getStartTime()) + " time to write into file.");
            }
        } else {
            System.err.println("Ideally both (Output Strings & Output Strings Timer) should have the same size.");
        }

        /* 4. Loop through and reading the same again and again with different size of buffer. */
        final List<FileTimer> inputBufferTimers = new ArrayList<>();
        final BufferSizeInput input = getBufferSizeInput();
        for (int bufferSize = input.getMinSize(); bufferSize <= input.getMaxSize(); bufferSize += input.getDeltaSize()) {
            final List<FileTimer> bufferTimers = new ArrayList<>();
            final List<String> readBufferedStrings = readFromFile.readFromFileWithPassedInBuffer(RESOURCE_SAMPLE_FOLDER, bufferSize, bufferTimers);
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

        /* 5. Get the Header and Values separated. */
        final String inputFileWithBufferTimerCSVHeader = inputBufferTimers.get(0).getClassHeaderInCSV();
        final List<String> inputFileWithBufferTimerCSVOutput = new ArrayList<>();
        for (FileTimer timer : inputBufferTimers) {
            inputFileWithBufferTimerCSVOutput.add(timer.getObjectDataInCSV());
        }

        csvWriter.writeResult(resultingFolder,"FileInputWithVaryingBufferSize-Metrics", inputFileWithBufferTimerCSVHeader, inputFileWithBufferTimerCSVOutput);

        final String osHeader = osDetails.getCSVHeaderOfOSDetails();
        final String osValues = osDetails.getCSVValuesofOSDetails();
        csvWriter.writeResult(resultingFolder,"OSDetails", osHeader, Arrays.asList(osValues));

        CleanUp.cleanUpFolder(RESOURCE_SAMPLE_FOLDER);
    }

    private static BufferSizeInput getBufferSizeInput() {
        return BufferSizeInput.Builder
                .newInstance()
                .minSize(MINIMUM_BUFFER_SIZE)
                .maxSize(MAXIMUM_BUFFER_SIZE)
                .deltaSize(DELTA_OF_BUFFER_SIZE)
                .build();
    }

    /**
     * Always send only one copy input.
     * @return - 0bject of StringGeneratorInput.
     */
    private static StringGeneratorInput getStringGeneratorInput() {
        return StringGeneratorInput.Builder
                .newInstance()
                .minLen(HUNDRED_MEGA_BYTE)
                .maxLen(HUNDRED_MEGA_BYTE)
                .deltaLen(1)
                .numberOfCopies(1)
                .build();
    }
}
