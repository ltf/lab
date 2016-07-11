package ltf.namerank.rank.dictrank.support.dict;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static ltf.namerank.utils.FileUtils.str2File;
import static ltf.namerank.utils.PathUtils.getJsonHome;
import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/7/11, 上午8:23
 */
public class HanyuCipinCd extends MdxtDict {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/汉语词频.txt";
    }

    @Override
    protected MdxtItem newItem(String key) {
        return new CipinItem(key);
    }

    private static HanYuDaCidian hanYuDaCidian = new HanYuDaCidian();

    private static class CipinItem extends MdxtItem {

        private boolean enable;
        private ltf.namerank.rank.dictrank.support.CipinItem cipin;

        CipinItem(String key) {
            super(key);
            hanYuDaCidian.initItems();
            enable = hanYuDaCidian.exists(key);
            //enable = "成功".equals(key);
        }

        private static final String psCJ = "词级：(\\d{1,9})";
        private final Pattern pCJ = Pattern.compile(psCJ);
        private static final String psCC = " 词次：(\\d{1,9})";
        private final Pattern pCC = Pattern.compile(psCC);
        private static final String psJG = "结构词次：(\\d{1,9})";
        private final Pattern pJG = Pattern.compile(psJG);

        @Override
        protected void addValue(String valueLine) {
            if (!enable) return;
            if (cipin == null) cipin = new ltf.namerank.rank.dictrank.support.CipinItem();
            if (valueLine.contains(">国<")) {
                Matcher matcher = pCJ.matcher(valueLine);
                if (matcher.find()) {
                    cipin.gj = Integer.parseInt(matcher.group(1));
                }
            } else if (valueLine.contains(">学<")) {
                Matcher matcher = pCJ.matcher(valueLine);
                if (matcher.find()) {
                    cipin.xj = Integer.parseInt(matcher.group(1));
                }
                matcher = pCC.matcher(valueLine);
                if (matcher.find()) {
                    cipin.xc = Integer.parseInt(matcher.group(1));
                }
            } else if (valueLine.contains(">报<")) {
                Matcher matcher = pCC.matcher(valueLine);
                if (matcher.find()) {
                    cipin.bc = Integer.parseInt(matcher.group(1));
                }
                matcher = pJG.matcher(valueLine);
                if (matcher.find()) {
                    cipin.bg = Integer.parseInt(matcher.group(1));
                }
            }
        }

        @Override
        protected boolean isValid() {
            return enable;
        }

        @Override
        protected void finishAdd() {
            if (cipin != null) cipins.put(getKey(), cipin);
            super.finishAdd();
        }
    }

    private static Map<String, ltf.namerank.rank.dictrank.support.CipinItem> cipins = new HashMap<>();


    private static void saveData() {
        try {
            str2File(JSON.toJSONString(cipins, PrettyFormat), getJsonHome() + "/cipin.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listKeys() {
        initItems();
        System.out.println(cipins.size());
        //saveData();
    }
}
