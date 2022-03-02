package ltf.namerank.rank.dictrank;

import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;

import java.io.IOException;
import java.util.HashMap;

import static ltf.namerank.rank.RankItemHelper.*;
import static ltf.namerank.utils.FileUtils.fromLinesData;

/**
 * @author ltf
 * @since 2022/3/2, 23:01
 */
public class CharRateRanker implements Ranker {
    private HashMap<Character, Double> charRateRank;

    @Override
    public double rank(RankItem target) {
        if (charRateRank == null) {
            charRateRank = new HashMap<>();
            try {
                int count = 1;
                for (String l : fromLinesData("zipin")) {
                    for (char c : l.toCharArray()) {
                        count++;
                        charRateRank.putIfAbsent(c, 100.0 / (1 + (count / 500)));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        char c1 = target.getKey().charAt(0);
        char c2 = target.getKey().charAt(1);

        double c1rk = charRateRank.getOrDefault(c1, 0.0);
        double c2rk = charRateRank.getOrDefault(c2, 0.0);
        double rk = c1rk + c2rk;
        if (RankSettings.reportMode) acquireBuilder();
        if (RankSettings.reportMode) addInfo(String.format("%c: %.1f, %c: %.1f",
                c1, c1rk, c2, c2rk));
        if (RankSettings.reportMode) flushResult(target, rk);

        return rk;
    }

    @Override
    public String getName() {
        return "字频";
    }
}
