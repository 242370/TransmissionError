package org.error;

import java.util.Arrays;

public class CodingMatrix {
    private final int M = 8; // długość kodowanych danych w bitach - liczba kolumn pierwszej części macierzy
    private final int N = 8; // liczba bitów kontroli parzystości - liczba wierszy macierzy H, 8 dla 2 bajtów finalnie
    private final int[][] twoBitMatrix = new int[][]{ // macierz H dla błędu 2-bitowego
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    // Kodowanie jednego bajtu, a więc i wszystkich bajtów pliku za pomocą ustalonej macierzy
    public int[] encode(int[] singleByte) throws Exception {
        if (singleByte.length != this.M) {
            throw new Exception("You have to give me one byte, therefore the length needs to be 8");
        }
        int[] codedByte = new int[this.M + this.N]; // zakodowany bajt powiększony o bity parzystości
        for (int i = 0; i < this.M; i++) // dla czytelności
        {
            codedByte[i] = singleByte[i]; // w zakodowanym bajcie początek to oryginał
        }
        for (int parityBit = 0; parityBit < this.N; parityBit++) {
            for (int i = 0; i < this.M; i++) {
                codedByte[this.M + parityBit] += singleByte[i] * this.twoBitMatrix[parityBit][i]; // mnożenie macierzy
            }
            codedByte[this.M + parityBit] = codedByte[this.M + parityBit] % 2; // ponieważ to bity możemy mieć tylko 1 | 2
        }
        return codedByte; // codedByte to wektor T
    }

    // Sprawdzanie czy zaszły błędy (max 2)
    // jeżeli zwrócimy false, to marker zawiera wytyczne do zlokalizowania błędu
    public boolean check(int[] codedByte, int[] marker) throws Exception {
        if (codedByte.length != this.M + this.N || marker.length != this.N) {
            throw new Exception("Incorrect lengths");
        }
        boolean result = true;
        int falseCounter = 0;
        Arrays.fill(marker, 0); // jeśli coś jest niepoprawnie, to marker to jedna z kolumn macierzy H
        for (int parityBit = 0; parityBit < this.N; parityBit++) {
            for (int i = 0; i < this.M + this.N; i++) {
                marker[parityBit] += codedByte[i] * this.twoBitMatrix[parityBit][i]; // mnożenie macierzy
            }
            marker[parityBit] = marker[parityBit] % 2; // jeśli nie wyjdzie 0, znaczy, że w tym miejscu jest błąd
            if (marker[parityBit] != 0) {
                result = false; // nie zwracam, żeby pętla wykonywała się dalej
//                falseCounter += 1;
//                if(falseCounter==3) {
//                    return result;
//                }
            }
        }
        return result;
    }


    // Poprawa błędów
    public void correct(int[] codedByte, int[] marker) throws Exception {
        if (codedByte.length != this.M + this.N || marker.length != this.N) {
            throw new Exception("Incorrect lengths");
        }
        int bit = check1(marker); // w tej kolumnie, a więc ten bit jest niepoprawny
        if (bit != -1) {
            codedByte[bit] = (codedByte[bit] + 1) % 2; // zamiana bitu na przeciwny
            return;
        }
        int[] bits = check2(marker);
        if (bits[0] != -1 && bits[1] != -1) {
            codedByte[bits[0]] = (codedByte[bit] + 1) % 2;
            System.out.println("po 1 zmianie");
            System.out.println("po 2 zmianie");
            codedByte[bits[1]] = (codedByte[bit] + 1) % 2;
            return;
        }
        else System.out.println("nie wszedlem w warunek");
    }


    // Zlokalizowanie pierwszego błędu
    private int check1(int[] marker) throws Exception {
        if (marker.length != this.N) {
            throw new Exception("Incorrect lengths");
        }
        int error = -1;
        for (int col = 0; col < this.N + this.M; col++) {
            boolean flag = true;
            for (int row = 0; row < this.N; row++) {
                if (this.twoBitMatrix[row][col] != marker[row]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return col;
            }
        }
        return error;
    }

    // Zlokalizowanie dwóch błędów
    private int[] check2(int[] marker) throws Exception {
        if (marker.length != this.N) {
            throw new Exception("Incorrect lengths");
        }
        int[] errors = new int[2];
        Arrays.fill(errors, -1); // ponieważ istnieje bit o indexie 0
        int[] colsSum = new int[this.N];
        for (int firstCol = 0; firstCol < this.N + this.M - 1; firstCol++) {
            for (int secondCol = firstCol + 1; secondCol < this.N + this.M; secondCol++) {
                Arrays.fill(colsSum, 0);
                for (int row = 0; row < this.N; row++) {
                    colsSum[row] = this.twoBitMatrix[row][firstCol] + this.twoBitMatrix[row][secondCol];
                }
                boolean flag = true;
                for (int row = 0; row < this.N; row++) {
                    if (colsSum[row] != marker[row]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    errors[0] = firstCol;
                    System.out.println("firstCol: "+firstCol);
                    errors[1] = secondCol;
                    System.out.println("secondCol: "+secondCol);
                    return errors;
                }
            }
        }
        return errors;
    }

}
