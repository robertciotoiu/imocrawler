package com.robertciotoiu.imocrawler.scheduler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NaturalScheduled {
    // time unit is milliseconds
    int min_frequency() default 1000;

    int max_frequency() default 2000;

    int initial_delay() default 0;

    boolean disabled() default false;
}

