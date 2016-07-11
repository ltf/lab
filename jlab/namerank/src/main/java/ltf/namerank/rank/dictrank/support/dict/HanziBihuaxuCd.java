package ltf.namerank.rank.dictrank.support.dict;

import java.util.HashMap;
import java.util.Map;

import static ltf.namerank.utils.PathUtils.getRawHome;

/**
 * @author ltf
 * @since 16/7/11, 上午8:22
 */
public class HanziBihuaxuCd extends MdxtDict {
    @Override
    protected String getFileName() {
        return getRawHome() + "/mdx/汉字笔画序.txt";
    }

    @Override
    protected MdxtItem newItem(String key) {
        return new BihuxuItem(key);
    }

    private static class BihuxuItem extends MdxtItem {
        private static final String SKIP_KEY = "0凡例---";
        private String word;
        private String bihua;
        private boolean keyIsBihua;

        BihuxuItem(String key) {
            super(key);
            keyIsBihua = isAllNumber(key);
        }

        private boolean isAllNumber(String s) {
            for (char c : s.toCharArray()) {
                if (c > '9' || c < '0') return false;
            }
            return true;
        }

        @Override
        protected void addValue(String valueLine) {
            if (SKIP_KEY.equals(getKey())) return;
            if (word != null) throw new IllegalStateException(getKey());
            if (keyIsBihua) {
                bihua = getKey();
                word = getValue(valueLine);
                //if (!bihuaMap.containsKey(word)) bihuaMap.put(word, bihua);
                if (!("2`丨".equals(word) || "2`亅".equals(word)))
                    addToBihuaMap(word, bihua);
            } else {
                word = getKey();
                bihua = getValue(valueLine);
                addToBihuaMap(word, bihua);
                //if (valueLine.endsWith(bihua.))
            }
        }

        private void addToBihuaMap(String word, String bihua) {
            if (word.length() > 1) throw new IllegalStateException(getKey() + " = len>1 = " + word);
            if (bihuaMap.containsKey(word)) {
                //throw new IllegalStateException(getKey() + " = repeated = " + word);
                if (!bihua.equals(bihuaMap.get(word)))
                    throw new IllegalStateException(getKey() + " = different = " + word);

            } else {
                bihuaMap.put(word, bihua);
                //bihuaMap.put(word.toCharArray()[0], bihua);
            }
        }

        private String getValue(String line) {
            line = line.replaceAll("\t", " ");
            int start = line.indexOf("`2`") + 3;
            int end = line.indexOf(" ", start);
            if (start < 0 || end < 0) {
                throw new IllegalStateException(getKey());
            }
            return line.substring(start, end);
        }

        @Override
        protected boolean isValid() {
            return !SKIP_KEY.equals(getKey());
        }
    }


    private static Map<String, String> bihuaMap = new HashMap<>();

    private static void saveData() {
//        try {
//            str2File(JSON.toJSONString(bihuaMap, WriteClassName, PrettyFormat), getJsonHome()+"/bihua.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void listKeys() {
        initItems();
        System.out.println(itemsMap.size());
        System.out.println(bihuaMap.size());
        System.out.println(itemsMap.size() - bihuaMap.size());

    }
}
