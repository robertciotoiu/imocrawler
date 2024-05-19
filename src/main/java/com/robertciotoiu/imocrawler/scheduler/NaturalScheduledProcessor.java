package com.robertciotoiu.imocrawler.scheduler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class NaturalScheduledProcessor implements BeanPostProcessor {

    @SuppressWarnings("NullableProblems")
    @Override
    public Object postProcessBeforeInitialization(Object obj, String beanName) throws BeansException {
        return obj;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        for (java.lang.reflect.Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(NaturalScheduled.class)) {
                NaturalScheduled annotation = method.getAnnotation(NaturalScheduled.class);
                Runnable task = getRunnable(bean, method, annotation);
                new Thread(task).start();
            }
        }
        return bean;
    }


    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait", "CallToPrintStackTrace"})
    private static Runnable getRunnable(Object obj, Method method, NaturalScheduled annotation) {
        boolean disabled = annotation.disabled();

        if (disabled)
            return () -> {
            };

        int min_frequency = annotation.min_frequency();
        int max_frequency = annotation.max_frequency();
        int initialDelay = annotation.initial_delay();

        if (min_frequency < 0) {
            throw new IntervalUnderFlowException("Interval minus randomizer range is less than 0. Adjust the values to be positive.");
        }

        return () -> {
            try {
                Thread.sleep(initialDelay);
                while (true) {
                    method.invoke(obj);
                    Thread.sleep(RandomInt.getRandomNumber(min_frequency, max_frequency));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private static class IntervalUnderFlowException extends RuntimeException {
        public IntervalUnderFlowException(String s) {
            super(s);
        }
    }
}


