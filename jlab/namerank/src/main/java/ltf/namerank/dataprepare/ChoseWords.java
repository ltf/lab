package ltf.namerank.dataprepare;

import com.alibaba.fastjson.JSON;
import ltf.namerank.entity.WordFeeling;
import ltf.namerank.rank.RankItem;
import ltf.namerank.rank.RankSettings;
import ltf.namerank.rank.Ranker;
import ltf.namerank.rank.dictrank.support.Words;
import ltf.namerank.rank.dictrank.support.dict.HanYuDaCidian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ltf.namerank.utils.FileUtils.file2Str;
import static ltf.namerank.utils.FileUtils.str2File;
import static ltf.namerank.utils.PathUtils.getJsonHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 7/11/16, 2:51 AM
 */
public class ChoseWords {

    public ChoseWords() {
        choose();
    }


    private void choose() {
        //loadFeelingwords();
        HanYuDaCidian ranker = new HanYuDaCidian();

        ranker.rank(new RankItem("test"));
        RankSettings.reportMode = true;
        List<RankItem> words = new ArrayList<>();
        for (String s : ranker.getItemsMap().keySet()) {
            RankItem item = new RankItem(s);
            ranker.rank(item);
            if (item.getScore() > 0)
                words.add(item);
        }

        words.sort(RankItem::descOrder);

        StringBuilder sb = new StringBuilder();
        for (RankItem item : words) {
            System.out.println(String.format("%s: %f", item.getKey(), item.getScore()));
            //sb.append(String.format("%s: %f", item.getKey(), item.getScore())).append("\n");
            sb.append(item.getKey()).append("\n");
        }
        try {
            str2File(sb.toString(), getRawHome() + "/wordsrerank.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void loadFeelingwords() {
        List<WordFeeling> wordFeelingList = null;
        try {
            wordFeelingList = JSON.parseArray(file2Str(getJsonHome() + "/wordfeeling"), WordFeeling.class);

            for (WordFeeling feeling : wordFeelingList) {
                if (feeling.getPolar() == 1) {
                    //Words.positiveSet.add(feeling.getWord());
                } else if (feeling.getPolar() == 2) {
                    //Words.negativeSet.add(feeling.getWord());
                }
            }
        } catch (IOException e) {
        }
    }
}
