package Tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class EnviromentSettings {
    //Adres testowanej aplikacji
    private static String currentUrl;

    //Ustawiam adres aplikacji testowej
    public void SetTestEnviroment(String urlForPageToSet) {
        currentUrl = urlForPageToSet;
    }

    public static String GetTestEnviroment() {
        return currentUrl;
    }

    //Funckja ustawia parametry przeglądarki w zaleznosci od dokonanego wyboru.
    public WebDriver setUpDriver(BrowserType browserType) {
        WebDriver driver = null;
        switch (browserType) {
            case CHROME: {
                System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            }
            case MOZILLA: {
                System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
                driver = new ChromeDriver();
                break;
            }


        }
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }




}
