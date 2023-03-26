package org.error;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataMaker {
    private final int bitsNumber = 8;

    public int[][] getData() {
        Path path = Paths.get("C:\\Users\\jkowa\\Desktop\\Studia\\semestr IV\\Telekomuna\\TransmissionError\\src\\main\\java\\org\\error\\data.txt");
        try {
            byte[] fileData = Files.readAllBytes(path);
            int [][]dataInBits = new int[fileData.length][8];
            for(int i=0; i<fileData.length; i++) {
                String bits = Integer.toBinaryString(fileData[i]);
                if(bits.length() != bitsNumber) {
                    bits = insertString(bits, 0);
                }
                for(int j=0; j<bitsNumber; j++) {
                    dataInBits[i][j] = Character.getNumericValue(bits.charAt(j));
                }
            }
            return dataInBits;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String insertString(String text, int value) {
        String updatedText = new String();
        int diff = bitsNumber - text.length();
        for(int i=0; i< diff; i++) {
            updatedText += "0";
        }
        updatedText += text;
        return updatedText;
    }

    public void schowData(int [][]data) {
        System.out.println("Length of bytes: " + data.length);
        for(int i=0; i<data.length; i++) {
            int counter = i+1;
            System.out.print("Row number " + counter + ": ");
            for(int j=0; j<data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }

    protected void saveToFile(int[] data) throws IOException {
        String s = new String();
        for (int i = 0; i < data.length; i++) {
            s += Integer.toString(data[i]);
        }
        Path path = Paths.get("data.bin");
        Files.write(path, s.getBytes());
    }
}
