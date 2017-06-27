package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    }

    //Dropdown Listy
    @FindBy(id = "project-field")
    protected WebElement projectList;
    @FindBy()
    protected WebElement issueType;
    @FindBy(id = "create-issue-submit")
    protected WebElement createButton;

    @FindBy(xpath = "//*[@id='customfield_10702-suggestions']//*[@id='all-objects']/*")
    protected List<WebElement> softwareTeamList;

    @FindBy(xpath = "//*[@id='issuelinks-linktype']")
    protected WebElement couseList;


    public void setProject() {
        projectList.sendKeys("PL_GRO_BUS_ONETBIZ_AWARIE (GROONBIZAW)" + Keys.ENTER);
        try {
            Thread.sleep(2000);
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
}
