package PageObjects.CustomerService;

import core.Tools.JiraWait;
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
        JiraWait.waitForProcesing(4000);
    }

    public boolean zweryfikujIstnienieKomentarza(String komentarz) {
        boolean exist = false;
        for (WebElement webElement : listaKomentarzy) {
            if (webElement.getText().contains(komentarz)) {
                exist = true;
                break;
            }
        }
        return exist;
    }
}
