package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.AbstractJiraPage;
import core.Tools.JiraWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class EditIssuePage extends AbstractJiraPage {
    @FindBy(xpath = "//*[@id='edit-issue-submit']")
    protected WebElement updateButton;
    @FindBy(xpath = "//*[@id='create-issue-submit']")
    protected WebElement createButton;
    @FindBy(id = "customfield_10615-field")
    protected WebElement productDropdown;
    @FindBy(id = "customfield_10401-field")
    protected WebElement categoryDropdown;
    @FindBy(id = "customfield_10614-field")
    protected WebElement productClassDropdown;
    @FindBy(id = "project-field")
    protected WebElement projectDropdown;
    @FindBy(id = "issuetype-field")
    protected WebElement issueTypeDropdown;
    @FindBy(id = "summary")
    protected WebElement summaryField;
    @FindBy(xpath = "//*[@class='rte-container']/div")
    protected WebElement descriptionField;
    @FindBy(id = "issuelinks-issues-textarea")
    protected WebElement issueField;


    public EditIssuePage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='jira-dialog-content']")));
    }

    //tworzymy MasterIncident z podanych parametrów
    public String createMasterIncident(String summary, String description, List<String> issue) {
        setProject("DreamLabSD (DLSD)");
        setIssueTypeDropdown("Master Incident");
        setSummary(summary);
        setDescription(description);
        setIssue(issue);
        setProduct();
        setCategory();
        setProductClass();
        return create();
    }



    //metoda uzupełnia pola randomowymi wartościami
    public void issueClasification() {
        setProduct();
        setCategory();
        setProductClass();
        update();
    }

    private void setIssue(List<String> issue) {
        for (String s : issue) {
            new Actions(driver).click(issueField).sendKeys(s + Keys.TAB).perform();
        }
    }

    private void setDescription(String description) {
        JiraWait.waitForProcesing(2000);
        new Actions(driver).moveToElement(descriptionField).click(descriptionField).sendKeys(description).perform();
    }

    private void setSummary(String s) {
        JiraWait.waitForProcesing(2000);
        new Actions(driver).moveToElement(summaryField).click(summaryField).sendKeys(s).perform();
    }

    private void setIssueTypeDropdown(String s) {
        JiraWait.waitForProcesing(2000);
        issueTypeDropdown.clear();
        issueTypeDropdown.sendKeys(s + Keys.ENTER);

    }

    private void setProject(String s) {
        projectDropdown.clear();
        projectDropdown.sendKeys(s + Keys.ENTER);
    }

    private void setProduct() {
        productDropdown.click();
        List<WebElement> listOdProduct = driver.findElements(By.xpath("//*[@id='showing-first-25-objects']/*"));
        //TODO zamienić wybieranie na Random
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

    private String create() {
        createButton.click();
        return getNewCreatedIssueNumber();
    }

}
