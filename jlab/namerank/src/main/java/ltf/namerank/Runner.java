package ltf.namerank;

import ltf.namerank.dataprepare.*;
import ltf.namerank.lab.NewDictTest;
import ltf.namerank.lab.PinyinTest;
import ltf.namerank.lab.RankingTest;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;
import ltf.namerank.service.EvenManager;
import ltf.namerank.utils.Rtc;

/**
 * @author ltf
 * @since 16/6/12, 下午5:26
 */
public class Runner {
    public static void main(String[] args) {
        Rtc.begin();

        //new WugeDataCollect();
        //new ChoseWords();
        //new NameDataProcessor().go();
        //new DictNameCollecter();
        // new HanziWuxing().run();
        //new WordFeelings().go();
        new RankingTest().go();
        //new NewDictTest();
        //new PinyinTest().go();
        //new HanYuDaCidian().listKeys();



        EvenManager.notifyTeardown();

        Rtc.end();
        System.out.println(Rtc.getReport());
    }

}