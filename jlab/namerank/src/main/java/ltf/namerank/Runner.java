package ltf.namerank;

import ltf.namerank.dataprepare.WordFeelings;
import ltf.namerank.lab.PinyinTest;
import ltf.namerank.lab.RankingTest;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;

/**
 * @author ltf
 * @since 16/6/12, 下午5:26
 */
public class Runner {
    public static void main(String[] args) {
        // new HanziWuxing().run();
        //new WordFeelings().go();
        new RankingTest().go();
        //new PinyinTest().go();
        //new HanYuDaCidian().listKeys();
    }
}