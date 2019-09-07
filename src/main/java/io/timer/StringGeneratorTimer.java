package io.timer;

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
}
