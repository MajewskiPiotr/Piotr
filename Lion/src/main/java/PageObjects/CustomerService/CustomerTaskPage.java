package PageObjects.CustomerService;

import core.Tools.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class CustomerTaskPage extends AbstractCustomerServicePage {

    @FindBy(xpath = "//*[@id='comment-on-request']")
    protected WebElement poleWprowadzaniaTekstu;
    @FindBy(xpath = "//*[@id='comment-form']//*[@value='Add']")
    protected WebElement addButton;
    @FindBy(xpath = "//*[@id='content']//*[@class='vp-request-header']/span[2]")
    protected WebElement status;
    @FindBy(xpath = "//*[@class='vp-activity-list']//*//div[contains(@class, 'comment')]")
    protected List<WebElement> listaKomentarzy;

    public CustomerTaskPage(WebDriver driver) {
        super(driver);
    }

    public String getStatus() {
        return status.getText();
    }

    public void respondToAgent(String komentarz) {
        new Actions(driver).moveToElement(poleWprowadzaniaTekstu).click(poleWprowadzaniaTekstu).sendKeys(komentarz)
                .moveToElement(addButton).click(addButton).perform();
        Tools.waitForProcesing(4000);
    }

    public boolean verifyCommentExist(String komentarz) {
        boolean exist = false;
        for (WebElement webElement : listaKomentarzy) {
            if (webElement.getText().contains(komentarz)) {
                exist = true;
                break;
            }
        }
        Tools.waitForProcesing(2000);
        driver.navigate().refresh();
        return exist;
    }

    public boolean isSlaExist() {
        boolean is =  driver.findElement(By.xpath("//*[contains(@class,'sla-tag')]")).isDisplayed();
        System.out.println("czy jest element " + is );
        return is;
    }
}
