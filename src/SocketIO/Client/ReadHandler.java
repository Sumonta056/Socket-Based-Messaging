package SocketIO.Client;

import java.io.DataInputStream;
import java.net.Socket;

public class ReadHandler implements Runnable {
    private Socket socket;
    private volatile boolean running = true;

    public ReadHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            while (running) {
                String message = dataInputStream.readUTF();
                System.out.println("\n"+ message +"\n");
                System.out.print("Input > ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (dataInputStream != null)
                    dataInputStream.close();
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}