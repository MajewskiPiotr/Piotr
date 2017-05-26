package Web.PageObjects.TaskPage;

import Web.PageObjects.Base.PageObject;
import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskStatus;
import Web.PageObjects.Elements.Task.TaskTab;
import Web.PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import Web.PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 * KLasa abstracyjna. Kazda Page Task powinien dziedziczyc po tej klasie.
 */
public abstract class AbstractTaskPage extends PageObject {
    @FindBy(id = "comment-issue")
    protected WebElement commentButton;


    @FindBy(id = "action_id_61")
    protected WebElement processingButton;


    @FindBy(id = "customfield_11401-val")
    protected WebElement plpCount;

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

    @FindBy(id = "assignee-val")
    protected WebElement assignee;

    @FindBy(id = "customfield_10801-val")
    protected WebElement editor;

    @FindBy(xpath = "//*[@id='status-val']/span")
    protected WebElement status;

    @FindBy(id = "action_id_11")
    protected WebElement acceptButton;

    @FindBy(xpath = "//*[@class='wrap']//a[@class=\"issue-link\"]")
    protected WebElement translationTaskRef;


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

    public void clickOnButton(TaskButton button) {

        switch (button) {
            case TRANSLATION_TASK_REF: {
                translationTaskRef.click();
                break;
            }
            case ACCEPT: {
                acceptButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ACCEPTED));
                break;
            }
            case COMPLETED_EDITOR: {
                completedEditorButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.COMPLETED));
                break;
            }
            case ASSIGN_TO_EDITOR: {
                assignToEditorButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ASSIGNED_TO_EDITOR));
                break;
            }
            case ASSIGN_TO_TRANSLATOR: {
                assignToTranslatorButton.click();
                if(this.getClass().isInstance(TaskPage.class)) {
                    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("workflow-transition-391-dialog"))));
                }

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
            case PROCESSING: {
                processingButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.IN_PROCESSING));
                break;
            }
        }

    }

    public String getStatus() {
        return status.getText();
    }

    public String getEditor() {
        return editor.getText();
    }

    public String getAssignee() {
        return assignee.getText();
    }
}
