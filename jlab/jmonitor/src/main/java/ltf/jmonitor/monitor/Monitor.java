package ltf.jmonitor.monitor;

import java.util.ArrayList;

/**
 * @author ltf
 * @since 4/7/17, 11:13 PM
 */
public class Monitor {

    private static ArrayList<Target> targets = new ArrayList<>();

    static {
        targets.add(new Target("oid", "http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/applicants-international-skilled-workers/international-skilled-worker-occupations-in-demand"));
        targets.add(new Target("max", "http://www.saskatchewan.ca/residents/moving-to-saskatchewan/immigrating-to-saskatchewan/saskatchewan-immigrant-nominee-program/maximum-number-of-sinp-applications"));
    }

    private int idx = 0;
    private long nextMillis;

    public boolean isPageChanged() {
        boolean result = false;
        long millis = System.currentTimeMillis();
        if (millis < nextMillis) return false;

        nextMillis = millis + 1000 * 5;


        try {
            if (targets.get(idx).isChanged())
                result = true;
            idx++;
            if (idx >= targets.size()) idx = 0;
        } catch (Exception e) {

        }
        return result;
    }

}
