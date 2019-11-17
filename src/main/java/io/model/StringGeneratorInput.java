package io.model;

/**
 * Input model class of random string generator.
 */
public class StringGeneratorInput {

    private final long minLen;
    private final long maxLen;
    private final long deltaLen;
    private final long numberOfCopies;

    public long getMinLen() {
        return minLen;
    }

    public long getMaxLen() {
        return maxLen;
    }

    public long getDeltaLen() {
        return deltaLen;
    }

    public long getNumberOfCopies() {
        return numberOfCopies;
    }

    public StringGeneratorInput(final Builder builder) {
        this.minLen = builder.minLen;
        this.maxLen = builder.maxLen;
        this.deltaLen = builder.deltaLen;
        this.numberOfCopies = builder.numberOfCopies;
    }

    public static class Builder {
        private long minLen;
        private long maxLen;
        private long deltaLen;
        private long numberOfCopies;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
            this.minLen = 100;
            this.maxLen = 100000;
            this.deltaLen = 50;
            this.numberOfCopies = 1999;
        }

        public Builder minLen(final long minLen) {
            this.minLen = minLen;
            return this;
        }

        public Builder maxLen(final long maxLen) {
            this.maxLen = maxLen;
            return this;
        }

        public Builder deltaLen(final long deltaLen) {
            this.deltaLen = deltaLen;
            return this;
        }

        public Builder numberOfCopies(final long numberOfCopies) {
            this.numberOfCopies = numberOfCopies;
            return this;
        }

        public StringGeneratorInput build() {
            return new StringGeneratorInput(this);
        }
    }
}
