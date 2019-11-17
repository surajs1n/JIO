package io.model;

/**
 * Input model class for describing input parameter for varying buffer size testing.
 */
public class BufferSizeInput {

    private final int minSize;
    private final int maxSize;
    private final int deltaSize;

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getDeltaSize() {
        return deltaSize;
    }

    public BufferSizeInput(final Builder builder) {
        this.minSize = builder.minSize;
        this.maxSize = builder.maxSize;
        this.deltaSize = builder.deltaSize;
    }

    public static class Builder {
        private int minSize;
        private int maxSize;
        private int deltaSize;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
            this.minSize = 5;
            this.maxSize = 5000;
            this.deltaSize = 5;
        }

        public Builder minSize(final int minSize) {
            this.minSize = minSize;
            return this;
        }

        public Builder maxSize(final int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder deltaSize(final int deltaSize) {
            this.deltaSize = deltaSize;
            return this;
        }

        public BufferSizeInput build() {
            return new BufferSizeInput(this);
        }
    }
}
