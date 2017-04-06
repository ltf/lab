package ltf.jmonitor;

import ltf.jmonitor.simulate.SimulateOID;

import java.net.MalformedURLException;
import java.util.Random;

/**
 * @author ltf
 * @since 17/4/6, 下午2:43
 */
public class Loader {
    static Random random = new Random();


    public static void main(String[] args) throws MalformedURLException {

        for (; ; ) {
            try {
                SimulateOID simulateOID = new SimulateOID();
                simulateOID.summitOid("", "");
                Thread.sleep(1000 * 60 * 60 * (8 + random.nextInt(8)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
