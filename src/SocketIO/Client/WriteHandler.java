package SocketIO.Client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class WriteHandler implements Runnable {
    private Socket socket;

    public WriteHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        DataOutputStream dataOutputStream = null;
        Scanner scanner = new Scanner(System.in);
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String message;
            while (true) {
                System.out.print("Input > ");
                message = scanner.nextLine();

                // Check for an exit command to terminate the loop
                if ("exit".equalsIgnoreCase(message) || "quit".equalsIgnoreCase(message)) {
                    break; // Exit the loop
                }

                dataOutputStream.writeUTF(message);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (dataOutputStream != null)
                    dataOutputStream.close();
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}