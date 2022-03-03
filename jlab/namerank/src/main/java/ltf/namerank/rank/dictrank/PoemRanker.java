package ltf.namerank.rank.dictrank;

import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;
import ltf.namerank.utils.Rtc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static ltf.namerank.rank.RankItemHelper.*;
import static ltf.namerank.utils.FileUtils.fromLinesData;

/**
 * @author ltf
 * @since 2022/3/2, 23:01
 */
public class PoemRanker implements Ranker {
    private PoemSet poemSet;

    @Override
    public double rank(RankItem target) {
        if (poemSet == null) {
            Rtc.begin();
            poemSet = new PoemSet();
            Rtc.end();
        }

        return poemSet.rank(target);
    }

    @Override
    public String getName() {
        return "诗歌";
    }

    private static class Poem {
        private ArrayList<String> sentences = new ArrayList<>();
        private HashSet<Character> chars = new HashSet<>();
        String logInfo;

        public double rank(RankItem target) {
            String givenName = target.getKey();
            if (givenName.length() != 2) {
                return 0;
            }

            char c1 = givenName.charAt(0);
            char c2 = givenName.charAt(1);
            if (!(chars.contains(c1) && chars.contains(c2))) {
                return 0;
            }

            double rk = 0;
            int p1 = -1;   // position
            int p2 = -1;
            int sp1 = -1;  // sentence index
            int sp2 = -1;
            for (int i = 0; i < sentences.size(); i++) {
                if (p1 < 0) {
                    p1 = sentences.get(i).indexOf(c1);
                    sp1 = i;
                }
                if (p2 < 0) {
                    p2 = sentences.get(i).indexOf(c2);
                    sp2 = i;
                }
                if (p1 >= 0 && p2 >= 0) break;
            }

            /**
             * 悬弧日
             * 福不尽
             * 贵无敌
             * 愿岁岁
             * 见华席
             *
             * 关关雎鸠
             * 在河之洲
             * 窈窕淑女
             * 君子好逑
             *
             * 桃之夭夭
             * 灼灼其华
             * 之子于归
             *
             * 千年恨未终
             * 关山人去后
             * 秋夜月明中
             *
             * 晴日孤舟好
             * 溪流带岸斜
             * 浮游忘世虑
             * 烂熳乱春华
             *
             * 万古山如此
             * 青青岁岁新
             *
             * 今日西川无子美
             * 诗风又起浣花村
             *
             * 若比争名求利处
             * 寻思此路却安宁
             */
            if (sp1 == sp2) {
                // 在同一句
                String sentence = sentences.get(sp1);
                switch (sentence.length()) {
                    case 3:
                        if (p2 - p1 == 1) rk = 100;
                        break;
                    case 4:
                        if ((p1 == 0 && p2 == 1) || (p1 == 2 && p2 == 3)) rk = 100;
                        break;
                    case 5:
                        if ((p1 == 0 && p2 == 1) || (p1 == 3 && p2 == 4)) rk = 100;
                        break;
                    case 7:
                        if ((p1 == 0 && p2 == 1) || (p1 == 2 && p2 == 3) || (p1 == 5 && p2 == 6)) rk = 100;
                        break;
                    default:
                        if (p2 - p1 == 1 || p2 - p1 == 2) rk = 10;
                        break;
                }

                if (RankSettings.reportMode && rk > 0) logInfo = String.format("%.1f : %s", rk, sentence);
            } else if (p1 == p2 && sentences.get(sp1).length() == sentences.get(sp2).length()
                    && (sp2 - sp1 == 1 || sp2 - sp1 == 2)) {
                // 在不同句的相同位置
                switch (sentences.get(sp1).length()) {
                    case 3:
                        if (p1 == 0 || p1 == 2) rk = 1000;
                        break;
                    case 4:
                        if (p1 == 0 || p1 == 3) rk = 1000;
                        break;
                    case 5:
                        if (p1 == 0 || p1 == 4) rk = 1000;
                        break;
                    case 7:
                        if (p1 == 0 || p1 == 6) rk = 1000;
                        if (p1 == 2 || p1 == 4 || p1 == 5) rk = 200;
                        break;
                }
                if (RankSettings.reportMode && rk > 0)
                    logInfo = String.format("%.1f : %s, %s", rk, sentences.get(sp1), sentences.get(sp2));
            }

            return rk;
        }

        void addSentence(String sentence) {
            sentences.add(sentence);
            for (char c : sentence.toCharArray()) {
                chars.add(c);
            }
        }
    }

    private static class PoemSet {
        ArrayList<Poem> poems = new ArrayList<>();

        PoemSet() {
            try {
                Poem curPoem = null;
                for (String l : fromLinesData("shici")) {
                    if ("".equals(l)) {
                        curPoem = null;
                        continue;
                    }

                    if (curPoem == null) {
                        curPoem = new Poem();
                        poems.add(curPoem);
                    }

                    curPoem.addSentence(l);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public double rank(RankItem target) {
            Rtc.begin();
            if (RankSettings.reportMode) acquireBuilder();

            double rk = 0;
            int count = 0;
            for (Poem poem : poems) {
                double delta = poem.rank(target);
                if (delta > 0) {
                    rk += delta;
                    count++;
                    if (RankSettings.reportMode) {
                        addInfo(poem.logInfo);
                        poem.logInfo = "";
                    }
                }
            }
            rk = count > 0 ? rk / count : rk;
            if (RankSettings.reportMode) flushResult(target, rk);

            Rtc.end();
            return rk;
        }
    }
}
