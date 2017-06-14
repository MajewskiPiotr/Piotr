package core.Tools.Configuration;

import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
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

    public WebDriver setUpDriver(String url, String browserType, Boolean isRemote) throws MalformedURLException {
        if (isRemote) {
            return setUpRemoteDriver(url, browserType);
        } else {
            return setUpLocalDriver(browserType);
        }

    }

    private WebDriver setUpRemoteDriver(String url, String browserType) throws MalformedURLException {
        URL nodeUrl = new URL(url);
        WebDriver driver = null;

        switch (browserType) {
            case BrowserType.CHROME: {
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setPlatform(Platform.VISTA);
                capabilities.setBrowserName("chrome");
                driver = new RemoteWebDriver(nodeUrl, capabilities);
                break;
            }
            case BrowserType.FIREFOX: {
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setPlatform(Platform.VISTA);
                capabilities.setBrowserName("firefox");
                driver = new RemoteWebDriver(nodeUrl, capabilities);
                break;
            }
            default: {
                Assert.fail("Nie ubsługiwana przeglądarka");
            }
        }
        return driver;
    }
    //Funckja ustawia parametry przeglądarki w zaleznosci od dokonanego wyboru.
    private WebDriver setUpLocalDriver(String browserType) {
        String basePath = Property.getProperty("basePath") + "/Lion/src/resources";
        WebDriver driver = null;
        switch (browserType) {
            case BrowserType.CHROME: {
                System.setProperty("webdriver.chrome.driver", basePath + "\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            }
            case BrowserType.FIREFOX: {
                System.setProperty("webdriver.gecko.driver", basePath + "\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            }
        }
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

}
