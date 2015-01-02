package com.uberj.diningphilosophers;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;

/**
 * Created by uberj on 1/1/15.
 */
public class TableServiceTest extends TestCase {
   TTableService.Client client;

   private static final String HOST = "localhost";
   private static int SERVER_PORT = 9090;
   @Override
   public void setUp() {
      // Setup Server
      System.out.println("Initializing TestTTableServer...");
      // Create handler for client requests
      TableService handler = new TableService();
      // Create a protocol that translates for the handler. We pass the processor an implementation of itself
      TTableService.Processor processor = new TTableService.Processor(handler);
      // Spin up a threaded server to listen on a port and route requests to protocol -> handler
      try {
         TServerTransport serverTransport = new TServerSocket(SERVER_PORT);
         final TThreadPoolServer server = new TThreadPoolServer(
                 new TThreadPoolServer.Args(serverTransport).processor(processor));

         Runnable server_task = new Runnable() {
            @Override
            public void run() {
               server.serve();
            }
         };
         new Thread(server_task).start();
      } catch (Exception e) {
         Assert.fail("Failed to initialize thrift server. Error: " + e.getMessage());
      }

      // Setup client
      System.out.println("Initializing TestTTable.Client...");
      try {
         TSocket transport = new TSocket(HOST, SERVER_PORT);
         TProtocol protocol = new TBinaryProtocol(transport);
         client = new TTableService.Client(protocol);
         transport.open();
      } catch (Exception e) {
         Assert.fail("Failed to initialize thrift client. Error: " + e.getMessage());
      }
   }

   public void testPickUpFork() {
      try {
         client.pickUpFork(new Fork(1, 1));
      } catch (Exception e) {
         Assert.fail("Failed to send a foo: " + e.getMessage());
      }

   }
}
