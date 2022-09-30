package com.opw.NapasSimulator.Socket;

import com.opw.NapasSimulator.Repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

public class SocketAcquirer extends SocketIO {
    private static int idCount = 1;
    private final String acquirerId;
    private MessageRepository messageRepository = new MessageRepository();
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketAcquirer.class);


    SocketAcquirer(Socket socket) throws Exception {
            super(socket);

            this.acquirerId = Integer.toString(idCount++);
            LOGGER.info("Socket acquirer created: " + acquirerId);
            this.start();
    }

    public void run(){
        while (true){
            String messageReceive = getMessage();
            if(messageReceive == null){
                return;
            }
            LOGGER.info(messageReceive);
            messageRepository.addAcquirerMessage(acquirerId, messageReceive);
        }
    }

    public String getAcquirerId() {
        return acquirerId;
    }


}
