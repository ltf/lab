package li.tf.mp3cutter;


import org.testng.annotations.Test;

/**
 * @author ltf
 * @since 17/5/5, 下午4:49
 */
public class Mp3CutterTest {
    @Test
    public void testCut() throws Exception {
        Mp3Cutter.cut("D:\\share\\mp3\\庞龙 - 兄弟抱一下.mp3",
                "D:\\share\\mp3\\庞龙 - 兄弟抱一下-part.mp3", 239, 242);


    }

}