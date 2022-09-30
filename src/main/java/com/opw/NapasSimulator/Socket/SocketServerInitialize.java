package com.opw.NapasSimulator.Socket;

import com.opw.NapasSimulator.Service.ImplService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SocketServerInitialize {
    private final ServerSocket serverSocket;
    private Map<String, SocketAcquirer> acquirersMap= new HashMap<>();
    private Map<String, SocketIssuer> issuersMap = new HashMap<>();

    public SocketServerInitialize(){
        try {
            this.serverSocket = new ServerSocket(1234);

            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(("src/main/resources/issuer.json"));
            JSONArray issuersArray = (JSONArray) jsonParser.parse(reader);
            issuersArray.forEach(i -> {
                String id = (String)((JSONObject)i).get("id");
                String ip = (String)((JSONObject)i).get("ip");
                int port = (int)(long)((JSONObject)i).get("port");

                try {
                    issuersMap.put(id, new SocketIssuer(port,ip,id));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SocketIssuer getSocketIssuerById(String id){
        return issuersMap.get(id);
    }

    public SocketAcquirer getSocketAcquirerById(String id){
        return acquirersMap.get(id);
    }

    @Scheduled(fixedDelay = 1)
    public void InitializeSocketIO(){
        try {
            SocketAcquirer socketAcquirer = new SocketAcquirer(serverSocket.accept());
            acquirersMap.put(socketAcquirer.getAcquirerId(), socketAcquirer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
