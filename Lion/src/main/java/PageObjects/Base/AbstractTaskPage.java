package PageObjects.Base;

import PageObjects.ServiceDesk.MainPage.DashboardPage;
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


public abstract class AbstractTaskPage extends AbstractJiraPage {


    //Guziki
    @FindBy(id = "action_id_11")
    protected WebElement acceptButton;
    @FindBy(id = "action_id_71")
    protected WebElement rejectButton;
    @FindBy(id = "comment-issue")
    protected WebElement commentButton;
    @FindBy(id = "opsbar-operations_more")
    protected WebElement moreButton;
    @FindBy(id = "create-linked-issue")
    protected WebElement createLinkedIssueButton;

    //Pola
    @FindBy(xpath = "//*[@id='rowForcustomfield_10615']//*[@class='tinylink']/*")
    protected List<WebElement> product;
    @FindBy(xpath = "//*[@id='customfield_10401-val']")
    protected WebElement category;
    @FindBy(xpath = "//*[@id='customfield_10400-val']/div")
    protected List<WebElement> productsAffected;
    @FindBy(xpath = "//*[@class='sla-view-info']/div[text()='Time to resolution']/../div[2]")
    protected WebElement sla;
    @FindBy(xpath = "//*[@id='key-val']")
    protected WebElement taskNr;


    //komentarze i pokrewne
    @FindBy(xpath = "//*[@id='issue-comment-add']//*[@id='comment-wiki-edit']//*[@id='comment']")
    protected WebElement poleWprowadzaniaKomentarza;
    @FindBy(xpath = "//*[@class='action-body flooded']")
    protected List<WebElement> listaKomentarzy;

    @FindBy(xpath = "//*[@id='aui-flag-container']/div/div/a")
    protected WebElement allert;

    //Linked Issue
    @FindBy(xpath = "//*[@class='issue-link link-title']")
    protected List<WebElement> linkedIssue;

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

    public List<String> getKomentarze() {
        List<String> lista = new ArrayList<String>();
        for (WebElement element : listaKomentarzy) {
            String tempKom= element.getText();
            lista.add(tempKom);
        }
        System.out.println(lista.toString());
        return lista;
    }
    public String getTaskNumber(){
        return taskNr.getText();
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
            for (int i = 0; i < productsAffected.size(); i++) {
                new Actions(driver).clickAndHold(FindInTaskList.getProductClass(productsAffected.get(i))).build().perform();
                productClassArray.add(driver.findElement(By.xpath(("(//*[@id='rlabs-details']/div/div[5]//*[@class='rlabs-value'])[" + (i + 1) + "]"))).getText());

                productClassArray.add(driver.findElement(By.xpath(("(//*[@id='rlabs-details']/div/div[5]//*[@class='rlabs-value'])[" + (i + 1) + "]"))).getText());


            }
        }
        System.out.println("Tablica " + productClassArray.toString());

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
                String s = sla.getText();
                textfromField = s.substring(s.indexOf(" ")).replace(" ", "").replace("h", "");


                break;
            }
        }
        return textfromField;
    }

}
