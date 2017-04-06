package ltf.jmonitor.simulate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author ltf
 * @since 17/4/6, 下午6:05
 */
public class SimulateOASIS {
    protected WebDriver mDriver;

    protected void openOASIS() throws MalformedURLException {
        mDriver = new RemoteWebDriver(
                new URL("http://10.2.42.240:9666/wd/hub"),
                DesiredCapabilities.chrome()
        );
        mDriver.get("https://immigration.saskatchewan.ca");
        new WebDriverWait(mDriver, 30).until(wd ->
                wd.findElement(By.id("P101_USERNAME")) != null &&
                        wd.findElement(By.id("P101_PASSWORD")) != null &&
                        wd.findElement(By.id("P101_LOGIN")) != null
        );
    }

    protected void quit() {
        mDriver.quit();
        mDriver = null;
    }

    protected void logout() {
        mDriver.findElement(By.linkText("Log Out")).click();
        new WebDriverWait(mDriver, 30).until(
                new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver input) {
                        return input.findElement(By.id("P102_LOGIN"));
                    }
                }
        );
    }

    protected void login(String username, String password) {
        mDriver.findElement(By.id("P101_USERNAME")).sendKeys(username);
        mDriver.findElement(By.id("P101_PASSWORD")).sendKeys(password);
        mDriver.findElement(By.id("P101_LOGIN")).click();
        new WebDriverWait(mDriver, 30).until(
                new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver input) {
                        return input.findElement(By.linkText("Log Out"));
                    }
                }
        );
    }
}
