package org.error;

public class Main {
    static DataHandler bm = new DataHandler();
    static CodingMatrix cm = new CodingMatrix();
    static FileHandler fh = new FileHandler();

    private static void encodeAndSave() throws Exception {

        //Pobierz dane z pliku i przekonwertuj na binarke w systemie dwojkowym
        int[][] data = bm.getData(fh.readFromFile("data.txt"));

        //Zakoduj kazdy bajt (stworz slowa kodowe poprzez dodanie bitow parzystosci)
        for (int i = 0; i < data.length; i++) {
            data[i] = cm.encode(data[i]);
        }
        System.out.println("encoded");
        bm.showData(data, "Zakodowane dane:");

        int counter = 0;
        int[] dataToSave = new int[data.length * 16];

        for (int i = 0; i < data.length; i++) { //konwersja na jeden wymiar
            for (int j = 0; j < 16; j++) {
                dataToSave[counter] = data[i][j];
                counter++;
            }
        }
        fh.saveToFile(dataToSave, "encoded_data.bin");
    }

    private static void repairAndSaveDecoded(String fiename) throws Exception {
        //wczytaj zmienione recznie dane (bledne)
        int[][] data = bm.getBitsData(fh.readFromEncodedFile("encoded_data.bin"), 16);
        bm.showData(data, "Dane z zasymulowanym przez nas bledem");

        for(int i=0; i<data.length; i++) {
            //Stworz wektor bledu, ktory bedzie uzyty do sprawdzenia poprawnosci danych
            int[] marker = new int[cm.getN()];
            if(!cm.check(data[i], marker)) {
                cm.correct(data[i], marker);
            }
        }
        bm.showData(data, "Poprawione dane");
        //Zapisz dane w postaci tekstowej (po zdekodowaniu) do pliku
        fh.saveToFile(bm.decode(data), "decodedData.txt");

    }

    public static void main(String[] args) throws Exception {
          //encodeAndSave();
          repairAndSaveDecoded("encoded_data.bin");
    }
}
