package ltf.namerank.rank.dictrank.pronounce;

import ltf.namerank.rank.*;
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
        double rk = 0;
        double childRk;
        if (RankSettings.reportMode) acquireBuilder();
        Set<String> words = PinyinMap.getWords(target.getKey());

        FreqRankList freqList = new FreqRankList();

        if (RankSettings.reportMode) addInfo("\n同声: ");
        for (String word : words) {
            if (target.getKey().equals(word)) continue;
            freqList.add(word);
        }
        for (FreqRankItem item : freqList.prepare()) {
            childRk = super.rank(new RankItem(item.getWord())) * item.getRate() * 8;
            rk += childRk;
            if (RankSettings.reportMode) info(item.getWord(), childRk);
        }

        if (RankSettings.reportMode) addInfo("\n异声: ");
        Set<String> wordsNoTone = PinyinMap.getWordsNoTone(target.getKey());
        freqList.clear();
        for (String word : wordsNoTone) {
            if (target.getKey().equals(word) || words.contains(word)) continue;
            freqList.add(word);
        }
        for (FreqRankItem item : freqList.prepare()) {
            childRk = super.rank(new RankItem(item.getWord())) * item.getRate() * 2;
            rk += childRk;
            if (RankSettings.reportMode) info(item.getWord(), childRk);
        }

        if (RankSettings.reportMode) flushResult(target, rk);
        else target.setScore(rk);

        return rk;
    }

    private void info(String word, double delta) {

        if (delta < 0)
            addInfo(String.format("<font color=red>%s: %.1f</font>; ", word, delta));
        else if (delta > 0)
            addInfo(String.format("%s: %.1f; ", word, delta));
        else
            addInfo(String.format("%s; ", word));
    }

}
