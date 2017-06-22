package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.PageObject;
import core.Tools.JiraWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class EditIssuePage extends PageObject {
    @FindBy(xpath = "//*[@id='edit-issue-submit']")
    protected WebElement updateButton;

    @FindBy(id = "customfield_10615-field")
    protected WebElement productDropdown;

    @FindBy(id = "customfield_10401-field")
    protected WebElement categoryDropdown;

    @FindBy(id = "customfield_10614-field")
    protected WebElement productClassDropdown;


    public EditIssuePage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='jira-dialog-content']")));
    }

    //metoda uzupełnia pola randomowymi wartościami
    public void sklasyfikujIssue() {
        setProduct();
        setCategory();
        setProductClass();
        update();
    }

    private void setProduct() {
        productDropdown.click();
        List<WebElement> listOdProduct = driver.findElements(By.xpath("//*[@id='showing-first-25-objects']/*"));
        //Random random = new Random();
        //listOdProduct.get(random.nextInt(listOdProduct.size() - 1)).click();
        listOdProduct.get(2).click();

    }

    private void setCategory() {
        categoryDropdown.click();
        List<WebElement> listOfCategory = driver.findElements(By.xpath("//*[@id='all-objects']/*"));
        Random random = new Random();
        listOfCategory.get(random.nextInt(listOfCategory.size() - 1)).click();

    }

    private void setProductClass() {
        JiraWait.waitForProcesing(2000);
        String SLA = driver.findElement(By.xpath("//*[@id='evercode-sla-field']")).getText();
        if (SLA.contains("none")) {
            Assert.fail("Brak zdefiniowanego Product Class dla wybranych opcji");
        } else {
            productClassDropdown.click();
            productClassDropdown.sendKeys(Keys.ENTER);

        }
    }


    private void update() {
        updateButton.click();
        //czekam na zamknięcie sie okna z edycją Issue
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='descriptionmodule_heading']")));
    }
}
