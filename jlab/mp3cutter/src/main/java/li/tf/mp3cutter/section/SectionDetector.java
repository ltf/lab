package li.tf.mp3cutter.section;

/**
 * @author ltf
 * @since 17/5/4, 下午5:07
 */
public class SectionDetector {
    private Section[] sectionTypes = {
            new TagID3v2Section(),
            new TagID3v2Footer(),
            new TagID3v1Section(),
            new Mp3Frame()
    };


    public Section detect(byte[] head) {
        for (Section sec : sectionTypes) {
            if (sec.parseHead(head)) {
                return sec;
            }
        }
        return null;
    }
}
