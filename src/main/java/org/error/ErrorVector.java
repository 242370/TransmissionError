package org.error;
//Hopefully I will finish that class in the future :/
public class ErrorVector {
    private int numberOfParityBits = 8;
    private int []vector = new int[numberOfParityBits];

    public void displayVector() {
        System.out.println("Error Vector content: ");
        for(int i=0; i<numberOfParityBits; i++) {
            System.out.println(vector[i]);
        }
    }
}
