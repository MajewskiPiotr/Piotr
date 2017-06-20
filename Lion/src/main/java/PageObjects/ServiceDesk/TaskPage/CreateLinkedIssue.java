package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.PageObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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


    public void setProject() {
    projectList.sendKeys("PL_GRO_BUS_ONETBIZ_AWARIE (GROONBIZAW)"+ Keys.ENTER);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setIssueType() {
    }

    public void setDescription() {
    }

    public void create() {
       wait.until(ExpectedConditions.elementToBeClickable(createButton));
        createButton.click();
    }
}
