public class GuitarString {
    private static final int SAMPLE_RATE = 44100;    // Standard sampling rate in Hz
    // Models energy dissipation over a round trip
    private static final double DECAY = 0.996;
    private RingBuffer buffer;  // Stores the displacement samples

    // Creates a string of a given frequency.
    public GuitarString(double frequency) {
        // n equals sample rate / frequency, rounded up
        int n = (int) Math.ceil(
                SAMPLE_RATE / frequency);
        buffer = new RingBuffer(n);
        // Initialize at rest by filling the buffer with zeros
        for (int i = 0; i < n; i++) {
            buffer.enqueue(0.0);
        }
    }

    // Alternative constructor for testing and debugging.
    public GuitarString(double[] init) {
        buffer = new RingBuffer(init.length);
        for (double v : init) {
            buffer.enqueue(v);
        }
    }

    // Returns the total number of samples (the length of the string).
    public int length() {
        return buffer.capacity();
    }

    /**
     * Excites the string with white noise.
     * Replaces all samples with random values between -0.5 and +0.5.
     */
    public void pluck() {
        int n = buffer.capacity();
        for (int i = 0; i < n; i++) {
            buffer.dequeue();
            double noise = StdRandom.uniformDouble(-0.5,
                                                   0.5);
            buffer.enqueue(noise);
        }
    }

    /**
     * Advances the simulation by one time step.
     * Uses a low-pass filter (averaging) and decay to simulate vibration.
     */
    public void tic() {
        double first = buffer.dequeue();
        double second = buffer.peek();
        double newSample = DECAY * (first + second) / 2.0;
        buffer.enqueue(newSample);
    }

    // Returns the current displacement sample at the front of the buffer.
    public double sample() {
        return buffer.peek();
    }

    // Direct test client for the GuitarString class.
    public static void main(String[] args) {
        GuitarString g = new GuitarString(440.0);
        System.out.println("Length: " + g.length());
        g.pluck();
        System.out.println("Sample after pluck: " + g.sample());
        for (int i = 0; i < 5; i++) {
            g.tic();
            System.out.println("Sample after tic: " + g.sample());
        }
    }
}
