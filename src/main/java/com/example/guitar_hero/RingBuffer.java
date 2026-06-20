package com.example.guitar_hero;

public class RingBuffer {
    private int capacity;   // Maximum number of samples the string can hold
    private double[] buffer;    // Array to store the displacement samples
    private int first;  // Index of the least recently inserted item (front)
    private int last;   // Index one beyond the most recently inserted item (end)
    private int size;   // Current number of samples in the buffer

    // Creates an empty ring buffer with the specified capacity.
    public RingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.buffer = new double[capacity]; // Proportional time to capacity
        this.first = 0;
        this.last = 0;
        this.size = 0;
    }

    // Returns the capacity of the buffer (n = 44,100 / frequency).
    public int capacity() {
        return capacity;
    }

    // Returns the number of samples currently active in the buffer.
    public int size() {
        return size;
    }

    // Checks if the buffer is empty (used during initial string setup).
    public boolean isEmpty() {
        return size == 0;
    }

    // Checks if the buffer is full (used to verify displacement point limits).
    public boolean isFull() {
        return size == capacity;
    }

    // Adds item x to the end of the buffer using cyclic wrap-around.
    public void enqueue(double x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        buffer[last] = x;
        last = (last + 1) % capacity;   // Increment last index with wrap-around logic.
        size++;
    }

    // Deletes and returns the sample at the front of the buffer.
    public double dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        double value = buffer[first];
        first = (first + 1) % capacity; // Increment first index with wrap-around logic
        size--;
        return value;
    }

    // Returns (but does not delete) the front item; used for averaging in tic().
    public double peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return buffer[first];
    }

    // Tests the RingBuffer functionality to ensure cyclic behavior works as intended.
    public static void main(String[] args) {
        RingBuffer rb = new RingBuffer(5);
        System.out.println("Capacity: " + rb.capacity()); // 5
        System.out.println("Initially empty? " + rb.isEmpty());

        // Enqueue some values
        rb.enqueue(1.0);
        rb.enqueue(2.0);
        rb.enqueue(3.0);

        System.out.println("Size after 3 enqueues: " + rb.size());
        System.out.println("Peek front: " + rb.peek());   // 1.0

        // Dequeue a couple
        System.out.println("Dequeue: " + rb.dequeue());   // 1.0
        System.out.println("Dequeue: " + rb.dequeue());   // 2.0

        System.out.println("Size now: " + rb.size());
        System.out.println("Peek front: " + rb.peek());   // 3.0

        // Wrap-around behavior
        rb.enqueue(4.0);
        rb.enqueue(5.0);
        rb.enqueue(6.0);  // Should wrap last index

        System.out.println("Is full? " + rb.isFull());

        // drain buffer
        while (!rb.isEmpty()) {
            System.out.println("Dequeue: " + rb.dequeue());
        }

        System.out.println("Empty at end? " + rb.isEmpty());
    }
}
