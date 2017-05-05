package li.tf.mp3cutter.section;

/**
 * ID3v2 header and section define (Header+Data)
 *
 * @author ltf
 * @see "http://id3.org/id3v2.4.0-structure"
 * @since 17/5/4, 下午3:49
 */
public class TagID3v2Section extends Section {

    /**
     * convert SynchsafeInt to Integer
     */
    public static int SynchsafeInt2Integer(byte[] data, int offset) {
        int v = 0;
        for (int i = 0; i < 4; i++) {
            v = (v << 7) & (data[offset + i] & 0x7f);   // big endian
        }
        return v;
    }

    @Override
    protected boolean parseHeadData(byte[] head) {
        if (head.length < 10) throw new RuntimeException("need at least 10 bytes for parsing");

        length = SynchsafeInt2Integer(head, 6);
        return true;
    }

    @Override
    protected byte[] getIdentifier() {
        return new byte[]{'I', 'D', '3'};
    }
}
