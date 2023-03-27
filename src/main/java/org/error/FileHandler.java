package org.error;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    private final int bitsNumber = 8;

    public void saveToFile(int[] data, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.write(path, convertIntArrayToString(data).getBytes());
        System.out.println("Saved successfully");
    }
    public void saveToFile(byte []data, String filename) throws IOException {
        Path path = Paths.get(filename);
        Files.write(path, data);
        System.out.println("Saved successfully");
    }

    private String convertIntArrayToString(int []data) {
        String textData = new String();
        for (int i = 0; i < data.length; i++) {
            textData += Integer.toString(data[i]);
        }
        return textData;
    }

    public byte []readFromFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);
        return data;
    }

}
