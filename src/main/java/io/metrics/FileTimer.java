package io.metrics;

public class FileTimer extends Timer {
    private int fileLen;
    private boolean isBufferUsed;

    public FileTimer() {
        super();
        this.fileLen = 0;
        this.isBufferUsed = false;
    }

    public FileTimer(final boolean isBufferUsed) {
        super();
        this.fileLen = 0;
        this.isBufferUsed = false;
    }

    public FileTimer(final int fileLen, final boolean isBufferUsed) {
        super();
        this.fileLen = fileLen;
        this.isBufferUsed = isBufferUsed;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public boolean isBufferUsed() {
        return isBufferUsed;
    }

    public void setBufferUsed(boolean bufferUsed) {
        isBufferUsed = bufferUsed;
    }

    @Override
    public String toString() {
        return "FileTimer{" +
                "fileLen=" + fileLen +
                ", isBufferUsed=" + isBufferUsed +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }
}
