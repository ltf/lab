package ltf.namerank.rank.filter;

import ltf.namerank.rank.RankFilter;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.Ranker;

import java.util.ArrayList;
import java.util.List;

/**
 * filter on ranker's score
 *
 * @author ltf
 * @since 16/7/12, 下午12:18
 */
public class OverRankerFilter implements RankFilter {

    private List<Ranker> rankers = new ArrayList<>();
    private List<Double> banScores = new ArrayList<>();

    @Override
    public boolean banned(String givenName) {
        RankItem item = new RankItem(givenName);
        for (int i = 0; i < rankers.size(); i++) {
            if (rankers.get(i).rank(item) < banScores.get(i))
                return true;
        }
        return false;
    }

    /**
     * add ranker to decide the ban score
     *
     * @param ranker   ranker
     * @param banScore if score < banScore, will be banned
     */
    public OverRankerFilter addRanker(Ranker ranker, double banScore) {
        if (ranker == null) return this;
        rankers.add(ranker);
        banScores.add(banScore);
        return this;
    }
}
