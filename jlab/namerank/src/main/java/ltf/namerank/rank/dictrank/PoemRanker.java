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

            if (sp1 == sp2 && p1 < p2) {
                // 在同一句
                rk += 100;
                if (RankSettings.reportMode) logInfo = String.format("%.1f : %s", rk, sentences.get(sp1));
            } else if (p1 == p2 && sentences.get(sp1).length() == sentences.get(sp2).length()) {
                // 在不同句的相同位置
                rk += 1000;
                if (RankSettings.reportMode)
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
