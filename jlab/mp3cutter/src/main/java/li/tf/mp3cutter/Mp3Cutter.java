package li.tf.mp3cutter;

import li.tf.mp3cutter.section.Mp3Frame;
import li.tf.mp3cutter.section.Mp3VbrIndex;
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

    public static void cut(String inFile, String outFile, double start, double end) throws IOException {
        new Mp3Cutter(inFile).cutTo(start, end, outFile);
    }

    public void cutTo(double start, double end, String outFile) throws IOException {
        byte[] head = new byte[10];
        int size;
        int offset = head.length;
        size = inFile.read(head);
        SectionDetector detector = new SectionDetector();
        Section sec = null;
        double timeLen = 0.0;

        while (size > 0) {
            if ((sec = detector.detect(head)) != null) {
                if (sec instanceof Mp3VbrIndex) {
                    inFile.skipBytes(sec.getLength() - head.length);
                    offset += sec.getLength() - head.length;
                    timeLen += ((Mp3Frame) sec).getTimeLength();
                    System.out.println(String.format("%s - %f", sec.getClass().getSimpleName(), timeLen));
                } else if (sec instanceof Mp3Frame) {
                    inFile.skipBytes(sec.getLength() - head.length);
                    offset += sec.getLength() - head.length;
                    timeLen += ((Mp3Frame) sec).getTimeLength();
                    System.out.println(String.format("%s - %f", sec.getClass().getSimpleName(), timeLen));
                } else {
                    inFile.skipBytes(sec.getLength() - head.length);
                    offset += sec.getLength() - head.length;
                    System.out.println(sec.getClass().getSimpleName());
                }
            } else {
                System.out.println("Error data: " + Integer.toHexString(offset));
            }
            size = inFile.read(head);
            offset += head.length;
        }
    }
}
