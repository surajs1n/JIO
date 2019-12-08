package io.metrics;

/**
 * Base timer class to metrics.
 */
public class Timer {
    private long startTime;
    private long endTime;

    public Timer() {
        this.startTime = this.endTime = 0;
    }

    public void setStartTime(final long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "Timer{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
