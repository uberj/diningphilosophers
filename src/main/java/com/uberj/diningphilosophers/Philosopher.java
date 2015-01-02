package com.uberj.diningphilosophers;

import com.google.common.util.concurrent.AbstractScheduledService;

import java.util.concurrent.TimeUnit;

/**
 * Created by uberj on 1/2/15.
 */
public class Philosopher extends AbstractScheduledService {
    TTableService.Client client;
    private int seatNo;
    private int maxSeatNo;

    public Philosopher(int seatNo, int maxSeatNo, TTableService.Client client) {
        this.seatNo = seatNo;
        this.maxSeatNo = maxSeatNo;
        this.client = client;
    }

    @Override
    protected void runOneIteration() throws Exception {
        System.out.println("I was called");
    }

    protected void pickUpForks() throws Exception {
        System.out.println("I was called");
    }

    protected void eatAndThink() throws Exception {
        System.out.println("[%s] I'm thinking" seatNo);
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(0, 1, TimeUnit.SECONDS);
    }
}
