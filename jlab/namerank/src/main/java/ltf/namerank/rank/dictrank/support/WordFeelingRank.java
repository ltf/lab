package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.CachedRanker;
import ltf.namerank.rank.RankConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.PathUtils.getJsonHome;

/**
 * @author ltf
 * @since 16/6/30, 下午4:57
 */
public class WordFeelingRank extends CachedRanker {

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
                if (feeling.getPolar() == 1) {
                    wordScores.add(new WordScore(feeling.getWord(), feeling.getLevel()));
                } else if (feeling.getPolar() == 2) {
                    wordScores.add(new WordScore(feeling.getWord(), -feeling.getLevel()));
                }
            }
        }
    }


    @Override
    protected double doRank(String target, RankConfig config) {
        initWordFeelings();
        double rk = 0;
        for (WordScore wordScore : wordScores) {
            int count = existsCount(target, wordScore.getWord());
            rk += wordScore.getScore() * Math.sqrt(count);
        }
        return rk;
    }

    private int existsCount(final String content, final String keyword) {
        int count = 0;
        int offset = 0;
        while ((offset = content.indexOf(keyword, offset) + 1) > 0) count++;
        return count;
    }

    public static WordFeelingRank getInstance() {
        return Holder.getInstance();
    }


    private static class Holder {
        private static final WordFeelingRank rank = new WordFeelingRank();

        private static WordFeelingRank getInstance() {
            return rank;
        }
    }
}
