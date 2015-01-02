package com.uberj.diningphilosophers;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by uberj on 1/2/15.
 */
public class BinaryTableClientFactory implements TableClientFactory {
    TableLocation tableLocation;

    public BinaryTableClientFactory(TableLocation tableLocation) {
        this.tableLocation = tableLocation;
    }

    public TTableService.Client getClient() {
        TSocket transport = new TSocket(tableLocation.host, tableLocation.port);
        TProtocol protocol = new TBinaryProtocol(transport);
        TTableService.Client client = new TTableService.Client(protocol);
        try {
            transport.open();
        } catch (TTransportException e) {
            System.out.println("Couldn't create client: " + e.getMessage());
        }
        return client;
    }
}
