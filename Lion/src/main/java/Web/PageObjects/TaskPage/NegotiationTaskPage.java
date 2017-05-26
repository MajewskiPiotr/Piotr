package Web.PageObjects.TaskPage;

import Web.PageObjects.Base.PageObject;
import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskLink;
import Web.PageObjects.Elements.Task.TaskStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class NegotiationTaskPage extends AbstractTaskPage {

    @FindBy(id = "action_id_71")
    private WebElement Reject;


    public NegotiationTaskPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public PageObject clickInLink(TaskLink link) {
        PageObject obj = null;
        switch (link) {
            case TRANSLATION_TASK_REF: {
                translationTaskRef.click();
                obj = new TaskPage(driver);
                break;
            }
        }
        return obj;
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case ACCEPT: {
                acceptButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ACCEPTED));
                break;
            }
            case TRANSLATION_TASK_REF: {
                translationTaskRef.click();
                break;
            }
        }

    }


}
