package li.tf.mp3cutter;

import java.io.*;

/**
 * @author ltf
 * @since 17/5/4, 下午4:16
 */
public class Mp3Cutter {

    private final RandomAccessFile inFile;

    public Mp3Cutter(String inFile) throws FileNotFoundException {
        this.inFile = new RandomAccessFile(inFile, "r");
    }

    public static void cut(String inFile, String outFile, int start, int end) {

    }

    public void cutTo(int start, int end, String outFile) throws IOException {
        byte[] head = new byte[10];
        inFile.read(head);




    }
}
