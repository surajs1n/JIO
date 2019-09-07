package io.timer;

public class OutputFileTimer extends Timer {
    private int fileLen;
    private boolean isBufferUsed;

    public OutputFileTimer() {
        super();
        this.fileLen = 0;
        this.isBufferUsed = false;
    }

    public OutputFileTimer(final int fileLen, final boolean isBufferUsed) {
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
        return "OutputFileTimer{" +
                "fileLen=" + fileLen +
                ", isBufferUsed=" + isBufferUsed +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }
}
