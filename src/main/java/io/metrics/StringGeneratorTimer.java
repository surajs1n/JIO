package io.metrics;

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

    public String getClassHeaderInCSV() {
        return "String Length , Time Taken (ns)";
    }

    public String getObjectDataInCSV() {
        return String.format(this.stringLen + " , " + (getEndTime() - getStartTime()));
    }
}
