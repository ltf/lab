package ltf.jmonitor.simulate;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

/**
 * @author ltf
 * @since 17/4/6, 下午6:11
 */
public class SimulateOID extends SimulateOASIS {

    public boolean summitOid(String username, String password) throws MalformedURLException {
        try {
            openOASIS();
            login(username, password);
            toContinueSINP();
            submitReq();
            continueReq();
            try {
                Thread.sleep(1000 * 60*2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logout();
        } finally {
            quit();
        }
        return false;
    }


    protected void toContinueSINP(){
        mDriver.findElement(By.cssSelector("a[title=\"Continue SINP Application\"]")).click();
        new WebDriverWait(mDriver, 30).until(wd ->
                wd.findElement(By.cssSelector("a[title=\"Check All Application Screens and Submit\"]"))
        );
    }

    protected void submitReq(){
        mDriver.findElement(By.cssSelector("a[title=\"Check All Application Screens and Submit\"]")).click();

        new WebDriverWait(mDriver, 30).until(wd ->
                wd.findElement(By.partialLinkText("Yes, Continue"))
        );
    }

    protected void continueReq(){
        mDriver.findElement(By.partialLinkText("Yes, Continue")).click();

    }
}
