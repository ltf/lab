package ltf.namerank.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ltf
 * @since 7/10/16, 12:20 AM
 */
public class EvenManager {
    private static List<TeardownListener> teardownListeners = new ArrayList<>();

    public static void addTeardownListener(TeardownListener listener) {
        teardownListeners.add(listener);
    }

    public static void notifyTeardown() {
        for (TeardownListener listener : teardownListeners) {
            try {
                listener.onTeardown();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


}
