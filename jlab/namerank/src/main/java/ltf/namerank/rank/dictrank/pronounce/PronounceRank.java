package ltf.namerank.rank.dictrank.pronounce;

import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.WrappedRanker;
import ltf.namerank.rank.dictrank.support.PinyinMap;

import java.util.Set;

import static ltf.namerank.rank.RankItemHelper.*;

/**
 * @author ltf
 * @since 16/6/30, 下午4:56
 */
public class PronounceRank extends WrappedRanker {

    public PronounceRank(Ranker ranker) {
        super(ranker);
    }

    @Override
    public String getName() {
        return "发音";
    }

    @Override
    public double rank(RankItem target) {
        double rk = target.getScore();
        double childRk;
        if (RankSettings.reportMode) acquireBuilder();
        Set<String> words = PinyinMap.getWords(target.getKey());
        for (String word : words) {
            if (target.getKey().equals(word)) continue;

            childRk = super.rank(new RankItem(word));
            rk += childRk;
            if (RankSettings.reportMode) addInfo(String.format("%s: %.1f; ", word, childRk));
        }

        Set<String> wordsNoTone = PinyinMap.getWordsNoTone(target.getKey());
        for (String word : wordsNoTone) {
            if (target.getKey().equals(word) || words.contains(word)) continue;

            childRk = super.rank(new RankItem(word)) * 0.2;
            rk += childRk;
            if (RankSettings.reportMode) addInfo(String.format("%s: %.1fx0.2; ", word, childRk));
        }

        if (RankSettings.reportMode) flushResult(target, rk); else target.setScore(rk);

        return rk;
    }

}
