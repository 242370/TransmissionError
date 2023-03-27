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
                bits = insertString(bits, 0);
            }
            for (int j = 0; j < bitsNumber; j++) {
                dataInBits[i][j] = Character.getNumericValue(bits.charAt(j));
            }
        }
        return dataInBits;
    }

    private String insertString(String text, int value) {
        String updatedText = new String();
        int diff = bitsNumber - text.length();
        for (int i = 0; i < diff; i++) {
            updatedText += "0";
        }
        updatedText += text;
        return updatedText;
    }

    public void showData(int[][] data) {
        System.out.println("Number of bytes: " + data.length);
        for (int i = 0; i < data.length; i++) {
            int counter = i + 1;
            System.out.print("Row number " + counter + ": ");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public byte[] decode(int[][] codingWords) {
        byte[] b = new byte[codingWords.length];
        for (int i = 0; i < codingWords.length; i++) {
            char[] c = new char[bitsNumber];
            for (int j = 0; j < bitsNumber; j++) {
                c[j] = (char) (codingWords[i][j] + '0');
            }
            String s = new String(c);
            b[i] = Byte.parseByte(s, 2);
        }
        String dataToSave = new String(b);
        //System.out.println(dataToSave);
        return b;
    }


}
