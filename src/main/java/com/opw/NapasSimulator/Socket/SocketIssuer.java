package com.opw.NapasSimulator.Socket;

import com.opw.NapasSimulator.Repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

public class SocketIssuer extends SocketIO {
    private final String issuerId;
    private MessageRepository messageRepository = new MessageRepository();
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketIssuer.class);


    SocketIssuer(int port, String ip, String id) throws Exception {
        super(new Socket(ip, port));
        this.issuerId = id;
        LOGGER.info("Socket issuer created: " + issuerId);
        this.start();
    }

    public void run(){
        while (true){
            String messageReceive = getMessage();
            if(messageReceive == null){
                return;
            }
            LOGGER.info(messageReceive);
            messageRepository.addIssuerMessage(messageReceive);
        }
    }

    public String getIssuerId() {
        return issuerId;
    }



}
