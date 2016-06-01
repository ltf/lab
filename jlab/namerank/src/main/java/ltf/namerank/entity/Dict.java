package ltf.namerank.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltf
 * @since 16/6/1, 上午11:13
 */
public class Dict {

    private int count = 0;

    @JSONField
    private Map<String, List<Hanzi>> dict = new HashMap<String, List<Hanzi>>();

    public Map<String, List<Hanzi>> getDict() {
        return dict;
    }

    public void setDict(Map<String, List<Hanzi>> dict) {
        this.dict = dict;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void add(Hanzi hanzi) {
        if (hanzi == null)
            return;

        List<Hanzi> list = dict.get(hanzi.getKword());
        if (list == null) {
            list = new ArrayList<Hanzi>(2);
            list.add(hanzi);
            dict.put(hanzi.getKword(), list);
        } else {
            list.add(hanzi);
        }
        count++;
    }

}
