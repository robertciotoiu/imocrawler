package com.robertciotoiu.imocrawler.scheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomIntTest {

    @Test
    public void getRandomNumber_ofPositiveValues_shouldReturnNumberFromThatInterval() {
        var number = RandomInt.getRandomNumber(400, 800);
        Assertions.assertTrue(number >= 400 && number <= 800);
    }

    @Test
    public void testGetRandomNumber_1000Times_toAssureRandomGenerationCorrectly() {
        for (int i = 0; i < 100; i++) {
            getRandomNumber_ofPositiveValues_shouldReturnNumberFromThatInterval();
        }
    }

}
