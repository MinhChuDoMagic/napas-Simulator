package com.opw.NapasSimulator.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketIO extends Thread {
    private final Socket socket;
    private final BufferedInputStream input;
    private final PrintWriter output;
    private boolean connected = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketIO.class);


    SocketIO(Socket socket){
        try {
            this.socket = socket;
            this.connected = true;
            this.output = new PrintWriter(socket.getOutputStream(), true);
            this.input = new BufferedInputStream(socket.getInputStream());
            this.connected = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        try {
//            LOGGER.info("Send message");
            if (socket.isConnected()) {
                output.print(message);
                output.flush();
            } else {
                System.out.println("Fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeElements(socket, input, output);
        }
    }

    public String getMessage() {
        try {
            String respond = "";
            byte[] lent = new byte[4];
            input.read(lent);
            String lengt = new String(lent);
            byte[] message = new byte[Integer.parseInt(lengt)];
            input.read(message);
            String messageContent = new String(message);
            respond = lengt + messageContent;
            return respond;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void closeElements(Socket socket, BufferedInputStream input, PrintWriter output) {
        try {
            if (socket != null)
                socket.close();
            if (input != null)
                input.close();
            if (output != null)
                output.close();
            this.connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
