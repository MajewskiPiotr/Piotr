package PageObjects.Base;

import PageObjects.MainPage.DashboardPage;
import core.ElementsOnPages.Task.*;
import core.Tools.FindInTaskList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 * KLasa abstracyjna. Kazda Page Task powinien dziedziczyc po tej klasie.
 */

//TODO w środosiku produkcyjnych można potworzyć podKlasy i umiescić w nich elementy z których nie wszyscy korzystają (np. AssignToTranslator uzywa tylko Edytor
public abstract class AbstractTaskPage extends AbstractJiraPage {


    //Guziki
    @FindBy(id = "action_id_11")
    protected WebElement acceptButton;
    @FindBy(id = "action_id_71")
    protected WebElement rejectButton;
    @FindBy(id = "comment-issue")
    protected WebElement commentButton;

    //Pola
    @FindBy(xpath = "//*[@id='rowForcustomfield_10615']//*[@class='tinylink']/*")
    protected List<WebElement> product;


    @FindBy(xpath = "//*[@id='customfield_10401-val']")
    protected WebElement category;

    @FindBy(xpath = "//*[@id='customfield_10400-val']/div")
    protected List<WebElement> productsAffected;

    @FindBy(xpath = "(.//*[@class=\"sla-view-issue\"])[2]//*[@class='sla-view-info']/div[2]")
    protected WebElement sla;


    //stan Taska
    @FindBy(xpath = "//*[@id='status-val']/span")
    protected WebElement status;


    public AbstractTaskPage(WebDriver driver) {
        super(driver);
    }

    public PageObject goToTab(TaskTab tab) {
        PageObject obj = null;
        switch (tab) {

        }
        return obj;
    }

    public AbstractTaskPage clickInLink(TaskLink link) {
        AbstractTaskPage obj = null;
        switch (link) {

        }
        return obj;
    }

    public String getUserFromRole(TaskPeople people) {
        String roleName = null;

        return roleName;
    }

    public List<String> getProductClass() {
        List<String> productClassArray = new ArrayList<>();
        By productclass = new By.ByXPath("//*[@id='rlabs-details']/div/div[5]//*[@class='rlabs-value']");

        if (product.size() > 0) {
            new Actions(driver).clickAndHold(product.get(0)).perform();
            String text = driver.findElement(productclass).getText();
            productClassArray.add(text);
        } else {
            for (WebElement element : productsAffected) {
                new Actions(driver).clickAndHold(FindInTaskList.getProductClass(element)).perform();
                productClassArray.add(driver.findElement(productclass).getText());
            }
            System.out.println("Tablica " + productClassArray.toString());
        }
        return productClassArray;
    }

    public void clickOnButton(TaskButton button) {

        switch (button) {
            case COMMENT: {
                commentButton.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='comment']")));
                break;
            }
        }
    }

    public String getStatus() {
        if (status.getText() == "") {
            Assert.fail("Nie udało się pobrać Statusu");
        }
        return status.getText();
    }

    public DashboardPage goToDashboard() {
        return new DashboardPage(driver);
    }

    public String getTextFromField(TaskField field) {
        String textfromField = "";
        switch (field) {
            case Product: {
                textfromField = product.get(0).getText();
                break;
            }
            case Category: {
                textfromField = category.getText();
                break;
            }
            case SLA: {
                textfromField = sla.getText();
                break;
            }
        }
        return textfromField;
    }

}
