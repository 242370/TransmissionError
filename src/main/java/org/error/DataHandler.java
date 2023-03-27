package org.error;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataHandler {
    private final int bitsNumber = 8;

    public int[][] getData(byte[] fileData) {
        int[][] dataInBits = new int[fileData.length][bitsNumber];
        for (int i = 0; i < fileData.length; i++) {
            String bits = Integer.toBinaryString(fileData[i]);
            if (bits.length() != bitsNumber) {
                bits = insertString(bits, 0, 8);
            }
            for (int j = 0; j < bitsNumber; j++) {
                dataInBits[i][j] = Character.getNumericValue(bits.charAt(j));
            }
        }
        return dataInBits;
    }

    public int[][] getBitsData(String fileData, int length) {
        int[][] dataInBits = new int[fileData.length()/length][length];
        String []dataStorage = fileData.split(("(?<=\\G.{" + length + "})"));
        String [][]finalDataInString = new String[dataStorage.length][16];
        for (int i=0; i<dataStorage.length; i++) {
            finalDataInString[i] = dataStorage[i].split(("(?<=\\G.{" + 1 + "})"));
        }
        for (int i = 0; i < dataInBits.length; i++) {
            for(int j=0; j<16; j++) {
                dataInBits[i][j] = Integer.parseInt(finalDataInString[i][j]);
            }
        }

        return dataInBits;
    }

    private String insertString(String text, int value, int length) {
        String updatedText = new String();
        int diff = length - text.length();
        for (int i = 0; i < diff; i++) {
            updatedText += "0";
        }
        updatedText += text;
        return updatedText;
    }

    public void showData(int[][] data, String header) {
        System.out.println(header);
        for (int i = 0; i < data.length; i++) {
            int counter = i + 1;
            System.out.print("Row number " + counter + ": ");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public byte[] decode(int[][] codingWords) {
        byte[] b = new byte[codingWords.length];
        for (int i = 0; i < codingWords.length; i++) {
            char[] c = new char[bitsNumber];
            for (int j = 0; j < bitsNumber; j++) {
                c[j] = (char) (codingWords[i][j] + '0');
            }
            String s = new String(c);
            b[i] = (byte) Integer.parseInt(s, 2);
                    //Byte.parseByte(s, 2);
        }
        String dataToSave = new String(b);
        System.out.println(dataToSave);
        return b;
    }


}
