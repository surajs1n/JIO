package io.generator;

import io.model.StringGeneratorInput;
import io.metrics.StringGeneratorTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class responsible for generating random string.
 */
public class StringGenerator {

    private final static long ONE = 1;
    private final static String ALPHABETS = "abcdefghijklmnopqrstuvwxyz .\n";

    /**
     * Function to generate List of random strings for given {@link StringGeneratorInput}
     * and also record time taken while generating them.
     * @param stringGeneratorInput - Input to tell how many random strings to be generated.
     * @param stringGenerationTime - List of Timer to capture generation metrics.
     * @return List of random Strings.
     */
    public List<String> generateStrings(final StringGeneratorInput stringGeneratorInput,
                                        final List<StringGeneratorTimer> stringGenerationTime) {
        final List<String> listofInputString = new ArrayList<String>();
        long startLen = stringGeneratorInput.getMinLen();
        long endLen = stringGeneratorInput.getMaxLen();
        long incrementLen = stringGeneratorInput.getDeltaLen();
        long numberOfCopies = stringGeneratorInput.getNumberOfCopies();
        long runningCopyCount = 1;

        while(startLen <= endLen && runningCopyCount <= numberOfCopies) {
            final StringGeneratorTimer timer = new StringGeneratorTimer();
            final String inputString = getInputString(startLen, timer);
            timer.setStringLen(startLen);
            stringGenerationTime.add(timer);
            listofInputString.add(inputString);
            startLen = startLen + incrementLen;
            runningCopyCount = runningCopyCount + ONE;
        }

        return listofInputString;
    }

    private String getInputString(final long startLen, final StringGeneratorTimer timer) {
        timer.setStartTime(System.nanoTime());

        final int length = ALPHABETS.length();
        String inputString = "";

        Random random = new Random();
        for(int i=0; i<startLen; i++) {
            inputString = inputString.concat(String.valueOf(ALPHABETS.charAt(random.nextInt(length))));
        }

        timer.setEndTime(System.nanoTime());
        return inputString;
    }
}
