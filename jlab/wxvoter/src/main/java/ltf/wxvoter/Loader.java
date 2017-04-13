package ltf.wxvoter;

import ltf.wxvoter.voter.WxVoteThread;

import java.io.IOException;

/**
 * @author ltf
 * @since 17/4/6, 下午2:43
 */
public class Loader {

    public static void main(String[] args) throws IOException {

        new WxVoteThread().voteLoop();
    }
}
