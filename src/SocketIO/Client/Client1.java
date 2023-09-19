package SocketIO.Client;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client1 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Client1 Connected to Server");

            // Create a thread pool with a 2 number of threads
            ExecutorService threadPool = Executors.newFixedThreadPool(2);

            // Assigning the tasks (ReadHandler and WriteHandler) to the thread pool
            threadPool.submit(new ReadHandler(socket));
            threadPool.submit(new WriteHandler(socket));

            // Shutdown the thread pool when done
            threadPool.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
