package ltf.jmonitor;

import com.alibaba.fastjson.TypeReference;
import ltf.jmonitor.simulate.SimulateOID;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import static ltf.jmonitor.utils.FileUtils.fromJsData;

/**
 * @author ltf
 * @since 17/4/6, 下午2:43
 */
public class Loader {
    static Random random = new Random();


    public static void main(String[] args) throws MalformedURLException {
        try {
            ArrayList<Account> accounts = fromJsData("accouts", new TypeReference<ArrayList<Account>>() {
            });

            if (accounts.size()==0) return;

            for (; ; ) {
                try {
                    SimulateOID simulateOID = new SimulateOID();
                    simulateOID.summitOid(accounts.get(0).un, accounts.get(0).pwd);
                    Thread.sleep(1000 * 60 * 60 * (8 + random.nextInt(8)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
