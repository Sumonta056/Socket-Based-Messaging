package SocketIO.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

public class Server {
    private static final int MAX_THREADS = 13; // Maximum number of threads in the thread pool
    private static List<Socket> clients = new ArrayList<>(); // List to store client sockets

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS); //Creating a pool of 13 thread

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server Started");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client Added");
                clients.add(client); // Add the client socket to the list

                // Use the thread pool to handle client communication
                threadPool.execute(new ReadHandler(client, clients));
                threadPool.execute(new WriteHandler(client, clients));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
