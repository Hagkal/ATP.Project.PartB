package IO;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }


    /**
     * Reads some number of bytes from the input stream and stores them into
     * the buffer array <code>b</code>. The number of bytes actually read is
     * returned as an integer.  This method blocks until input data is
     * available, end of file is detected, or an exception is thrown.
     *
     * <p> If the length of <code>b</code> is zero, then no bytes are read and
     * <code>0</code> is returned; otherwise, there is an attempt to read at
     * least one byte. If no byte is available because the stream is at the
     * end of the file, the value <code>-1</code> is returned; otherwise, at
     * least one byte is read and stored into <code>b</code>.
     *
     * <p> The first byte read is stored into element <code>b[0]</code>, the
     * next one into <code>b[1]</code>, and so on. The number of bytes read is,
     * at most, equal to the length of <code>b</code>. Let <i>k</i> be the
     * number of bytes actually read; these bytes will be stored in elements
     * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
     * leaving elements <code>b[</code><i>k</i><code>]</code> through
     * <code>b[b.length-1]</code> unaffected.
     *
     * <p> The <code>read(b)</code> method for class <code>InputStream</code>
     * has the same effect as: <pre><code> read(b, 0, b.length) </code></pre>
     *
     * @param b the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or
     * <code>-1</code> if there is no more data because the end of
     * the stream has been reached.
     * @throws IOException          If the first byte cannot be read for any reason
     *                              other than the end of the file, if the input stream has been closed, or
     *                              if some other I/O error occurs.
     * @throws NullPointerException if <code>b</code> is <code>null</code>.
     * @see InputStream#read(byte[], int, int)
     */
    //@Override
    public int read(byte[] b) throws IOException {
        /* getting compressed byte into an array list */
        //ArrayList<Byte> arrayList = new ArrayList<>();
        /*
        int tmp = in.read();
        while (tmp != -1) {
            arrayList.add((byte) tmp);
            tmp = in.read();
        }
        */


        /* getting compressed bytes into an array
        int p = 0;
        byte[] arr = new byte[arrayList.size()];
        for (Byte by : arrayList)
            arr[p++] = by;
        */
        /*
        for (int y = 0; y<arr.length; y++)
            System.out.print(arr[y] + ",");
        System.out.println();
        */


        ObjectInputStream in2 = new ObjectInputStream(in);
        /* getting compressed bytes into an array*/
        byte [] arr = new byte[0];
        try {
            arr = (byte[]) in2.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // extract relevant metadata from the compressed array
        int mazeCol = getData(5, arr);

        int compressedIdx = getPointer(6, arr);
        int uncompreesedIdx = compressedIdx;
        boolean flag = (mazeCol%8 == 0);

        // copying metadata elements to the new uncompressed array: b
        System.arraycopy(arr, 0, b, 0, compressedIdx);

        if (flag) { // maze col num is divided by 8
            for (int i = compressedIdx; i < arr.length; i++) {
                byte[] binary = (arr[i]<0) ? binaryParse((256 + arr[i])) : binaryParse(arr[i]);
                System.arraycopy(binary, 0, b, uncompreesedIdx, binary.length);
                uncompreesedIdx += 8;
            }
        }
        else{ // maze col num is NOT divided by 8
            for (int i = compressedIdx, j=0; i < arr.length; i++) {
                if (j == mazeCol/8){
                    byte[] binary = (arr[i]<0) ? binaryParse((256 + arr[i])) : binaryParse(arr[i]);
                    System.arraycopy(binary, 8-mazeCol%8, b, uncompreesedIdx, mazeCol%8);
                    uncompreesedIdx += mazeCol%8;
                    j=0;
                }
                else{
                    byte[] binary = (arr[i]<0) ? binaryParse((256 + arr[i])) : binaryParse(arr[i]);
                    System.arraycopy(binary, 0, b, uncompreesedIdx, binary.length);
                    uncompreesedIdx += 8;
                    j++;
                }

            }
        }


        return  arr.length;
    }

    /**
     * this method will transform a number to it's binary representation in an array of bytes
     * MSB - index 0, LSB - index 7.
     * @param num - the number to tranform to binary
     * @return - an array of bytes represting a number in range 0-255
     */
    private byte[] binaryParse(int num){
        byte[] toReturn = new byte[8];
        int idx = 7;

        while (idx > -1){
            toReturn[idx] = (byte) (num % 2);
            num /= 2;
            idx--;
        }

        return toReturn;
    }

/*
    public static void main(String args[]){
        byte [] b = {0, -1,
                     1, -1,
                     3, -1,
                     9, -1,
                     1, 0, -1,
                     1, 0, -1,
                     22, 1, 81, 0, 44, 1, 65, 2, 46, 2, 72, 0, 37, 2, 72, 3, 106, 0, 1, 1};
        byte[] test = new byte[114];
        try {
            read(test, b);
            for (int i = 0, j=0; i< test.length; i++){

                if ( i > 13)
                    j++;
                if ( i>13 && j==11 ) {
                    System.out.println();
                    j=1;
                }
                System.out.print(test[i]);
                if (i == 13)
                    System.out.println();
            }
        }
        catch (IOException e){
            System.out.println("OK");
        }
    }
*/
    /**
     * returns a pointer to the proper metadata index
     * @param amount - the metadata field desired
     * @param data - the byte array
     * @return - index of the desired metadata start point
     */
    private int getPointer(int amount, byte[] data){
        int dataPointer = 0;
        while (amount > 0){
            if (data[dataPointer] == -1)
                amount--;
            dataPointer++;
        }
        return dataPointer;
    }

    /**
     * this method will get the Integer representation of the proper coded byte metadata
     * as requested by the amount field
     * @param amount - the metadata desired to decode
     * @param data - the byte array
     * @return - int representation of the metadata
     */
    private int getData(int amount, byte[] data){
        int toReturn = 0;
        int dataPointer = getPointer(amount, data);
        int numOfDigits = 0;

        //number of digits in the number
        for (int l = dataPointer; data[l] != -1; l++)
            numOfDigits++;

        while (data[dataPointer] != -1){
            toReturn += data[dataPointer]*Math.pow(10, numOfDigits-1);
            numOfDigits--;
            dataPointer++;
        }

        return toReturn;
    }


    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     * <p> A subclass must provide an implementation of this method.
     *
     * @return the next byte of data, or <code>-1</code> if the end of the
     * stream is reached.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public int read() throws IOException {
        return 0;
    }
}
