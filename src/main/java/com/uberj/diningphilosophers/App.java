package com.uberj.diningphilosophers;

import com.google.common.util.concurrent.Service;

/**
 * Dining Philosophers Toy Implementation
 * Objects and their thrift services:
 *  * Philosopher -- Picks up one fork, picks up another, thoughtfully eats for a while and then puts down the forks
 *      consumes:
 *          table.pickUpFork
 *          table.setDownFork
 *      services:
 *          - getState
 *  * Table -- Hosts all the philosophers and holds forks while philosophers are not using them
 *      services:
 *          - pickUpFork
 *          - setDownFork
 *  * Fork -- philosophers pick these up. They can also be on the table
 *  * Observer
 *      consumes:
 *          philosopher.getState
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Service s = new ExampleService();
        s.startAsync();
        while (true) {
            continue;
        }
    }
}
