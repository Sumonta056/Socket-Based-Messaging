package SocketIO.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

public class ReadHandler implements Runnable {
    private Socket client;
    private List<Socket> clients;

    public ReadHandler(Socket client, List<Socket> clients) {
        this.client = client;
        this.clients = clients;
    }

    public void run() {
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(client.getInputStream());
            String message;
            while (true) {
                message = dataInputStream.readUTF();
                System.out.println("Client says: " + message);

                // Broadcast the message to all connected clients except the sender
                for (Socket connectedClient : clients) {
                    if (connectedClient != client) {
                        try {
                            DataOutputStream clientOutputStream = new DataOutputStream(connectedClient.getOutputStream());
                            clientOutputStream.writeUTF( "Client says: " + message );
                        } catch (Exception ex) {
                            System.out.println("Error broadcasting message to a client: " + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading client input: " + e.getMessage());
        } finally {
            try {
                if (dataInputStream != null)
                    dataInputStream.close();
                if (client != null)
                    client.close();
                clients.remove(client);
            } catch (Exception e) {
                System.out.println("Error closing client connection: " + e.getMessage());
            }
        }
    }
}
