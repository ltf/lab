package li.tf.mp3cutter.section;

/**
 * ID3v2 footer
 *
 * @author ltf
 * @see "http://id3.org/id3v2.4.0-structure"
 * @since 17/5/5, 上午10:12
 */
public class TagID3v2Footer extends Section {
    @Override
    protected boolean parseHeadData(byte[] head) {
        length = 10;
        return true;
    }

    @Override
    protected byte[] getIdentifier() {
        return new byte[]{'3', 'D', 'I'};
    }
}
