package li.tf.mp3cutter;

import li.tf.mp3cutter.section.Mp3Frame;
import li.tf.mp3cutter.section.Mp3VbrIndex;
import li.tf.mp3cutter.section.Section;
import li.tf.mp3cutter.section.SectionDetector;

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

    /**
     * cut part from mp3
     *
     * @param inFile  in
     * @param outFile out
     * @param start   start (s)
     * @param end     end(s)
     * @throws IOException
     */
    public static void cut(String inFile, String outFile, double start, double end) throws IOException {
        new Mp3Cutter(inFile).cutTo(start, end, outFile);
    }

    public void cutTo(double start, double end, String outFile) throws IOException {
        byte[] head = new byte[10];
        byte[] buf = new byte[1024 * 16];
        int size;
        size = inFile.read(head);
        SectionDetector detector = new SectionDetector();
        Section sec = null;
        BufferedOutputStream out = null;
        double timeLen = 0.0;

        while (size > 0) {
            if ((sec = detector.detect(head)) != null) {
                if (sec instanceof Mp3VbrIndex) {
                    inFile.skipBytes(sec.getLength() - head.length);
                    timeLen += ((Mp3Frame) sec).getTimeLength();
                } else if (sec instanceof Mp3Frame) {
                    if (timeLen >= start && timeLen < end) {
                        if (out == null) {
                            out = new BufferedOutputStream(new FileOutputStream(outFile));
                        }
                        out.write(head, 0, size);
                        copyData(inFile, out, sec.getLength() - head.length, buf);
                    } else {
                        inFile.skipBytes(sec.getLength() - head.length);
                    }
                    timeLen += ((Mp3Frame) sec).getTimeLength();
                } else {
                    inFile.skipBytes(sec.getLength() - head.length);
                }
            }
            size = inFile.read(head);
        }

        if (out != null) {
            out.close();
        }
    }

    private void copyData(RandomAccessFile in, OutputStream out, int len, byte[] buf) throws IOException {
        int size = 0;
        while ((size = in.read(buf, 0, len < buf.length ? len : buf.length)) > 0) {
            out.write(buf, 0, size);
            len -= size;
        }
    }
}
