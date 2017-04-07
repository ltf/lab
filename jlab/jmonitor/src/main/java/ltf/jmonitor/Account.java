package ltf.jmonitor;

import com.alibaba.fastjson.annotation.JSONField;
import ltf.jmonitor.simulate.SimulateOID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author ltf
 * @since 17/4/7, 下午3:02
 */
public class Account {

    static private Logger log = LoggerFactory.getLogger(Account.class);
    static Random random = new Random();

    public Account() {
    }

    public Account(String un, String pwd) {
        this.un = un;
        this.pwd = pwd;
    }

    @JSONField(serialize = false)
    private long willRun;
    @JSONField(serialize = false)
    private int pageChangedMode = 0;

    public String un;
    public String pwd;
    public int itvBase = 15;
    public int itvRnd = 15;

    public void checkOrRun() {
        long tc = System.currentTimeMillis();
        if (tc > willRun) {
            try {
                log.info(un + " start submit");
                new SimulateOID().summitOid(un, pwd);
                log.info(un + " finish submit");
            } catch (Exception e) {
                log.error(un + " submit failed", e);
            }
            updateNextRunTime(tc);
        }

    }

    private void updateNextRunTime(long currentMillis) {
        int m;
        switch (pageChangedMode) {
            case 5:
                m = 1;
                pageChangedMode--;
                break;
            case 4:
                m = 2;
                pageChangedMode--;
                break;
            case 3:
                m = 3;
                pageChangedMode--;
                break;
            case 2:
                m = 5;
                pageChangedMode--;
                break;
            case 1:
                m = 8;
                pageChangedMode--;
                break;
            default:
                m = itvBase + random.nextInt(itvRnd);
        }
        willRun = currentMillis + 1000 * 60 * 60 * m;
        log.info("%s will submit after %d minutes later", un, m);
    }

    public void notifyPageChanged() {
        pageChangedMode = 5;
        willRun = 0;
    }
}
