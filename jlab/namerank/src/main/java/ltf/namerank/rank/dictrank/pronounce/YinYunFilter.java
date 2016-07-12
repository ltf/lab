package ltf.namerank.rank.dictrank.pronounce;

import ltf.namerank.rank.RankFilter;
import ltf.namerank.rank.dictrank.support.PinyinMap;

import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.rank.RankSettings.getFamilyName;
import static ltf.namerank.rank.dictrank.support.PinyinMap.toPinyin;

/**
 * @author ltf
 * @since 7/12/16, 11:36 PM
 */
public class YinYunFilter implements RankFilter {

    private List<String> shengMus = new ArrayList<>();
    private List<String> yunMus = new ArrayList<>();
    private List<String> tones = new ArrayList<>();

    private List<String> yunTous = new ArrayList<>();
    private List<String> yunFus = new ArrayList<>();
    private List<String> yunWeis = new ArrayList<>();

    private String pingze = "";


    @Override
    public boolean banned(String givenName) {
        reset();
        add(getFamilyName().toCharArray()[0]);
        for (char c : givenName.toCharArray()) {
            if (!add(c)) return true;
        }

        if (!"仄平平".equals(pingze)) return true;

        if (!(checkYunTou() && checkYunFu() && checkYunWei() && checkShengmu())) return true;


        return false;
    }

    /**
     * add Character success return true, else return false and ban the name
     */
    private boolean add(char zi) {
        PinyinMap.Pinyin[] pinyins = toPinyin(zi);
        if (shengMus.contains(pinyins[0].shengMu)) return false;
        if (yunMus.contains(pinyins[0].yunMu)) return false;
        if (tones.contains(pinyins[0].tone)) return false;
        shengMus.add(pinyins[0].shengMu);
        yunMus.add(pinyins[0].yunMu);
        tones.add(pinyins[0].tone);
        yunTous.add(pinyins[0].yunTou);
        yunFus.add(pinyins[0].yunFu);
        yunWeis.add(pinyins[0].yunWei);
        pingze += getPingZe(pinyins[0]);
        return true;
    }

    /**
     * check pass return true, else check fail return false and ban the name
     */
    private boolean checkYunTou() {
//        韵头介音的区别，齐齿呼i、合口呼u、撮口呼ü与开口呼ɑ、e（非介音）的区别最大，
//        齐齿呼i与合口呼u、撮口呼ü的区别较大，合口呼u与撮口呼ü的区别最小。
//        “吴婉媛”有介音u、ü，很绕口；破作“吴嫣媛”，有介音i、ü区别，好很多；再作“吴霭媛”，有ɑ对ü，显得更好。

        int count = 0;
        for (String wei : yunWeis) {
            if ("u".equals(wei) || "v".equals(wei)) count++;
        }
        return count <= 1;
    }

    /**
     * check pass return true, else check fail return false and ban the name
     */
    private boolean checkYunFu() {
        //韵腹主元音首先是洪音“ɑ、o、u（ü、）”与细音“i（ï、ï）、e”的区别度最大，
        // 其次是“ɑ”与“o、u”、“i（ï、ï）与“e”的对应区别，
        // “o”与“u”、“u”与“ü”、“i”与“ï、ï”的区别度都很小，
        // 姓名用字间一定要注意挑主元音区别大的做韵腹以提高语音区别度。
        // 在众多的主元音中，取名字最易发且响亮动听的是“ɑ（a）”
        if (!yunFus.contains("a")) return false; // need "a"
        return true;
    }

    /**
     * check pass return true, else check fail return false and ban the name
     */
    private boolean checkYunWei() {
//        韵尾区别主要是开音节韵尾（零尾、元音尾）与闭音节鼻辅音韵尾（n、nɡ）的对应区别，
//        “龚弘农”都有nɡ尾，重复呆板。“龚坛农”有n、nɡ尾，也改善不了多少。
//        很多地区方言前鼻音n与后鼻音nɡ不分，“n、nɡ”两韵尾区别度较小。
//        “龚开农”有元音韵尾i与鼻辅音韵尾nɡ的较大差别，听感就清晰多了。

        int count = 0;
        for (String wei : yunWeis) {
            if ("ng".equals(wei) || "n".equals(wei)) count++;
        }

        return count <= 1;
    }


    private boolean checkShengmu() {
//        声母辅音的区别，发音部位分组：
//        唇音b、p、m、f；
//        舌尖前音（平舌）z、c、s，
//        舌尖中音d、t、n、l，
//        舌尖后音（翘舌）zh、ch、sh、r；
//        舌面前音j、q、x，舌面后音（舌根）ɡ、k、h。唇音与舌音各组区别最大，
//        舌尖与舌面区别次大。
//        舌面两组，j、q、x只与细音韵母i相拼，ɡ、k、h与洪音韵母相拼，区别还是比较大的。
//        舌尖前、后两组最易混淆，南方人多平舌、翘舌不分，最好不要混用这两组声母，如“师思帅”声母为sh 、s 、sh，很绕。
//        d、t、n、l是塞音与鼻、边音，与塞擦音及擦音的z、zh两组差别较大，只是“n、l不分”是很多人通病，“那拉妮”声母为n、l、n，很绕口。
//        各组声母之间是发音方法的区别，鼻音与其他音的区别最大，塞音与擦音的区别次之，送气与不送气音区别最小，
//        “皮必备”声母为b、p、b，不送气与送气的差别小，很绕口，相声的绕口令主要是绕声母相同相近的字音。

        int count = 0;
        for (String sm : shengMus) {
            if ("z".equals(sm) || "c".equals(sm) || "s".equals(sm)
                    || "zh".equals(sm) || "ch".equals(sm) || "sh".equals(sm))
                return false;
            if ("n".equals(sm) || "l".equals(sm)) count++;
        }

        return count <= 1;
    }


    private void reset() {
        shengMus.clear();
        yunMus.clear();
        tones.clear();
        yunTous.clear();
        yunFus.clear();
        yunWeis.clear();
        pingze = "";
    }

    // http://blog.sina.com.cn/s/blog_5134f2be0102drqu.html
    // “主元音的洪细（韵腹）→音高升降的抑扬（声调）→收尾的开闭（韵尾）→韵头的口型开合（韵头）→音节前部辅助音的位移（声母）”，形成了影响音节总区别度的重要性递减的连续统。
    //在众多的主元音中，取名字最易发且响亮动听的是“ɑ（a）”

    // 唇音b、p、m、f；舌尖前音（平舌）z、c、s，舌尖中音d、t、n、l，舌尖后音（翘舌）zh、ch、sh、r；
    // 舌面前音j、q、x，
    // 舌面后音（舌根）ɡ、k、h。
    // 唇音与舌音各组区别最大，舌尖与舌面区别次大。

    // 三字姓名的平仄优化搭配情况是有规律可循的。就我们的统计数据来看，“仄平平”的概率最高
    private String getPingZe(PinyinMap.Pinyin pinyin) {
        if ("1".equals(pinyin.tone) || "2".equals(pinyin.tone)) return "平";
        else if ("3".equals(pinyin.tone) || "4".equals(pinyin.tone)) return "仄";
        else return "";
    }
}
