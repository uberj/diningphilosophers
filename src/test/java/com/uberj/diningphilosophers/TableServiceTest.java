package com.uberj.diningphilosophers;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by uberj on 1/1/15.
 */
public class TableServiceTest extends TestCase {
   TableService tableService;
   TTableService.Client client;
   TSocket transport;
   TServerTransport serverTransport;

   private static final String HOST = "localhost";
   private static int SERVER_PORT = 9090;
   private static int NUM_PHILOSOPHERS = 3;
   TableLocation tableLocation = new TableLocation(HOST, SERVER_PORT);

   @Override
   public void setUp() {
      // Setup Server
      System.out.println("Initializing TestTTableServer...");
      // Create handler for client requests
      try {
         tableService = new TableService(NUM_PHILOSOPHERS, new BinaryTableClientFactory(tableLocation));
      } catch (Exception e) {
         Assert.fail("Failed to create table service: " + e.getMessage());
         return;
      }
      // Create a protocol that translates for the handler. We pass the processor an implementation of itself
      TTableService.Processor processor = new TTableService.Processor(tableService);
      // Spin up a threaded server to listen on a port and route requests to protocol -> handler
      try {
         serverTransport = new TServerSocket(tableLocation.port);
         final TThreadPoolServer server = new TThreadPoolServer(
                 new TThreadPoolServer.Args(serverTransport).processor(processor));

         Runnable server_task = new Runnable() {
            @Override
            public void run() {
               server.serve();
            }
         };
         new Thread(server_task).start();
      } catch (TTransportException e) {
         Assert.fail("Failed to initialize thrift server. Error: " + e.getMessage());
      }

      // Setup client
      System.out.println("Initializing TestTTable.Client...");
      try {
         transport = new TSocket(tableLocation.host, tableLocation.port);
         TProtocol protocol = new TBinaryProtocol(transport);
         client = new TTableService.Client(protocol);
         transport.open();
      } catch (TTransportException e) {
         Assert.fail("Failed to initialize thrift client. Error: " + e.getMessage());
      }
   }

   @Override
   public void tearDown() {
      System.out.println("Cleaning up sockets...");
      transport.close();
      serverTransport.close();
   }

   public void testPickUpFork() {
      final int NOPERMITS = 0;

      try {
         client.pickUpFork(new Fork(0));
      } catch (TException e) {
         Assert.fail("Failed to pickup fork 0: " + e.getMessage());
      }
      Assert.assertEquals(NOPERMITS, tableService.forkLocks[0].availablePermits());

      try {
         client.pickUpFork(new Fork(1));
      } catch (TException e) {
         Assert.fail("Failed to pickup fork 1: " + e.getMessage());
      }
      Assert.assertEquals(NOPERMITS, tableService.forkLocks[1].availablePermits());

      try {
         client.pickUpFork(new Fork(2));
      } catch (TException e) {
         Assert.fail("Failed to pickup fork 2: " + e.getMessage());
      }
      Assert.assertEquals(NOPERMITS, tableService.forkLocks[2].availablePermits());
   }

   public void testPickUpSetDownFork() {
      Fork fork = new Fork(0);
      try {
         client.pickUpFork(fork);
      } catch (TException e) {
         Assert.fail("Failed to pickup fork 0: " + e.getMessage());
      }

      try {
         client.setDownFork(fork);
      } catch (TException e) {
         Assert.fail("Failed to pickup fork 0: " + e.getMessage());
      }
   }
}
