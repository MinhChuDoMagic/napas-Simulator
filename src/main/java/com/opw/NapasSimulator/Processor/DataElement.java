package com.opw.NapasSimulator.Processor;

public class DataElement {
    private Integer number;
    private Integer length = 0;
    private Integer prefixLength = 0;
    private String type;
    private String lengthType;

    public Integer getNumber() {
        return number;
    }

    public DataElement setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public DataElement setLength(Integer length) {
        this.length = length;
        return this;
    }

    public Integer getPrefixLength() {
        return prefixLength;
    }

    public DataElement setPrefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public String getType() {
        return type;
    }

    public DataElement setType(String type) {
        this.type = type;
        return this;
    }

    public String getLengthType() {
        return lengthType;
    }

    public DataElement setLengthType(String lengthType) {
        this.lengthType = lengthType;
        return this;
    }
}
