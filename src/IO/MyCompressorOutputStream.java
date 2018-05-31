package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream (OutputStream OS){
        this.out = OS;
    }

    public void write(int b) throws IOException {

    }

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    public void write(byte[] b) throws IOException {
        int counter = 0, i;
        String rowsDim = "", colsDim = "";
        ArrayList<String> all = new ArrayList<>();
        //adding metadata to array list and finding maze dimensions
        for (i = 0; i < b.length && counter < 6; i++){
            if (counter == 4 && b[i] != -1)
                rowsDim += b[i];
            else if (counter == 5 && b[i] != -1)
                colsDim += b[i];
            if (b[i] == -1 && counter < 6) {
                counter++;
                all.add(b[i] + "");
            }
            else
                all.add(b[i] + "");
        }
        int row = Integer.parseInt(rowsDim);
        int col = Integer.parseInt(colsDim);
        int colMod = col % 8;
        int numOfBlocks = col / 8;


        byte[] bval = new byte[all.size() + row + row*numOfBlocks];
        int j = i;

        //adding binary strings to array list
        while(j < b.length){
            counter = 0;
            String toAdd = "";
            if (colMod == 0) {
                for (; j < i + 8 && j < b.length; j++)
                    toAdd += b[j];
                all.add(toAdd);
                i += 8;
            }
            else{
                while (counter < numOfBlocks) {
                    for (; j < b.length && j < i + 8; j++)
                        toAdd += b[j];
                    all.add(toAdd);
                    counter++;
                    i += 8;
                    toAdd = "";
                }
                for(int k = 0; k < 8 - colMod; k++)
                    toAdd+=0;
                int m = j;
                for (; j < m + colMod; j++) {
                    toAdd += b[j];
                    i++;
                }
                all.add(toAdd);
            }
        }

        //from string to byte
        int temp = 0;
        for (i = 0; i < all.size(); i++) {
            if (all.get(i).length() < 2) {
                temp = Integer.parseInt(all.get(i));
                bval[i] = (byte) temp;
            }
            else{
                temp  = Integer.parseInt(all.get(i),2);
                bval[i] = (byte)temp;
            }
        }

        out.write(bval);
    }

}
