package io.resultwriter;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CSVWriter {

    public static final String OUTPUT_FOLDER_PATH = "./src/main/resources/results";

    public void writeResult(final String fileName, final String header, final List<String> outputList)
            throws IOException {
        try (OutputStream fileOutputStream = new BufferedOutputStream(
                new FileOutputStream(OUTPUT_FOLDER_PATH + "/" + fileName + ".csv"))) {
            fileOutputStream.write(header.getBytes());
            fileOutputStream.write("\n".getBytes());
            for(String output: outputList) {
                fileOutputStream.write(output.getBytes());
                fileOutputStream.write("\n".getBytes());
            }
            fileOutputStream.flush();
        }
    }
}
