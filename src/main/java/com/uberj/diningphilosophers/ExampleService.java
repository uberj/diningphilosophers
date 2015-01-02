package com.uberj.diningphilosophers;

/**
 * Created by uberj on 12/22/14.
 */
import com.google.common.util.concurrent.AbstractScheduledService;

import java.util.concurrent.TimeUnit;

public class ExampleService extends AbstractScheduledService {
    @Override
    protected void runOneIteration() throws Exception {
        System.out.println("I was called");
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 1, TimeUnit.SECONDS);
    }
}
