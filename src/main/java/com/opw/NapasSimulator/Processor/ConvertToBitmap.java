package com.opw.NapasSimulator.Processor;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ConvertToBitmap {
    public String hexToBinary(String hex) {

        String binary = "";

        hex = hex.toUpperCase();

        HashMap<Character, String> hashMap = new HashMap<Character, String>();

        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        int i;
        char ch;

        for (i = 0; i < hex.length(); i++) {

            ch = hex.charAt(i);

            if (hashMap.containsKey(ch))

                binary += hashMap.get(ch);

            else {
                binary = "Invalid Hexadecimal String";
                return binary;
            }
        }
        return binary;
    }

    public String binaryFromElement(MessageISO messageISO){
        StringBuffer bitmap = new StringBuffer();
        int lenght = 128;

        for (int i = 1 ; i <= lenght; i++ ){
            if(messageISO.getDataElementContent().containsKey(i)){
                bitmap.append('1');
                if(i == 38){
                    System.out.println(i);
                }
                continue;
            }
            bitmap.append('0');
        }
        if (bitmap.lastIndexOf("1")>64){
            bitmap.setCharAt(0,'1');
        }else{
            return bitmap.substring(0,64).toString();
        }
        return bitmap.toString();
    }

    public String biToHex(MessageISO messageISO){
        String hex = "";
        String bi= binaryFromElement(messageISO);
        HashMap<String, Character> hashMap = new HashMap<>();
        hashMap.put("0000",'0');
        hashMap.put("0001",'1');
        hashMap.put("0010",'2');
        hashMap.put("0011",'3');
        hashMap.put("0100",'4');
        hashMap.put("0101",'5');
        hashMap.put("0110", '6');
        hashMap.put("0111", '7');
        hashMap.put("1000", '8');
        hashMap.put("1001", '9');
        hashMap.put("1010", 'A');
        hashMap.put("1011", 'B');
        hashMap.put("1100", 'C');
        hashMap.put("1101", 'D');
        hashMap.put("1110", 'E');
        hashMap.put("1111", 'F');
        for(int i = 0; i < bi.length(); i+=4){
            hex += hashMap.get(bi.substring(i,i+4));
        }
        return hex;
    }
}

