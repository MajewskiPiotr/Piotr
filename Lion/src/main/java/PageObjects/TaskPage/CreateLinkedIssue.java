package PageObjects.TaskPage;

import PageObjects.Base.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    }


    public void setIssueType() {
    }

    public void setDescription() {
    }

    public void create() {

        createButton.click();
    }
}
