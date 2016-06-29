package ltf.namerank.lab;

import java.util.Calendar;

/**
 * @author ltf
 * @since 16/6/29, 下午3:01
 */
public class GanzhiTest {

    public final static String[] Gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    public final static String[] Zhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    public void go() {
        verifyBazi();
    }


    private void verifyBazi() {

        Calendar bao = Calendar.getInstance();
        bao.set(2016, Calendar.JUNE, 23);

        // 乾隆
        // 辛卯  丁酉  庚午  丙子
        Calendar qianlong = Calendar.getInstance();
        qianlong.set(1711, Calendar.SEPTEMBER, 25);
        System.out.println("qianlong-bao: " + offsetGanzhi(qianlong, "庚","午", bao));


        // 董中堂 http://blog.sina.com.cn/s/blog_9b6237b80102v5ax.html
        // 董诰（1740～1818）清代官员、书画家。字雅伦，一字西京，号蔗林，一号柘林，董邦达长子，与其父有“大、小董”之称，浙江富阳人。乾隆二十九年进士，授翰林院庶吉士。以善画，受高宗知，历任内阁学士，擢公、户、吏、刑部侍郎，充四库馆副总裁。累官至东阁大学士、太子太傅，直军机先后四十年。
        // 董中堂（清高宗乾隆五年三月廿七日1740/4/23）
        // 八字：庚申  庚辰  戊辰 戊午（4岁起运）
        // 清明：4月4日  谷雨：4月20日
        // 大运：4辛巳  14壬午  24癸未  34甲申 44乙酉  54丙戌  64丁亥 74戊子
        // （赋云：拱禄拱贵，纯粹者王候之论。又云：夹贵夹禄，必居八座之尊。辰，为收藏，申辰拱子为墨池，伤官配印能通琴棋书画，故而又是书画家。庚金食神为寿元星，运至子运，庚金入死地。）
        Calendar dong = Calendar.getInstance();
        dong.set(1740, Calendar.APRIL, 23);
        System.out.println("qianlong-dong: " + offsetGanzhi(qianlong, "庚","午", dong));
        System.out.println("dong-bao: " + offsetGanzhi(dong, "戊","辰", bao));
    }

    private String offsetGanzhi(Calendar from, String fromGan, String fromZhi, Calendar to) {
        String result = "";
        long between_days = (to.getTimeInMillis() - from.getTimeInMillis()) / (1000 * 3600 * 24);
        return Gan[(indexOf(Gan, fromGan) + (int) (between_days % 10)) % 10] + " " +
                Zhi[(indexOf(Zhi, fromZhi) + (int) (between_days % 12)) % 12];
    }

    private int indexOf(String[] set, String target) {
        for (int i = 0; i < set.length; i++) {
            if (target.equals(set[i])) return i;
        }
        return -1;
    }

}
