package li.tf.mp3cutter.section;

/**
 * @author ltf
 * @since 17/5/4, 下午5:11
 */
abstract public class Section {

    protected int length;

    /**
     * check the head data starts with identifier, then parse other info if it's true
     *
     * @return true if the head is recognized and parsed as this type
     */
    final boolean parseHead(byte[] head) {
        length = -1;
        byte[] id = getIdentifier();
        if (head == null || head.length < id.length) return false;
        for (int i = 0; i < id.length; i++) {
            if (head[i] != id[i]) return false;
        }
        return parseHeadData(head);
    }

    /**
     * do parse header data
     */
    abstract protected boolean parseHeadData(byte[] head);

    abstract protected byte[] getIdentifier();

    /**
     * full length of the section, include the head data bytes
     */
    public final int getLength() {
        return length;
    }
}
