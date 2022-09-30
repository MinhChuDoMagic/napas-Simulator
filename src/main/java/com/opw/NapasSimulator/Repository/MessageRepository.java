package com.opw.NapasSimulator.Repository;

import com.opw.NapasSimulator.Processor.CustomPair;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Repository
public class MessageRepository {
    private static Queue<CustomPair<String,String>> messageRequestQueue = new LinkedList<>();
    private static Queue<String> messageResponseQueue = new LinkedList<>();
    private Map<String, String> waitingSocketAcquirerMap = new HashMap<>();

    public void addIssuerMessage(String message){
        messageResponseQueue.add(message);
    }

    public void addAcquirerMessage(String acquirerId, String message){
        messageRequestQueue.add(CustomPair.of(acquirerId,message));
    }

    public CustomPair<String,String> getMessageRequestInQueue(){
        return messageRequestQueue.poll();
    }

    public String getMessageResponseInQueue(){
        return messageResponseQueue.poll();
    }

    public String getWaitingSocketAcquirerByF63(String f63){
        String socketAcquirerId = waitingSocketAcquirerMap.get(f63);
        waitingSocketAcquirerMap.remove(f63);
        return socketAcquirerId;
    }

    public void putWaitingSocketAcquirerMap(String f63, String acquirerId){
        waitingSocketAcquirerMap.put(f63, acquirerId);
    }
}
