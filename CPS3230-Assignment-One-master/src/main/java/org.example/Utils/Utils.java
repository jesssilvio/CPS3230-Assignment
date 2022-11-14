package org.example.Utils;

public class Utils {
    public Utils(){}

    public int parsePrice(String price){
        int multiplier = 100;
        String newPrice = "";

        if (price.contains(".")){
            multiplier = 1;
        }

        for (int i = 0; i < price.length(); i ++){
            char currentChar = price.charAt(i);
            if (currentChar == '0' || currentChar == '1' || currentChar == '2' || currentChar == '3' || currentChar == '4' || currentChar == '5' || currentChar == '6' || currentChar == '7' || currentChar == '8' || currentChar == '9'){
                newPrice += currentChar;
            }
        }

        return (Integer.parseInt(newPrice) * multiplier);
    }
}
