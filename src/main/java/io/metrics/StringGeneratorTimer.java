package io.metrics;

/**
 * Random string generator metrics captor.
 */
public class StringGeneratorTimer extends Timer {
    private long stringLen;

    public StringGeneratorTimer() {
        super();
        this.stringLen = 0;
    }

    public long getStringLen() {
        return this.stringLen;
    }

    public void setStringLen(final long stringLen) {
        this.stringLen = stringLen;
    }

    @Override
    public String toString() {
        return "StringGeneratorTimer{" +
                "stringLen=" + stringLen +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }

    public String getClassHeaderInCSV() {
        return "String Length , Time Taken (ns)";
    }

    public String getObjectDataInCSV() {
        return String.format(this.stringLen + " , " + (getEndTime() - getStartTime()));
    }
}
