package ltf.namerank.rank.dictrank.support;

import com.alibaba.fastjson.JSON;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.RankScore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.PathUtils.getJsonHome;

/**
 * @author ltf
 * @since 16/6/30, 下午4:57
 */
public class WordFeelingRank {

    private List<WordScore> wordScores = new ArrayList<>();

    private void initWordFeelings() throws IOException {
        List<WordFeeling> wordFeelingList = JSON.parseArray(file2Str(getJsonHome() + "/wordfeeling"), WordFeeling.class);

        for (WordFeeling feeling : wordFeelingList) {
            if (feeling.getPolar() == 1) {
                wordScores.add(new WordScore(feeling.getWord(), feeling.getLevel()));
            } else if (feeling.getPolar() == 2) {
                wordScores.add(new WordScore(feeling.getWord(), -feeling.getLevel()));
            }
        }
    }

    public void rank(final String content, final RankScore record) {
        for (WordScore wordScore : wordScores) {
            int count = existsCount(content, wordScore.getWord());
            record.add(wordScore.getScore() * Math.sqrt(count));
        }
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
