package li.tf.mp3cutter.section;

/**
 * MP3 VBR Index frame (Header+Data)
 *
 * @author ltf
 * @see "http://www.multiweb.cz/twoinches/MP3inside.htm"
 * Created by ltf on 2017/5/6.
 */
public class Mp3VbrIndex extends Mp3Frame {

    @Override
    protected boolean parseHeadData(byte[] head) {
        if (super.parseHeadData(head) && length == 156) {
            timeLength = 0.0;  // VBR index frame count no time length
            return true;
        }
        return false;
    }
}
