package io.reader;

import io.metrics.FileTimer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Core class to contain all the read from file methods.
 */
public class ReadFromFile {

    private static final int EOF = -1;
    private static final int MAX_LIMIT = 10000000;
    private static final String GITIGNORE = "GITIGNORE";
    private static final String EMPTY_STRING = "";

    /**
     * Function to read list of files from folders without using buffer memory and also to capture the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileOnlyWithoutBuffer(final String folderPath,
                                                      final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(false);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new FileInputStream(f.getPath())) {
                    int count = 0;
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        count++;
                        byteCharacter = fileInputStream.read();
                    }
                    readFiles.add(EMPTY_STRING);
                    timer.setFileLen(count);
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

    /**
     * Function to read list of files from folders without using buffer memory and also to capture the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithPlusOperatorWithoutBuffer(final String folderPath,
                                                                  final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(false);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new FileInputStream(f.getPath())) {
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

    /**
     * Function to read list of files from folders without using buffer memory and also to capture the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithConcatWithoutBuffer(final String folderPath,
                                                            final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(false);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new FileInputStream(f.getPath())) {
                    String readFile="";
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

    /**
     * Function to read list of files from folders without using buffer memory and also to capture the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithStringBufferWithoutBuffer(final String folderPath,
                                                                  final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(false);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new FileInputStream(f.getPath())) {
                    StringBuffer readFileBuffer = new StringBuffer();
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFileBuffer.append((char)byteCharacter);
                        byteCharacter = fileInputStream.read();
                    }
                    final String readFile= readFileBuffer.toString();
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

    /**
     * Function to read list of files from folders without using buffer memory and also to capture the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithStringBuilderWithoutBuffer(final String folderPath,
                                                                   final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(false);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new FileInputStream(f.getPath())) {
                    StringBuilder readFileBuilder = new StringBuilder();
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFileBuilder.append((char)byteCharacter);
                        byteCharacter = fileInputStream.read();
                    }
                    final String readFile= readFileBuilder.toString();
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


    /**
     * Function to read list of files from folders using buffer memory and also captures the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileOnlyWithBuffer(final String folderPath,
                                                   final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(true);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new BufferedInputStream(new FileInputStream(f.getPath()))) {
                    int count = 0;
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        count++;
                        byteCharacter = fileInputStream.read();
                    }
                    readFiles.add(EMPTY_STRING);
                    timer.setFileLen(count);
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

    /**
     * Function to read list of files from folders using buffer memory and also captures the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithPlusOperatorWithBuffer(final String folderPath,
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

    /**
     * Function to read list of files from folders using buffer memory and also captures the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithConcatWithBuffer(final String folderPath,
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

    /**
     * Function to read list of files from folders using buffer memory and also captures the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithStringBufferWithBuffer(final String folderPath,
                                                               final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(true);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new BufferedInputStream(new FileInputStream(f.getPath()))) {
                    StringBuffer readFileBuffer = new StringBuffer();
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFileBuffer.append((char)byteCharacter);
                        byteCharacter = fileInputStream.read();
                    }
                    final String readFile= readFileBuffer.toString();
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

    /**
     * Function to read list of files from folders using buffer memory and also captures the Metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithStringBuilderWithBuffer(final String folderPath,
                                                                final List<FileTimer> timerList) throws IOException {
        final List<String> readFiles = new ArrayList<>();
        final File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File [] filePaths = getFilesWithoutGitIgnore(folder);
            for(File f: filePaths) {
                FileTimer timer = new FileTimer(true);
                timer.setStartTime(System.nanoTime());

                try (InputStream fileInputStream = new BufferedInputStream(new FileInputStream(f.getPath()))) {
                    StringBuilder readFileBuilder = new StringBuilder();
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFileBuilder.append((char)byteCharacter);
                        byteCharacter = fileInputStream.read();
                    }
                    final String readFile= readFileBuilder.toString();
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

    /**
     * Function to read list of files from folders using variable buffer memory and also to capture the metrics.
     * @param folderPath - Path of folder.
     * @param timerList - List of Timer
     * @return - List of read strings.
     * @throws IOException - throw in case of exception.
     */
    public List<String> readFromFileWithPassedInBuffer(final String folderPath,
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
                    StringBuilder readFileBuilder = new StringBuilder();
                    int byteCharacter = fileInputStream.read();
                    while (byteCharacter != EOF) {
                        readFileBuilder.append((char)byteCharacter);
                        byteCharacter = fileInputStream.read();
                    }
                    final String readFile= readFileBuilder.toString();
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
