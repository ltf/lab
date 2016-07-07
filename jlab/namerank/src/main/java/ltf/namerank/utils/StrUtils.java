package ltf.namerank.utils;

import com.hankcs.hanlp.HanLP;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

/**
 * @author ltf
 * @since 16/6/20, 上午10:00
 */
public class StrUtils {

    /**
     * 随机生成一个汉字
     */
    //在0x4e00---0x9fa5之间产生一个随机的字符
    public static char getRandomHanzi() {
        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }

    /**
     * 获取指定长度随机简体中文
     *
     * @param len int
     * @return String
     */
    public static String getRandomJianHan(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBk"); //转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    /**
     * 统计关键词在内容中出现的次数
     */
    public static int existsCount(final String content, final String keyword) {
        if ("".equals(keyword)) return 0;
        int count = 0;
        int offset = 0;
        while ((offset = content.indexOf(keyword, offset) + 1) > 0) count++;
        return count;
    }

    /**
     * distinct lines, not break the order
     */
    public static void distinct(final Iterable<String> in, Collection<String> out) {
        HashSet<String> set = new HashSet<>();
        in.forEach(s -> {
            s = HanLP.convertToSimplifiedChinese(s);
            if (!set.contains(s)) {
                set.add(s);
                out.add(s);
            }
        });
    }
}
