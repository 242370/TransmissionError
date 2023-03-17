package org.error;
public class CodingMatrix {
    private final int M = 8; // długość kodowanych danych w bitach - liczba kolumn pierwszej części macierzy
    private final int N = 8; // liczba bitów kontroli parzystości - liczba wierszy macierzy H, 8 dla 2 bajtów finalnie
    private final int[][] twoBitMatrix = new int[][]{ // macierz H dla błędu 2-bitowego
            {1, 1, 1, 1, 1, 1, 1, 1,  1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 1, 1,  0, 1, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 1, 1, 0, 1,  0, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 1, 0, 1,  0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 1,  0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 0,  0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 1, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0,  0, 0, 0, 0, 0, 0, 0, 1}
    };

    // Kodowanie jednego bajtu, a więc i wszystkich bajtów pliku za pomocą ustalonej macierzy
    public int[] encode(int[] singleByte) throws Exception
    {
        if(singleByte.length != this.M)
        {
            throw new Exception("You have to give me one byte, therefore the length needs to be 8");
        }
        int[] codedByte = new int[this.M + this.N]; // zakodowany bajt powiększony o bity parzystości
        for(int i = 0 ; i < this.M ; i++)
        {
            codedByte[i] = singleByte[i]; // w zakodowanym bajcie początek to oryginał
        }
        for(int parityBit = 0 ; parityBit < this.N ; parityBit++)
        {
            for(int i = 0 ; i < this.M ; i++)
            {
                codedByte[this.M + parityBit] += singleByte[i] * this.twoBitMatrix[parityBit][i]; // iloczyn s wektorów
            }
            codedByte[this.M + parityBit] = codedByte[this.M + parityBit] % 2; // ponieważ to bity możemy mieć tylko 1 | 2
        }
        return codedByte; // codedByte to wektor T
    }

    // Sprawdzanie czy zaszły błędy (max 2)
    public boolean check(int[] codedByte)
    {
        boolean result = true;
        int[] marker = new int[this.N]; // wskazanie, na którym miejscu jest błąd
        for(int parityBit = 0 ; parityBit < this.N ; parityBit++)
        {
            for(int i = 0 ; i < this.M + this.N ; i++)
            {
                marker[parityBit] += codedByte[i] * this.twoBitMatrix[parityBit][i];
            }
            marker[parityBit] = marker[parityBit] % 2; // jeśli nie wyjdzie 0, znaczy, że w tym miejscu jest błąd
            if(marker[parityBit] != 0)
            {
                result = false; // nie zwracam, żeby pętla wykonywała się dalej
            }
        }
        return result;
    }
}
