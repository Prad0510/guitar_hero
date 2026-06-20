package com.example.guitar_hero;

import edu.princeton.cs.introcs.StdAudio;
import edu.princeton.cs.introcs.StdDraw;

public class GuitarHero {
    // Interactive client that simulates a 37-string guitar using the keyboard.2
    public static void main(String[] args) {
        String keyboard
                = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        int n = keyboard.length();
        // Visual cue to ensure the user clicks the window for focus
        StdDraw.text(0.5, 0.5, "Click here and press keys to play!");
        System.out.println("Initializing strings...");
        // Array to manage multiple GuitarString objects efficiently
        GuitarString[] strings = new GuitarString[n];
        for (int i = 0; i < n; i++) {
            // Calculate frequency based on the chromatic scale formula
            double frequency = 440.0 * Math.pow(2, (i - 24) / 12.0);
            strings[i] = new GuitarString(frequency);
        }
        System.out.println("Initialization complete. Ready to play.");

        // Infinite loop to continuously process audio samples
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    strings[index].pluck();
                }
                // If key is valid, pluck the corresponding string
                if (index != -1) {
                    System.out.println("Plucking key: " + key);
                    strings[index].pluck();
                }
            }
            // Compute the superposition (sum) of all string samples
            double sample = 0.0;
            for (int i = 0; i < n; i++) {
                sample += strings[i].sample();
            }
            StdAudio.play(sample);
            for (int i = 0; i < n; i++) {
                strings[i].tic();
            }
        }
    }
}
