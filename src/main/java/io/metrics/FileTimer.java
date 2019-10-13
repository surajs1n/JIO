package io.metrics;

public class FileTimer extends Timer {
    private int fileLen;
    private boolean isBufferUsed;
    private int bufferSize;

    public FileTimer() {
        super();
        this.fileLen = 0;
        this.isBufferUsed = false;
        this.bufferSize = 0;
    }

    public FileTimer(final boolean isBufferUsed) {
        super();
        this.fileLen = 0;
        this.isBufferUsed = isBufferUsed;
        this.bufferSize = 0;
    }

    public FileTimer(final int fileLen, final boolean isBufferUsed) {
        super();
        this.fileLen = fileLen;
        this.isBufferUsed = isBufferUsed;
        this.bufferSize = 0;
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

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public String toString() {
        return "FileTimer{" +
                "fileLen=" + fileLen +
                ", isBufferUsed=" + isBufferUsed +
                ", bufferSize=" + bufferSize +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }

    public String getClassHeaderInCSV() {
        if (this.isBufferUsed) {
            return "File Length , Buffer Size (Byte), Buffered Time Taken (ns)";
        } else {
            return "File Length , Non-Buffered Time Taken (ns)";
        }
    }

    public String getObjectDataInCSV() {
        if (this.isBufferUsed) {
            return String.format(this.fileLen + " , " + this.bufferSize + " , " + (getEndTime() - getStartTime()));
        } else {
            return String.format(this.fileLen + " , " + (getEndTime() - getStartTime()));
        }
    }
}
