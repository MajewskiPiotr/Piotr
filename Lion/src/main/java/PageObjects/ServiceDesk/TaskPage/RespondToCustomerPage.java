package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class RespondToCustomerPage extends PageObject {

    @FindBy(xpath = "//*[@class='rte-container']")
    protected WebElement poleWprowadzaniaTekstu;
    @FindBy(xpath = "//*[@id='issue-workflow-transition-submit']")
    protected WebElement respondToCustomerButton;

    public RespondToCustomerPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='jira-dialog-content']")));

    }

    public void przekazDoCustomera(String komenarz) {
        new Actions(driver).moveToElement(poleWprowadzaniaTekstu).click(poleWprowadzaniaTekstu).sendKeys(poleWprowadzaniaTekstu, komenarz).perform();
        respondToCustomerButton.click();
    }
}
