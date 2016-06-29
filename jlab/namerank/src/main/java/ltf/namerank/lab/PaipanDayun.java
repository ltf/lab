package ltf.namerank.lab;

import java.text.ParseException;
import java.util.Calendar;

/**
 * 排大运
 *
 * @author ltf
 * @since 16/6/29, 上午10:10
 */
public class PaipanDayun {

    PaipanShenshahehun myPaipanShenshahehun = new PaipanShenshahehun();
    PaipanLvhehun myPaipanLvhehun = new PaipanLvhehun();

    /**
     * @param man 生日 yyyy-MM-dd HH
     * @return 返回乾造
     * @throws ParseException
     */
    public String paipan(String man, PaipanLvhehun.sex isman) throws ParseException {

        Calendar mancal;

        try {
            mancal = myPaipanShenshahehun.getCalendarfromString(man, "yyyy-MM-dd HH");
            //原来的private 方法改了下  
        } catch (ParseException ex) {
            return "输入不正确" + ex.getMessage();
        }

        return paipan(mancal, isman);

    }


    /**
     * 找数组中月柱起始位置
     *
     * @param jiazhi
     * @param yuezhu
     * @return
     */
    public int getyuezhuStart(String[] jiazhi, String yuezhu) {

        int start = -1;
        for (int i = 0; i < jiazhi.length; i++) {
            if (yuezhu.equals(jiazhi[i])) {
                start = i;
                break;
            }

        }

        return start;
    }
//顺行排大运  

    private String[] shundayun(String yuezhu) {

        String[] DayunStringArray = new String[8];//取八个  

        String[] jiazhi = myPaipanLvhehun.jiazhi;
        int start = getyuezhuStart(jiazhi, yuezhu);
        if (start == -1) {
            return null;
        } else {
            start++;
        }
        for (int i = 0; i < 8; i++) {
            DayunStringArray[i] = jiazhi[(start + i) % jiazhi.length];
        }

        return DayunStringArray;

    }
//逆行排大运  

    private String[] nidayun(String yuezhu) {
        String[] DayunStringArray = new String[8];//取八个  

        String[] jiazhi = myPaipanLvhehun.jiazhi;
        int start = getyuezhuStart(jiazhi, yuezhu);
        if (start == -1) {
            return null;
        } else {
            start--;
        }
        for (int i = 0; i < 8; i++) {
            DayunStringArray[i] = jiazhi[(start - i) % jiazhi.length];
        }

        return DayunStringArray;

    }
    //大运用月柱排  

    public String[] Dayun(String nianzhu, String yuezhu, PaipanLvhehun.sex isman) {
        String[] DayunStringArray = null;
        if (yuezhu == null || yuezhu.length() != 2) {
            return null;
        }


        //甲、丙、戊、庚、壬之年为阳，乙、丁、己、辛、癸之年为阴  
        //阴年生男子（或阳年生女子），大运逆行否则顺行  
        if (nianzhu.startsWith("甲") || nianzhu.startsWith("丙") || nianzhu.startsWith("戊") || nianzhu.startsWith("庚") || nianzhu.startsWith("庚") || nianzhu.startsWith("壬")) {
            if (isman == PaipanLvhehun.sex.man) {//顺行  
                DayunStringArray = shundayun(yuezhu);
            } else {
                DayunStringArray = nidayun(yuezhu);
            }

        } else {
            if (isman == PaipanLvhehun.sex.man) {
                DayunStringArray = nidayun(yuezhu);
            } else {
                DayunStringArray = shundayun(yuezhu);
            }

        }
        return DayunStringArray;
    }

    private String paipan(Calendar cal, PaipanLvhehun.sex isman) throws ParseException {

        BaZi lunar = new BaZi(cal);
        System.out.println("此人农历的日期【" + lunar.toString() + "】");
        /**
         * 很多地方都是按照23：00-1：00为子时这是不对的。 
         * 子时24.00－2.00,丑时2.00－4.00,寅时4.00－6.00,卯时6.00－8.00, 
         * 辰时8.00－10.00,巳时10.00－12.00,午时12.00－14.00,未时14.00－16.00 
         * 申时16.00－18.00,酉时18.00－20.00,戌时20.00－22.00,亥时22.00－24.00 
         *
         */
        int time = cal.get(Calendar.HOUR_OF_DAY) / 2;
        System.out.println("此人八字【" + lunar.getYearGanZhi(time) + "】");
        //获取生肖  
        System.out.println("此人的农历生肖【" + lunar.animalsYear() + "】");


        String GanZhi = lunar.getYearGanZhi(time);//取八字
        String[] tempchar = GanZhi.split(",");
        //我修改原来的，用,分割  
        String ganziyear = tempchar[0];//年柱  
        String ganzimonth = tempchar[1];//月柱  
        String ganziday = tempchar[2];//日柱  
        String ganzitime = tempchar[3];//时柱  

        //五行纳音  

        String soundyear = myPaipanShenshahehun.getnumsix(ganziyear);
        String soundmonth = myPaipanShenshahehun.getnumsix(ganzimonth);
        String soundday = myPaipanShenshahehun.getnumsix(ganziday);
        String soundtime = myPaipanShenshahehun.getnumsix(ganzitime);

        String[] DayunArray = Dayun(ganziyear, ganzimonth, isman);
        pringst(DayunArray);
        return null;
    }

    public static void pringst(String[] res) {
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
            System.out.print("   ");
        }
        System.out.println("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        PaipanDayun my = new PaipanDayun();
        my.paipan("2000-3-13 11", PaipanLvhehun.sex.man);

    }
}  
