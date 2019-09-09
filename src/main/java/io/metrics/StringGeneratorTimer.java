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

    public static String getClassHeaderInCSV() {
        return "String Length , Start Time (ns) , End Time (ns)";
    }

    public String getObjectDataInCSV() {
        return String.format(this.stringLen + " , " + getStartTime() + " , " + getEndTime());
    }
}
