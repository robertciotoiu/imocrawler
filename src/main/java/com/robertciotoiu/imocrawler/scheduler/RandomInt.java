package com.robertciotoiu.imocrawler.scheduler;

import java.util.Random;

public class RandomInt {
    private static final Random RANDOM = new Random();

    public static int getRandomNumber(int min, int max) {
        int range = max - min + 1;

        // Generate a random number within the specified interval
        int randomNumber = RANDOM.nextInt(range) + min;
//        System.out.println("Random number within the interval [" + min + ", " + max + "]: " + randomNumber);
        return randomNumber;
    }
}
