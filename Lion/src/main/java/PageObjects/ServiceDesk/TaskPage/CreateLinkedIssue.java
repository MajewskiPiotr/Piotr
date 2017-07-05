package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.PageObject;
import core.Tools.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

/**
 * Created by Piotr on 2017-06-16.
 */
public class CreateLinkedIssue extends PageObject {

    public CreateLinkedIssue(WebDriver driver) {
        super(driver);
        Tools.waitForProcesing(2000);
    }

    //Dropdown Listy
    @FindBy(xpath = "//*[@id='write_id_of_your_picker_field_here-field']")
    protected WebElement projectList;

    @FindBy(xpath = "//*[@id='issuetype-field']")
    protected WebElement issueType;

    @FindBy(id = "create-issue-submit")
    protected WebElement createButton;

    @FindBy(xpath = "//*[@id='customfield_10702-suggestions']//*[@id='all-objects']/*")
    protected List<WebElement> softwareTeamList;

    @FindBy(xpath = "//*[@id='issuelinks-linktype']")
    protected WebElement couseList;

    @FindBy(xpath = "//*[@id='issuetype-field']")
    protected WebElement issueTypeList;


    public void setProject() {
        driver.findElement(By.xpath("//*[@id='write_id_of_your_picker_field_here-single-select']/span")).click();
        Tools.waitForProcesing(2000);
        List<WebElement> listaIssueType = driver.findElements(By.xpath("//*[@id='projects']//li/a"));
        System.out.println(listaIssueType.get(5).getText());
        new Actions(driver).moveToElement(listaIssueType.get(5)).click(listaIssueType.get(5)).perform();
    }

    public void xxx() {
        projectList.sendKeys("PL_GRO_BUS_ONETBIZ_AWARIE" + Keys.ENTER);
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='customfield_10702-field']"))));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setSoftwareTeam() {
        driver.findElement(By.xpath("//*[@id='customfield_10702-field']")).click();
        Random random = new Random();
        softwareTeamList.get(random.nextInt(softwareTeamList.size() - 1)).click();
    }

    public void create() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton));
        createButton.click();
    }

    public void setCousedBy() {
        Select select = new Select(couseList);
        select.selectByValue("is caused by");

    }

    public void setIssueType() {
       // issueType.click();
      //  issueType.sendKeys("Task" + Keys.ENTER);
        Tools.waitForProcesing(2000);

    }
}
