package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.rank.RankItemHelper.acquireBuilder;
import static ltf.namerank.rank.RankItemHelper.addInfo;
import static ltf.namerank.rank.RankItemHelper.flushResult;
import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.PathUtils.getJsonHome;
import static ltf.namerank.utils.StrUtils.existsCount;

/**
 * @author ltf
 * @since 16/6/30, 下午4:57
 */
public class WordFeelingRank implements Ranker {

    private List<WordScore> wordScores = null;

    private void initWordFeelings() {
        if (wordScores == null) {
            wordScores = new ArrayList<>();
            List<WordFeeling> wordFeelingList = null;
            try {
                wordFeelingList = JSON.parseArray(file2Str(getJsonHome() + "/wordfeeling"), WordFeeling.class);
            } catch (IOException e) {
            }

            for (WordFeeling feeling : wordFeelingList) {

                if (!( //"noun".equals(feeling.getProperty()) ||
                        // "verb".equals(feeling.getProperty()) ||
                        "adj".equals(feeling.getProperty()) ||
                                "adv".equals(feeling.getProperty())))
                    continue;


                if (feeling.getPolar() == 1) {
                    wordScores.add(new WordScore(feeling.getWord(), feeling.getLevel()));
                } else if (feeling.getPolar() == 2) {
                    wordScores.add(new WordScore(feeling.getWord(), -feeling.getLevel()));
                }
            }
        }
    }


    public static WordFeelingRank getInstance() {
        return Holder.getInstance();
    }

    @Override
    public double rank(RankItem target) {
        initWordFeelings();
        double rk = 0;
        if (RankSettings.reportMode) acquireBuilder();
        for (WordScore wordScore : wordScores) {
            int count = existsCount(target.getKey(), wordScore.getWord());
            rk += wordScore.getScore() * Math.sqrt(count);
            if (RankSettings.reportMode) addInfo(String.format("%s %f %d; ", wordScore.getWord(), wordScore.getScore(), count));
        }
        if (RankSettings.reportMode) flushResult(target, rk); else target.setScore(rk);
        return rk;
    }

    private static class Holder {
        private static final WordFeelingRank rank = new WordFeelingRank();

        private static WordFeelingRank getInstance() {
            return rank;
        }
    }

    public void listItems() {
        initWordFeelings();
        for (WordScore wordScore : wordScores) {
            System.out.println(String.format("%s : %f", wordScore.getWord(), wordScore.getScore()));
        }
    }
}
