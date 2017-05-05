package li.tf.mp3cutter.section;

/**
 * MPEG Audio Tag ID3v1
 *
 * @author ltf
 * @see "http://mpgedit.org/mpgedit/mpeg_format/mpeghdr.htm"
 * @since 17/5/5, 上午10:18
 */
public class TagID3v1Section extends Section {
    @Override
    protected boolean parseHeadData(byte[] head) {
        length = 128;
        return false;
    }

    @Override
    protected byte[] getIdentifier() {
        return new byte[]{'T', 'A', 'G'};
    }
}
