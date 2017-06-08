package PageObjects.TaskPage;

import PageObjects.Base.PageObject;
import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskLink;
import PageObjects.Elements.Task.TaskStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class TaskPage extends AbstractTaskPage {

    @FindBy(id = "action_id_81")
    protected WebElement completedEditorButton;

    @FindBy(id = "action_id_331")
    protected WebElement assignToEditorButton;

    @FindBy(xpath = "//*[@id='customfield_11100-val']//span[@class='tinylink']")
    protected List<WebElement> translatorPool1;

    @FindBy(id = "action_id_21")
    protected WebElement ReguestNewDedline;

    @FindBy(id = "action_id_391")
    protected WebElement assignToTranslatorButton;

    @FindBy(id = "action_id_51")
    protected WebElement inProgressButton;

    @FindBy(id = "action_id_321")
    protected WebElement selfQAButton;

    @FindBy(id = "action_id_61")
    protected WebElement comletedTranslatorButton;

    @FindBy(id = "customfield_10334-val")
    protected WebElement packageReference;

    @FindBy(xpath = "//*[@id='issue_actions_container']")
    protected WebElement allComents;

    public TaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PageFactory.initElements(driver, this);


    }

    @Override
    public PageObject clickInLink(TaskLink link) {
        PageObject obj = null;
        switch (link) {
            case PACKAGE_REFERENCE: {
                packageReference.click();
                obj = new TaskPage(driver);
            }
        }
        return obj;
    }

    public void clickOnButtonAndDontWait(TaskButton button) {

        switch (button) {
            case COMPLETED_EDITOR: {
                Assert.assertTrue(completedEditorButton.isEnabled());
                completedEditorButton.click();
                break;
            }


        }

    }

    @Override
    public void clickOnButton(TaskButton button) {

        switch (button) {
            case COMPLETED_EDITOR: {
                completedEditorButton.click();
                wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElement(status, TaskStatus.COMPLETED), ExpectedConditions.textToBePresentInElement(status, TaskStatus.QA)));

                break;
            }
            case ASSIGN_TO_EDITOR: {
                assignToEditorButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ASSIGNED_TO_EDITOR));
                break;
            }
            case ASSIGN_TO_TRANSLATOR: {

                assignToTranslatorButton.click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("workflow-transition-391-dialog"))));
                break;
            }
            case IN_PROGRESS: {
                inProgressButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.IN_PROGRESS));
                break;

            }
            case SELF_QA: {
                selfQAButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.SELF_QA));
                break;
            }
            case COMPLETED_TRANSLATOR: {
                comletedTranslatorButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[@id='status-val']/span"), TaskStatus.SELF_QA));
                break;
            }
            case COMMENT: {
                commentButton.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='comment']")));
                break;
            }
        }

    }

    public String getStatus() {
        return status.getText();
    }

    public int getTranslatorPool1Count() {
        return translatorPool1.size();
    }

    public String getAllComents() {
        return allComents.getText();
    }


}
