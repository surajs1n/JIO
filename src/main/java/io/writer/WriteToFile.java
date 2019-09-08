package io.writer;

import io.metrics.FileTimer;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class WriteToFile {
    private static final String OUTPUT_FILE_PREFIX = "./src/main/resources/sample/File-";

    public void writeToFileWithoutBuffer(final List<String> stringList,
                                         final List<FileTimer> timerList) throws IOException {
        for(int i=0; i<stringList.size(); i++) {
            String file     = stringList.get(i);
            int fileLength  = file.length();
            String filePath = OUTPUT_FILE_PREFIX + fileLength + ".txt";

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

    public void writeToFileWithBuffer(final List<String> stringList,
                                      final List<FileTimer> timerList) throws IOException {
        for(int i=0; i<stringList.size(); i++) {
            String file = stringList.get(i);
            int fileLength = file.length();
            String filePath = OUTPUT_FILE_PREFIX + fileLength + ".txt";

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
}
