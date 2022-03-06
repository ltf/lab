package ltf.namerank.rank.wuge;

import com.alibaba.fastjson.TypeReference;
import ltf.namerank.rank.RankFilter;

import java.io.IOException;
import java.util.HashMap;

import static ltf.namerank.utils.FileUtils.fromJsData;

/**
 * @author ltf
 * @since 2022/3/4, 1:20
 */
public class WugeFilter implements RankFilter {
    private HashMap<String, Integer> char2Stroke;

    @Override
    public boolean banned(String givenName) {
        if (givenName.length() != 2) {
            return true;
        }

        if (char2Stroke == null) {
            try {
                char2Stroke = fromJsData("selected_wuge_char2stroke", new TypeReference<HashMap<String, Integer>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return !WugePicker.StrokePicker.isGood(
                char2Stroke.getOrDefault(String.valueOf(givenName.charAt(0)), 0),
                char2Stroke.getOrDefault(String.valueOf(givenName.charAt(1)), 0)
        );
    }
}
