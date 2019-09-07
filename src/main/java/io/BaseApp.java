package io;

import io.generator.StringGenerator;
import io.model.StringGeneratorInput;
import io.timer.StringGeneratorTimer;

import java.util.ArrayList;
import java.util.List;

public class BaseApp {

    private static final StringGenerator stringGenerator = new StringGenerator();

    /**
     * This is the entry place of the project. It does the following things.
     * 1. Generate List of Strings and pump those into files maintaining one:one (string:file) mapping.
     * 2.
     * 3.
     * 4.
     * @param args
     */
    public static void main(String []args) {
        final StringGeneratorInput generatorInput = StringGeneratorInput.Builder
                .newInstance()
                .minLen(10)
                .maxLen(100)
                .deltaLen(5)
                .numberOfCopies(50).build();
        final List<StringGeneratorTimer> stringGeneratorTimers = new ArrayList<StringGeneratorTimer>();
        final List<String> generatedStrings = stringGenerator.generateStrings(generatorInput,stringGeneratorTimers);

        if (stringGeneratorTimers.size() != generatedStrings.size()) {
            System.out.println("Ideally both of these should have the same size.");
        } else {
            for(int i=0; i<stringGeneratorTimers.size(); i++) {
                System.out.println("String of size => " + stringGeneratorTimers.get(i).getStringLen() + " took "
                        +  (stringGeneratorTimers.get(i).getEndTime() - stringGeneratorTimers.get(i).getStartTime()) + " time to generate.");
            }
        }

    }
}
