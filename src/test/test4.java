package test;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class test4 {

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    public byte[] compress(int[][]maze){
        byte [] bval = new byte[100];
        int i = 0;
        String valtemp = "", val = "";
        byte bigThen127 = 0;
        for (int j = 0; j < maze.length; j++) {
            val = Arrays.toString(maze[j]);
            val = val.substring(1).replaceAll("[\\s\\,]", "").replaceAll("]", "")
                    .replaceAll("E", "0").replaceAll("S", "0");
            while (val.length() > 0 && i < 100) {
                if (8 < val.length()) {
                    valtemp = val.substring(0, 8);
                    BigInteger ig = new BigInteger(valtemp, 2);
                    if (ig.intValue() > 127) {
                        bigThen127 = (byte) unsignedToBytes(ig.byteValue());
                        bval[i] = bigThen127;
                    }
                    else
                        bval[i] = ig.byteValueExact();
                    val = val.substring(8);
                    i++;
                }
                else{
                    valtemp = val.substring(0);
                    val = "";
                    BigInteger ig = new BigInteger(valtemp, 2);
                    bval[i] = ig.byteValueExact();
                    i++;
                }
            }
        }
        return bval;
    }


    public static void main(String[] args) {
        int[][] maze = {{0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0},
                {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0}};

        byte [] b = {42, -56, 2, -96, 29, 8, 31, 76, 10, 112, -95, 2, 14, 46, 13, -95, -128, 0, 85, 47, 5, 8, 64, 13, -94, -106,
                2,73,36,12,18,114,1,-83,36,10,-87,74,4,36,98,14,-79,-119,6,68,87,0,58,-128,6,107,53,1,0,101,4,85,18};
        ArrayList<Integer> num = new ArrayList<Integer>();
        for (int i = 0; i < b.length; i++) {
            String s1 = String.format("%8s", Integer.toBinaryString(b[1] & 0xFF)).replace(' ', '0');
            for (int j = 0; j < s1.length(); j++)
                num.add((int)s1.charAt(j));


        }
    }
}


