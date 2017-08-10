package PageObjects.Base;


import core.Tools.Configuration.EnviromentSettings;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Piotr Majewski on 2017-05-16.
 * Klasa bazowa wszystkich Page objektow jakie beda tworzone
 */
public class PageObject {
    protected WebDriver driver;
    protected Wait wait;
    protected String baseUrl;

    public PageObject() {
    }

    public PageObject(WebDriver driver) {
        //pobieramy adres testowanej strony
        baseUrl = EnviromentSettings.getTestEnviroment();
        this.driver = driver;
        wait = new WebDriverWait(driver, 60);
        PageFactory.initElements(driver, this);

    }



}
