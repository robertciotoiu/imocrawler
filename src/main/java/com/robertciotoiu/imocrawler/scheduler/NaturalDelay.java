package com.robertciotoiu.imocrawler.scheduler;

public class NaturalDelay {
    public static void naturalAwait() throws InterruptedException {
        Thread.sleep(RandomInt.getRandomNumber(400, 1200));
    }
}
