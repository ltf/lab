package ltf.namerank.dataprepare;

import com.util.LunarCalendar;
import ltf.namerank.nongli.ShouxingCalendar;

import java.util.Calendar;

/**
 * @author ltf
 * @since 16/6/17, 上午10:55
 */
public class CalendarTest {
    public void go() {
        //String dateStr = "2015-2-4"
        Calendar birth = Calendar.getInstance();
        //birth.set(1987, 4, 13, 21, 46, 00);
        birth.set(2016, Calendar.JUNE, 23, 21, 46, 00);
        LunarCalendar calendar = new LunarCalendar(birth.getTimeInMillis());
//        LunarCalendar calendar = new LunarCalendar();
        System.out.println("getAnimalString=" + calendar.getAnimalString());
        System.out.println("getDateString=" + calendar.getDateString());
        System.out.println("getDay=" + calendar.getDay());
        System.out.println("getDayString=" + calendar.getDayString());
        System.out.println("getGanZhiDateString=" + calendar.getGanZhiDateString());
        System.out.println("getMaxDayInMonth=" + calendar.getMaxDayInMonth());
        System.out.println("getMonth=" + calendar.getMonth());
        System.out.println("getMonthString=" + calendar.getMonthString());
        System.out.println("getYear=" + calendar.getYear());
        System.out.println("getYearString=" + calendar.getYearString());

        String[] strs = calendar.getAllSolarTerm();
        for (String str : strs) {
            System.out.println(str);
        }


        ShouxingCalendar st = new ShouxingCalendar();
        st.JQtest(2016);

    }
}
