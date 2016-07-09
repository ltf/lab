package ltf.namerank.utils;

import java.util.*;

/**
 * Runtime counter
 *
 * @author tengfei.li@renren-inc.com
 * @since 14-1-29, 上午11:43
 */
public final class Rtc {

    /**
     * time counters
     */
    private static Map<String, TimeCounter> counters = new HashMap<String, TimeCounter>();

    /**
     * begin count
     */
    public static void begin() {
        String key = getKey();
        TimeCounter counter;

        if (counters.containsKey(key)) {
            counter = counters.get(key);

        } else {
            counter = new TimeCounter();
            counters.put(key, counter);
        }

        counter.begin();
    }

    /**
     * finish count
     */
    public static void end() {
        String key = getKey();
        TimeCounter counter = counters.get(key);
        if (counter != null) counter.end();
    }

    /**
     * get call position key
     */
    private static String getKey() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement stack = stacks[3];

        return stack.getClassName() + "->" + stack.getMethodName();
    }

    /**
     * get counted time cost report
     */
    public static String getReport() {
        StringBuilder sb = new StringBuilder();

        ValueCompare vc =  new ValueCompare(counters);
        TreeMap<String, TimeCounter> sorted = new TreeMap<String, TimeCounter>(vc);
        sorted.putAll(counters);

        for (Map.Entry<String, TimeCounter> it : sorted.entrySet()) {
            sb.append(it.getKey());
            sb.append(": ");
            sb.append(timelen(it.getValue().count));
            sb.append("\n");
        }

        return sb.toString();
    }

    private static String timelen(long milliseconds) {
        String result = Long.toString(milliseconds % 1000);
        long left = milliseconds / 1000;
        if (left > 0) {
            result = Long.toString(left % 60) + "s " + result;
            left = left / 60;
        }
        if (left > 0) {
            result = Long.toString(left % 60) + "m " + result;
            left = left / 60;
        }
        if (left > 0) {
            result = Long.toString(left) + "h " + result;
        }

        return result;
    }

    private static class TimeCounter {
        private long start;
        private long count = 0;

        public void begin() {
            start = System.currentTimeMillis();
        }

        public void end() {
            count += System.currentTimeMillis() - start;
        }
    }

    private static class ValueCompare implements Comparator<String>{
        Map<String, TimeCounter> counters;

        private ValueCompare(Map<String, TimeCounter> counters) {
            this.counters = counters;
        }

        @Override
        public int compare(String k1, String k2) {
            return (int)(counters.get(k1).count - counters.get(k2).count);
        }
    }
}
