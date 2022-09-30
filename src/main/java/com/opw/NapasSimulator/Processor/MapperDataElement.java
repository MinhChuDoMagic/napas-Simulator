package com.opw.NapasSimulator.Processor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MapperDataElement {
    private MapperDataElement mapperDataElement;
    private Map<Integer, DataElement> dataElement = new HashMap<Integer,DataElement>();
    public MapperDataElement(){
        if(dataElement.isEmpty()){
            loadDataElement();
        }
    }
    public Map<Integer, DataElement> getDataElement() {
        return dataElement;
    }

    public void setDataElement(Map<Integer, DataElement> dataElement) {
        this.dataElement = dataElement;
    }

    public void loadDataElement(){
        if (!dataElement.isEmpty()){
            dataElement.clear();
        }
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("src/main/resources/ConfigMapper.json")){
            Object obj = parser.parse(reader);

            JSONArray dataElementList = (JSONArray) obj;

            dataElementList.forEach(elemnent -> {
                JSONObject emp = (JSONObject) elemnent;
                DataElement dataElemnt = new DataElement();

                int id = Math.toIntExact((Long) emp.get("id"));

                JSONObject DataElement = (JSONObject) emp.get("dataElement");

                dataElemnt.setNumber(Math.toIntExact((Long) DataElement.get("number")));

                String lengthType = (String) DataElement.get("lengthType");
                dataElemnt.setLengthType(lengthType);

                if (lengthType.equals("VARIABLE")){
                    dataElemnt.setPrefixLength(Math.toIntExact((Long) DataElement.get("prefixLength")));
                }else{
                    dataElemnt.setLength(Math.toIntExact((Long) DataElement.get("length")));
                }
                dataElemnt.setType((String) DataElement.get("type"));
                this.dataElement.put(id,dataElemnt);
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
