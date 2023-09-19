package SocketIO.Server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class WriteHandler implements Runnable {
    private Socket client;
    private List<Socket> clients;

    public WriteHandler(Socket client, List<Socket> clients) {
        this.client = client;
        this.clients = clients;
    }

    public void run() {
        DataOutputStream dataOutputStream = null;
        Scanner scanner = new Scanner(System.in);

        try {
            dataOutputStream = new DataOutputStream(client.getOutputStream());
            String message;
            while (true) {
                //System.out.print("Server > ");
                message = scanner.nextLine();

                // Send the message to the client
                dataOutputStream.writeUTF(message);

                // Broadcast the message to all connected clients except the sender
                for (Socket connectedClient : clients) {
                    if (connectedClient != client) {
                        try {
                            DataOutputStream clientOutputStream = new DataOutputStream(connectedClient.getOutputStream());
                            clientOutputStream.writeUTF(message);
                        } catch (Exception ex) {
                            System.out.println("Error broadcasting message to a client: " + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error writing to client: " + e.getMessage());
        } finally {
            try {
                if (dataOutputStream != null)
                    dataOutputStream.close();
                if (client != null)
                    client.close();
                clients.remove(client);
            } catch (Exception e) {
                System.out.println("Error closing client connection: " + e.getMessage());
            }
        }
    }
}
