package com.uberj.diningphilosophers;


import org.apache.thrift.TException;

/**
 * Created by uberj on 12/29/14.
 */
public class TableService implements TTableService.Iface {
    @Override
    public boolean pickUpFork(Fork fork) throws TException {
        System.out.println("In server Picking up fork");
        return false;
    }

    @Override
    public boolean setDownFork(Fork fork) throws TException {
        return false;
    }

    @Override
    public boolean foo() throws TException {
        System.out.println("received a foo()");
        return false;
    }
}
