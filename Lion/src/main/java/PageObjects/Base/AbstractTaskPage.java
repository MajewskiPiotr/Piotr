package PageObjects.Base;

import PageObjects.ServiceDesk.MainPage.DashboardPage;
import core.ElementsOnPages.Task.*;
import core.Tools.FindInTaskList;
import core.Tools.JiraWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 * KLasa abstracyjna. Kazda Page Task powinien dziedziczyc po tej klasie.
 */


public abstract class AbstractTaskPage extends AbstractJiraPage {


    //Guziki
    @FindBy(xpath = "//*[@id='action_id_2']")
    protected WebElement closeIssueButton;
    @FindBy(id = "comment-issue")
    protected WebElement commentButton;
    @FindBy(id = "opsbar-operations_more")
    protected WebElement moreButton;
    @FindBy(id = "create-linked-issue")
    protected WebElement createLinkedIssueButton;
    @FindBy(id = "edit-issue")
    protected WebElement editButton;
    @FindBy(id = "action_id_851")
    protected WebElement respondToCustomerButton;

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


    //stan Taska
    @FindBy(xpath = "//*[@id='status-val']/span")
    protected WebElement status;

    //Lista zaleznych Issue
    @FindBy(xpath = "//*[@class='links-container']/*/dd//*[@class='link-snapshot']/*")
    protected List<WebElement> relatedIssueList;


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
            String tempKom = element.getText();
            lista.add(tempKom);
        }
        System.out.println(lista.toString());
        return lista;
    }

    public String getTaskNumber() {
        return taskNr.getText();
    }

    public String getUserFromRole(TaskPeople people) {
        String roleName = null;

        return roleName;
    }

    public List<String> getProductClass() {
        JiraWait.waitForProcesing(1000);
        List<String> productClassArray = new ArrayList<>();

        By productclass = new By.ByXPath("//*[@id='rlabs-details']/div/div[5]//*[@class='rlabs-value']");
        if (product.size() > 0) {
            new Actions(driver).moveToElement(product.get(0)).perform();
            String text = driver.findElement(productclass).getText();
            productClassArray.add(text);
        }
        if (productsAffected.size() > 0) {
            driver.navigate().refresh();
            JiraWait.waitForProcesing(2000);
            for (int i = 0; i < productsAffected.size(); i++) {
                new Actions(driver).moveToElement(productsAffected.get(i)).build().perform();
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

    public boolean checkIsRelatedIssueIsClosed() {
        boolean close = false;
        for (int i = 1; i <= relatedIssueList.size(); i++) {
            if (FindInTaskList.getStatusFromRelatedIssue(relatedIssueList.get(i - 1), i).getText().equals("RESOLVED")) {
                close = true;
            } else {
                close = false;
                break;
            }

        }
        return close;
    }

    public void closeIssue() {
        closeIssueButton.click();
        //wait until system show new screen with close parameters
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='issue-workflow-transition']")));
        Select select = new Select(driver.findElement(By.id("resolution")));
        select.selectByVisibleText("Done");
        driver.findElement(By.xpath("//*[@id='issue-workflow-transition-submit']")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.CLOSED.getStatus()));
        driver.navigate().refresh();
    }
}
