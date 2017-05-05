package li.tf.mp3cutter;

import li.tf.mp3cutter.section.Mp3Frame;
import li.tf.mp3cutter.section.Section;
import li.tf.mp3cutter.section.SectionDetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author ltf
 * @since 17/5/4, 下午4:16
 */
public class Mp3Cutter {

    private final RandomAccessFile inFile;

    public Mp3Cutter(String inFile) throws FileNotFoundException {
        this.inFile = new RandomAccessFile(inFile, "r");
    }

    public static void cut(String inFile, String outFile, int start, int end) throws IOException {
        new Mp3Cutter(inFile).cutTo(start, end, outFile);
    }

    public void cutTo(int start, int end, String outFile) throws IOException {
        byte[] head = new byte[10];
        int size;
        size = inFile.read(head);
        SectionDetector detector = new SectionDetector();
        Section sec = null;

        while (size > 0) {
            if ((sec = detector.detect(head)) != null) {
                System.out.println(sec.getClass().getSimpleName());
                if (sec instanceof Mp3Frame) {
                    inFile.skipBytes(sec.getLength() - head.length);

                } else {
                    inFile.skipBytes(sec.getLength() - head.length);
                }
                size = inFile.read(head);
            }

        }
    }
}
