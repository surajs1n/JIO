package io.resultwriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * CSVWriter class.
 */
public final class CSVWriter {
    /**
     * Function to document output into CSV format.
     * @param outputFolderPath - Output Folder Path.
     * @param fileName - output file name.
     * @param header - header of comma-separated output.
     * @param outputList - List of comma-separated output values.
     * @throws IOException - throw in case of exception.
     */
    public void writeResult(final String outputFolderPath,
                            final String fileName,
                            final String header,
                            final List<String> outputList) throws IOException {
        final String completeFilePath = outputFolderPath + "/" + fileName + ".csv";
        final File outputFile = new File(completeFilePath);
        outputFile.getParentFile().mkdirs();
        try (OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(completeFilePath))) {
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
