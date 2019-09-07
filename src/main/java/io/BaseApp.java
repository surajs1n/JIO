package io;

import io.generator.StringGenerator;
import io.model.StringGeneratorInput;
import io.timer.OutputFileTimer;
import io.timer.StringGeneratorTimer;
import io.writer.WriteToFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BaseApp {

    private static final StringGenerator stringGenerator = new StringGenerator();
    private static final WriteToFile writeToFile = new WriteToFile();

    /**
     * This is the entry place of the project. It does the following things.
     * 1. Generate List of Strings and pump those into files maintaining one:one (string:file) mapping.
     * 2.
     * 3.
     * 4.
     * @param args - Arguments passed through CLI.
     */
    public static void main(String []args) throws IOException {

        /* 1. Generate List of Strings and pump those into files maintaining one:one (string:file) mapping and capture metrics. */
        final StringGeneratorInput generatorInput = getStringGeneratorInput();
        final List<StringGeneratorTimer> stringGeneratorTimers = new ArrayList<StringGeneratorTimer>();
        final List<String> generatedStrings = stringGenerator.generateStrings(generatorInput, stringGeneratorTimers);
        if (stringGeneratorTimers.size() != generatedStrings.size()) {
            System.out.println("Ideally both (Generated Strings & Generated Strings Timer) should have the same size.");
        } else {
            for(int i=0; i<stringGeneratorTimers.size(); i++) {
                System.out.println("String of size => " + stringGeneratorTimers.get(i).getStringLen() + " took "
                        +  (stringGeneratorTimers.get(i).getEndTime() - stringGeneratorTimers.get(i).getStartTime()) + " time to generate.");
            }
        }

        final List<OutputFileTimer> outputFileWithoutBufferTimers = new ArrayList<>();
        writeToFile.writeToFileWithoutBuffer(generatedStrings, outputFileWithoutBufferTimers);
        if (generatedStrings.size() == outputFileWithoutBufferTimers.size()) {
            for(int i=0; i<outputFileWithoutBufferTimers.size(); i++) {
                System.out.println("Output string of size => " + outputFileWithoutBufferTimers.get(i).getFileLen() + " took "
                        + (outputFileWithoutBufferTimers.get(i).getEndTime() - outputFileWithoutBufferTimers.get(i).getStartTime()) + " time to write into file.");
            }
        } else {
            System.out.println("Ideally both (Output Strings & Output Strings Timer) should have the same size.");
        }

    }

    private static StringGeneratorInput getStringGeneratorInput() {
        return StringGeneratorInput.Builder
                .newInstance()
                .minLen(1000)
                .maxLen(10000)
                .deltaLen(500)
                .numberOfCopies(50).build();
    }
}
