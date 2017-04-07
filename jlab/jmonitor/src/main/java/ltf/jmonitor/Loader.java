package ltf.jmonitor;

import com.alibaba.fastjson.TypeReference;
import ltf.jmonitor.monitor.Monitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static ltf.jmonitor.utils.FileUtils.fromJsData;

/**
 * @author ltf
 * @since 17/4/6, 下午2:43
 */
public class Loader {
    static Random random = new Random();


    public static void main(String[] args) throws IOException {
        ArrayList<Account> accounts = fromJsData("accouts", new TypeReference<ArrayList<Account>>() {
        });

        if (accounts.size() == 0) return;
        Monitor monitor = new Monitor();

        for (; ; ) {
            for (Account a : accounts) {
                a.checkOrRun();
            }

            if (monitor.isPageChanged()) {
                for (Account a : accounts) {
                    a.notifyPageChanged();
                    a.checkOrRun();
                }
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
