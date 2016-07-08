package ltf.namerank.rank.dictrank.pronounce;

import ltf.namerank.rank.RankLogger;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.WrappedRanker;
import ltf.namerank.rank.dictrank.support.PinyinMap;

import java.util.Set;

/**
 * @author ltf
 * @since 16/6/30, 下午4:56
 */
public class PronounceRank extends WrappedRanker {

    public PronounceRank(Ranker ranker) {
        super(ranker);
    }

    @Override
    public double rank(String target, RankLogger logger) {

        double rk = 0;
        Set<String> words = PinyinMap.getWords(target);
        for (String word : words) {
            if (target.equals(word)) continue;

            rk += super.rank(word, logger);
        }

        final double finalRk = rk;
        logger.log(() -> String.format("%f", finalRk));

        Set<String> wordsNoTone = PinyinMap.getWords(target);
        for (String word : wordsNoTone) {
            if (target.equals(word) || words.contains(word)) continue;

            rk += super.rank(word, logger) * 0.3;
        }


        return rk;
    }

}
