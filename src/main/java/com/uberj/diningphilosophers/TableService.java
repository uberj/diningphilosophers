package com.uberj.diningphilosophers;


import org.apache.thrift.TException;

import java.util.concurrent.Semaphore;

/**
 * Created by uberj on 12/29/14.
 */
public class TableService implements TTableService.Iface {
    Philosopher[] philosophers;
    Fork[] forks;
    Semaphore[] forkLocks;

    public TableService(int numPhilosophers, TableClientFactory clientFactory) throws Exception {
        int numForks = numPhilosophers;
        if (numForks < 1) {
            throw new Exception("not enough thinkers!");
        }
        philosophers = new Philosopher[numPhilosophers];
        for (int seatNo=0; seatNo < numPhilosophers; seatNo++) {
            philosophers[seatNo] = new Philosopher(seatNo, numPhilosophers, clientFactory.getClient());
        }

        forks = new Fork[numForks];
        for (int forkNo=0; forkNo < numForks; forkNo++) {
            forks[forkNo] = new Fork(forkNo);
        }

        forkLocks = new Semaphore[numForks];
        for (int lockNo=0; lockNo < numForks; lockNo++) {
            forkLocks[lockNo] = new Semaphore(1);
        }

    }

    @Override
    public boolean pickUpFork(Fork fork) throws TException {
        System.out.println("In server Picking up fork: "+fork.getTablePosition());
        try {
            forkLocks[fork.getTablePosition()].acquire();
        } catch (InterruptedException e) {
            throw new TableException("We were interrupted: " + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean setDownFork(Fork fork) throws TException {
        System.out.println("In server setting down fork: "+fork.getTablePosition());
        forkLocks[fork.getTablePosition()].release();
        return true;
    }

    @Override
    public boolean foo() throws TException {
        System.out.println("received a foo()");
        return false;
    }
}
