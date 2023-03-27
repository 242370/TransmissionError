package org.error;

public class Main {
    public static void main(String[] args) throws Exception {
        DataHandler bm = new DataHandler();
        CodingMatrix cm = new CodingMatrix();
        FileHandler fh = new FileHandler();

        //Pobierz dane z pliku i przekonwertuj na binarke w systemie dwojkowym
        int[][] hehe = bm.getData(fh.readFromFile("data.txt"));


        //wyswietl bity kolejnych bajtow danych
        bm.showData(hehe);

        //Zakoduj kazdy bajt (stworz slowa kodowe poprzez dodanie bitow parzystosci)
        for (int i = 0; i < hehe.length; i++) {
            hehe[i] = cm.encode(hehe[i]);
        }
        System.out.println("encoded");

        //Stworz wektor bledu, ktory bedzie uzyty do sprawdzenia poprawnosci danych
        int[] marker = new int[cm.getN()];

        //Wprowadz blad do danych - symulaja bledu transmisji
        hehe[2][1] = 0;
        //dwa bledy jeszcze nie dzialaja!!!!
        hehe[2][2] = 0;

        //Zapisz bledne dane do pliku
        int counter = 0;
        int[] dataToSave = new int[hehe.length * hehe[0].length];

        for (int i = 0; i < hehe.length; i++) { //konwersja na jeden wymiar
            for (int j = 0; j < 8; j++) {
                dataToSave[counter] = hehe[i][j];
                counter++;
            }
        }
        fh.saveToFile(dataToSave, "wrong_data.bin");



        //sprawdz poprawnosc zakodowanych danych
        boolean isGood = cm.check(hehe[2], marker);
        for (int i = 0; i < marker.length; i++) {
            System.out.print(marker[i]);
        }
        System.out.println();

        //pokaz czy dane faktycznie sa bledne
        bm.showData(hehe);


        if (isGood) {
            System.out.println("nothing to correct");
        } else {
            System.out.println("bit or 2 bits to correct");
        }

        //Popraw wczesniej zasymulowany blad - teraz dane beda poprawne
        cm.correct(hehe[2], marker);

        //Pokaz czy dane faktycznie sa poprawne
        bm.showData(hehe);

        //Ponownie sprawdz poprawnosc danych - juz nie powinno byc bledu
        isGood = cm.check(hehe[2], marker);

        if (isGood) {
            System.out.println("nothing to correct");
        } else {
            System.out.println("bit or two bits to correct");
        }

        //Zapisz poprawne dane do pliku
        counter = 0;
        dataToSave = new int[hehe.length * hehe[0].length];
        for (int i = 0; i < hehe.length; i++) { //konwersja na jeden wymiar
            for (int j = 0; j < hehe[i].length; j++) {
                dataToSave[counter] = hehe[i][j];
                counter++;
            }
        }
        fh.saveToFile(dataToSave, "correct_data.bin");

        //Pokaz dane ktore beda dekodowane
        bm.showData(hehe);

        //Zapisz dane w postaci tekstowej (po zdekodowaniu) do pliku
        fh.saveToFile(bm.decode(hehe), "decodedData.txt");
    }
}