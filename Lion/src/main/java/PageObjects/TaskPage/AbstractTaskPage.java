package PageObjects.TaskPage;

import PageObjects.Base.PageObject;
import PageObjects.Elements.Task.TaskRole;
import PageObjects.Elements.Task.TaskTab;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 * KLasa abstracyjna. Kazda Page Task powinien dziedziczyc po tej klasie.
 */
public abstract class AbstractTaskPage extends PageObject {


    @FindBy(id = "customfield_10801-val")
    protected WebElement editor;

    @FindBy(xpath = "//*[@id='customfield_10338-val']//*[@class='user-hover']")
    protected WebElement languageOwner;

    @FindBy(id = "action_id_71")
    protected WebElement rejectButton;

    @FindBy(id = "assignee-val")
    protected WebElement assignee;


    @FindBy(xpath = "//*[@id='status-val']/span")
    protected WebElement status;

    @FindBy(id = "action_id_11")
    protected WebElement acceptButton;

    @FindBy(xpath = "//*[@class='wrap']//a[@class=\"issue-link\"]")
    protected WebElement translationTaskRef;

    @FindBy(id = "comment-issue")
    protected WebElement commentButton;

    @FindBy(id = "comment")
    protected WebElement commentField;

    @FindBy(xpath = "//*[@label='Project Roles']")
    protected WebElement commentAccessList;

    public AbstractTaskPage(WebDriver driver) {
        super(driver);
    }

    public PageObject goToTab(TaskTab tab) {
        PageObject obj = null;
        switch (tab) {
            case TRANSLATION_TASKS: {
                driver.findElement(By.xpath("//*[@class='tabs-menu']//*/strong[text()='Translation Tasks']")).click();
                obj = new TranslationTabPage(driver);
                break;
            }
            case ASSIGMENTS: {
                driver.findElement(By.xpath("//*[@class='tabs-menu']//*/strong[text()='Assignments']")).click();
                obj = new AssigmentsTabPage(driver);
                break;
            }
        }
        return obj;
    }

    public String getStatus() {
        return status.getText();
    }

    public String getUserFromRole(TaskRole role) {
        String roleName = null;
        switch (role) {
            case Language_Owner: {
                roleName = languageOwner.getAttribute("rel");
                break;
            }
        }
        return roleName;
    }

    public String getEditor() {
        return editor.getText();
    }

    public String getAssignee() {
        return assignee.getText();
    }

    public void typeCommentAndSetScope(String txt, String scope) {
        commentField.sendKeys(txt);
        driver.findElement(By.xpath("//*[@id='commentLevel-multi-select']/a/span[2]")).click();
        setScope(scope);
        //driver.findElement(By.xpath("//*[@id='issue-comment-add-submit']")).click();
    }

    public void setScope(String scope) {

    }
}
