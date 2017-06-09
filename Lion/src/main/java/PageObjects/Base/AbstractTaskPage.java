package PageObjects.Base;

import core.ElementsOnPages.Task.*;
import PageObjects.TaskPage.TaskPage;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-05-19.
 * KLasa abstracyjna. Kazda Page Task powinien dziedziczyc po tej klasie.
 */

//TODO w środosiku produkcyjnych można potworzyć podKlasy i umiescić w nich elementy z których nie wszyscy korzystają (np. AssignToTranslator uzywa tylko Edytor
public abstract class AbstractTaskPage extends AbstractJiraPage {

    //Zakladki
    @FindBy(xpath = "//*[@class='tabs-menu']//*/strong[text()='Translation Tasks']")
    protected WebElement translationTasksTab;
    @FindBy(xpath = "//*[@class='tabs-menu']//*/strong[text()='Assignments']")
    protected WebElement assignmentsTab;


    //Guziki
    @FindBy(id = "action_id_11")
    protected WebElement acceptButton;
    @FindBy(id = "action_id_81")
    protected WebElement completedEditorButton;
    @FindBy(id = "action_id_331")
    protected WebElement assignToEditorButton;
    @FindBy(id = "action_id_391")
    protected WebElement assignToTranslatorButton;
    @FindBy(id = "action_id_51")
    protected WebElement inProgressButton;
    @FindBy(id = "action_id_321")
    protected WebElement selfQAButton;
    @FindBy(id = "action_id_61")
    protected WebElement comletedTranslatorButton;
    @FindBy(id = "action_id_71")
    protected WebElement rejectButton;
    @FindBy(id = "comment-issue")
    protected WebElement commentButton;

    //Pola

    @FindBy(id = "customfield_10801-val")
    protected WebElement editor;
    @FindBy(xpath = "//*[@id='customfield_10338-val']//*[@class='user-hover']")
    protected WebElement languageOwner;
    @FindBy(id = "assignee-val")
    protected WebElement assignee;

    //stan Taska
    @FindBy(xpath = "//*[@id='status-val']/span")
    protected WebElement status;


    //Inne
    @FindBy(id = "customfield_10334-val")
    protected WebElement packageReference;
    @FindBy(xpath = "//*[@id='issue_actions_container']")
    protected WebElement allComents;
    @FindBy(xpath = "//*[@class='wrap']//a[@class=\"issue-link\"]")
    protected WebElement translationTaskRef;
    @FindBy(id = "comment")
    protected WebElement commentField;


    public AbstractTaskPage(WebDriver driver) {
        super(driver);
    }

    public PageObject goToTab(TaskTab tab) {
        PageObject obj = null;
        switch (tab) {
            case ASSIGMENTS: {
                assignmentsTab.click();
                obj = new AssigmentsTabPage(driver);
                break;
            }
            case TRANSLATION_TASKS: {
                translationTasksTab.click();
                obj = new TranslationTabPage(driver);
                break;
            }
        }
        return obj;
    }

    public AbstractTaskPage clickInLink(TaskLink link) {
        AbstractTaskPage obj = null;
        switch (link) {
            case PACKAGE_REFERENCE: {
                packageReference.click();
                obj = new TaskPage(driver);
            }
        }
        return obj;
    }

    public String getUserFromRole(TaskPeople people) {
        String roleName = null;
        switch (people) {
            case Language_Owner: {
                roleName = languageOwner.getAttribute("rel");
                break;
            }
        }
        return roleName;
    }

    public void clickOnButton(TaskButton button) {

        switch (button) {
            case COMPLETED_EDITOR: {
                completedEditorButton.click();
                wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElement(status, TaskStatus.COMPLETED.getStatus()), ExpectedConditions.textToBePresentInElement(status, TaskStatus.QA.getStatus())));
                break;
            }
            case ASSIGN_TO_EDITOR: {
                assignToEditorButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ASSIGNED_TO_EDITOR.getStatus()));
                break;
            }
            case ASSIGN_TO_TRANSLATOR: {

                assignToTranslatorButton.click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("workflow-transition-391-dialog"))));
                break;
            }
            case IN_PROGRESS: {
                inProgressButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.IN_PROGRESS.getStatus()));
                break;

            }
            case SELF_QA: {
                selfQAButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.SELF_QA.getStatus()));
                break;
            }
            case COMPLETED_TRANSLATOR: {
                comletedTranslatorButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[@id='status-val']/span"), TaskStatus.SELF_QA.getStatus()));
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
        if (status.getText()==""){
            Assert.fail("Nie udało się pobrać Statusu");}
        return status.getText();
    }


}
