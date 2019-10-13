package io.reader;

import io.metrics.FileTimer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFromFile {

    private static final int EOF = -1;
    private static final int MAX_LIMIT = 10000000;
    private static final String GITIGNORE = "GITIGNORE";

    public List<String> readFromFileWithoutBuffer(final String folderPath,
                                                  final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(false);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new FileInputStream(f.getPath())) {
//                    byte [] tempRead = new byte[MAX_LIMIT];
//                    int index = 0;
                    String readFile="";
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFile = readFile + (char)byteCharacter;
                        byteCharacter = fileInputStream.read();
                    }
                    readFiles.add(readFile);
                    timer.setFileLen(readFile.length());
                } catch (FileNotFoundException e) {
                    System.err.println("Not able to locate the file you are looking for : " + f.getPath());
                }

                timer.setEndTime(System.nanoTime());
                timerList.add(timer);
            }
        } else {
            System.err.println("Unfortunately, It is not a folder : " + folderPath);
        }

        return readFiles;
    }

    public List<String> readFromFileWithBuffer(final String folderPath,
                                               final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(true);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new BufferedInputStream(new FileInputStream(f.getPath()))) {
                    String readFile = "";
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFile = readFile.concat(String.valueOf((char)byteCharacter));
                        byteCharacter = fileInputStream.read();
                    }
                    readFiles.add(readFile);
                    timer.setFileLen(readFile.length());
                } catch (FileNotFoundException e) {
                    System.err.println("Not able to locate the file you are looking for : " + f.getPath());
                }

                timer.setEndTime(System.nanoTime());
                timerList.add(timer);
            }
        } else {
            System.err.println("Unfortunately, It is not a folder : " + folderPath);
        }

        return readFiles;
    }

    public List<String> readFromFileWithBuffer(final String folderPath,
                                               final Integer bufferSize,
                                               final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(true);
                timer.setStartTime(System.nanoTime());
                timer.setBufferSize(bufferSize);

                try (InputStream fileInputStream = new BufferedInputStream(new FileInputStream(f.getPath()), bufferSize)) {
                    String readFile = "";
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFile = readFile.concat(String.valueOf((char)byteCharacter));
                        byteCharacter = fileInputStream.read();
                    }
                    readFiles.add(readFile);
                    timer.setFileLen(readFile.length());
                } catch (FileNotFoundException e) {
                    System.err.println("Not able to locate the file you are looking for : " + f.getPath());
                }

                timer.setEndTime(System.nanoTime());
                timerList.add(timer);
            }
        } else {
            System.err.println("Unfortunately, It is not a folder : " + folderPath);
        }

        return readFiles;
    }

    private File [] getFilesWithoutGitIgnore(final File folder) {
        List<File> files = new ArrayList<>();

        for(File file : folder.listFiles()) {
            if (!GITIGNORE.equals(file.getName())) {
                files.add(file);
            }
        }

        return files.stream().toArray(File[]::new);
    }
 }
