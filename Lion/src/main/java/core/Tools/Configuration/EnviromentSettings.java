package core.Tools.Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class EnviromentSettings {
    //Adres testowanej aplikacji
    private static String currentUrl;

    public static String GetTestEnviroment() {
        return currentUrl;
    }

    //Ustawiam adres aplikacji testowej
    public void SetTestEnviroment(String urlForPageToSet) {
        currentUrl = urlForPageToSet;
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
        driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

}
