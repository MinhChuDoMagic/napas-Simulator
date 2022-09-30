package com.opw.NapasSimulator.Service;

import com.opw.NapasSimulator.Repository.MessageRepository;
import com.opw.NapasSimulator.Processor.CustomPair;
import com.opw.NapasSimulator.Processor.MapperDataElement;
import com.opw.NapasSimulator.Processor.MessageISO;
import com.opw.NapasSimulator.Processor.Processor;
import com.opw.NapasSimulator.Socket.SocketServerInitialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ImplService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private Processor processor;
    @Autowired
    private MapperDataElement mapperDataElement;
    @Autowired
    private SocketServerInitialize socketServerInitialize;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImplService.class);


    @Scheduled(fixedDelay = 100)
    public void autoGetMessageRequest(){
        CustomPair<String,String> messageReceivePair = messageRepository.getMessageRequestInQueue();
        if(messageReceivePair == null){
            return;
        }
        processor.getInstance(mapperDataElement);
        String messageReceive = messageReceivePair.getValue();
        MessageISO temp = processor.parsMessage(messageReceive);

        if(temp.getMti().equals("0800") ){
            socketServerInitialize.getSocketAcquirerById(messageReceivePair.getKey()).sendMessage("003908100000000000000002016"+
                    temp.getDataElementContent().get(63));
            return;
        }

        String f63 = temp.getDataElementContent().get(63);
        String issuerId = temp.getDataElementContent().get(2).substring(0,6);
        LOGGER.info("Processing request: " + issuerId + " " + f63 + " " + messageReceive);
        messageRepository.putWaitingSocketAcquirerMap(f63, messageReceivePair.getKey());
        socketServerInitialize.getSocketIssuerById(issuerId).sendMessage(messageReceive);
    }

    @Scheduled(fixedDelay = 1)
    public void autoGetMessageResponse(){
        String messageResponse = messageRepository.getMessageResponseInQueue();
        if(messageResponse == null){
            return;
        }
        LOGGER.info("Processing response: " + messageResponse);
        processor.getInstance(mapperDataElement);
        MessageISO temp = processor.parsMessage(messageResponse);
        String f63 = temp.getDataElementContent().get(63);

        String acquirerId = messageRepository.getWaitingSocketAcquirerByF63(f63);
        socketServerInitialize.getSocketAcquirerById(acquirerId).sendMessage(messageResponse);
    }
}
