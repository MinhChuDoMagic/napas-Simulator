package com.opw.NapasSimulator.Processor;

import java.util.HashMap;
import java.util.Map;

public class MessageISO {
    private String header;
    private String mti;
    private String primaryBitMap;
    private String secondaryBitMap;
    private String overralBitMap;
    Map<Integer, String> dataElementContent = new HashMap<>();

    // loi o day--------------------------=============================
    ConvertToBitmap convert = new ConvertToBitmap();

    public boolean hasSecondaryBitMap(char bit){
        if (bit == '1'){
            return true;
        }
        return false;
    }

    public boolean hasField(int i){
        return overralBitMap.charAt(i) == '1';
    }

    public void buildBitMap(){
        ConvertToBitmap buildBit = new ConvertToBitmap();
        String bitMap = buildBit.biToHex(this);
        this.primaryBitMap = bitMap.substring(0,16);
        this.overralBitMap = buildBit.hexToBinary(this.primaryBitMap);
        if (bitMap.length()>16){
            this.secondaryBitMap= bitMap.substring(16,32);
            this.overralBitMap += buildBit.hexToBinary(this.secondaryBitMap);
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getPrimaryBitMap() {
        return primaryBitMap;
    }

    public void setPrimaryBitMap(String primaryBitMap) {
        this.primaryBitMap = primaryBitMap;
        this.overralBitMap = convert.hexToBinary(primaryBitMap);
    }

    public String getSecondaryBitMap() {
        if (secondaryBitMap == null){
            return "";
        }
        return secondaryBitMap;
    }

    public void setSecondaryBitMap(String secondaryBitMap) {
        this.secondaryBitMap = secondaryBitMap;
        this.overralBitMap += convert.hexToBinary(secondaryBitMap);
    }

    public String getOverralBitMap() {
        return overralBitMap;
    }

    public void setOverralBitMap(String overralBitMap) {
        this.overralBitMap = overralBitMap;
    }

    public Map<Integer, String> getDataElementContent() {
        return dataElementContent;
    }

    public void setDataElement(Map<Integer, String> dataElement) {
        this.dataElementContent = dataElement;
    }

    public void printDataElement (){
        for (Integer key : this.dataElementContent.keySet()){
            System.out.println(key +" "+ this.dataElementContent.get(key));
        }
    }
}
