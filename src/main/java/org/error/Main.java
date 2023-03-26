package org.error;

public class Main {
    public static void main(String[] args) throws Exception {
        DataMaker bm = new DataMaker();
        CodingMatrix cm = new CodingMatrix();
        int [][]hehe = bm.getData();
        bm.schowData(hehe);
        for(int i=0; i<hehe.length; i++) {
            hehe[i] = cm.encode(hehe[i]);
        }
        System.out.println("encoded");
        int []dataToSave = new int[hehe.length * hehe[0].length];
        int counter = 0;
        for(int i=0; i<hehe.length; i++) {
            for(int j=0; j<hehe[i].length; j++) {
                dataToSave[counter] = hehe[i][j];
                counter++;
            }
        }
        bm.saveToFile(dataToSave);
        System.out.println("saved");
    }
}