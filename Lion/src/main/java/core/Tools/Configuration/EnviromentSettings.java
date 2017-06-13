package core.Tools.Configuration;

import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class EnviromentSettings {
    //Adres testowanej aplikacji
    private static String currentUrl;

    public static String getTestEnviroment() {
        if (currentUrl == null) {
            Assert.fail("Nie udało się ustawić adresu (URL) testowanej aplikacji");
        }
        return currentUrl;
    }

    //Ustawiam adres aplikacji testowej
    public void SetTestEnviroment(String urlForPageToSet) {
        currentUrl = urlForPageToSet;
    }

    public WebDriver setUpRemoteDriver(String url) throws MalformedURLException {
        URL nodeUrl = new URL(url);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setPlatform(Platform.VISTA);
        capabilities.setBrowserName("chrome");
        return new RemoteWebDriver(nodeUrl, capabilities);
    }


    //Funckja ustawia parametry przeglądarki w zaleznosci od dokonanego wyboru.
    public WebDriver setUpDriver(BrowserType browserType) {
        String basePath = Property.getProperty("basePath") + "/Lion/src/resources";
        WebDriver driver = null;
        switch (browserType) {
            case CHROME: {
                System.setProperty("webdriver.chrome.driver", basePath + "\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            }
            case MOZILLA: {
                System.setProperty("webdriver.gecko.driver", basePath + "\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            }
        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

}
