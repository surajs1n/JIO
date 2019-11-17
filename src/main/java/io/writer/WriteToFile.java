package io.writer;

import io.metrics.FileTimer;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Core class to contain all the write to file methods.
 */
public class WriteToFile {
    private static final String OUTPUT_FILE_PREFIX = "File-";

    /**
     * Function to write list of strings into folder without using buffer memory and also to capture the Metrics.
     * @param folderPath - Path of folder where to write.
     * @param stringList - List of string to be outputted.
     * @param timerList - List of Timer.
     * @throws IOException - throws in case of exception.
     */
    public void writeToFileWithoutBuffer(final String folderPath,
                                         final List<String> stringList,
                                         final List<FileTimer> timerList) throws IOException {
        for(int i=0; i<stringList.size(); i++) {
            String file     = stringList.get(i);
            int fileLength  = file.length();
            String filePath = folderPath + "/" + OUTPUT_FILE_PREFIX + fileLength + ".txt";

            FileTimer timer = new FileTimer(fileLength, false);
            timer.setStartTime(System.nanoTime());

            try(OutputStream fileOutputStream = new FileOutputStream(filePath)) {
                int j=0;
                while (j < fileLength) {
                    fileOutputStream.write(file.charAt(j));
                    j++;
                }
                fileOutputStream.flush();
            } catch (FileNotFoundException e) {
                System.err.println("Not able to locate the file you are looking for : " + filePath);
            }

            timer.setEndTime(System.nanoTime());
            timerList.add(timer);
        }
    }

    /**
     * Function to write list of strings into folder using buffer memory and also to capture the Metrics.
     * @param folderPath - Path of folder where to write.
     * @param stringList - List of string to be outputted.
     * @param timerList - List of Timer.
     * @throws IOException - throws in case of exception.
     */
    public void writeToFileWithBuffer(final String folderPath,
                                      final List<String> stringList,
                                      final List<FileTimer> timerList) throws IOException {
        for(int i=0; i<stringList.size(); i++) {
            String file = stringList.get(i);
            int fileLength = file.length();
            String filePath = folderPath + "/" + OUTPUT_FILE_PREFIX + fileLength + ".txt";

            FileTimer timer = new FileTimer(fileLength, true);
            timer.setStartTime(System.nanoTime());

            try (OutputStream fileBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
                int j=0;
                while (j < fileLength) {
                    fileBufferedOutputStream.write(file.charAt(j));
                    j++;
                }
                fileBufferedOutputStream.flush();
            } catch (FileNotFoundException e) {
                System.err.println("Not able to locate the file you are looking for : " + filePath);
            }

            timer.setEndTime(System.nanoTime());
            timerList.add(timer);
        }
    }

    /**
     * Function to write list of strings into folder using varying buffer memory and also to capture the metrics.
     * @param folderPath - Path of folder where to write.
     * @param stringList - List of string to be outputted.
     * @param timerList - List of Timer.
     * @throws IOException - throws in case of exception.
     */
    public void writeToFileWithBuffer(final String folderPath,
                                      final List<String> stringList,
                                      final Integer bufferSize,
                                      final List<FileTimer> timerList) throws IOException {
        for(int i=0; i<stringList.size(); i++) {
            String file = stringList.get(i);
            int fileLength = file.length();
            String filePath = folderPath + "/" + OUTPUT_FILE_PREFIX + fileLength + ".txt";

            FileTimer timer = new FileTimer(fileLength, true);
            timer.setStartTime(System.nanoTime());
            timer.setBufferSize(bufferSize);

            try (OutputStream fileBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath), bufferSize)) {
                int j=0;
                while (j < fileLength) {
                    fileBufferedOutputStream.write(file.charAt(j));
                    j++;
                }
                fileBufferedOutputStream.flush();
            } catch (FileNotFoundException e) {
                System.err.println("Not able to locate the file you are looking for : " + filePath);
            }

            timer.setEndTime(System.nanoTime());
            timerList.add(timer);
        }
    }
}
