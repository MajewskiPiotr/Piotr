package Web.PageObjects.TaskPage;

import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class PmAgencyTaskPage extends AbstractTaskPage {


    public PmAgencyTaskPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case ACCEPT: {
                acceptButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ACCEPTED));
            }
            case TRANSLATION_TASK_REF: {
                translationTaskRef.click();

            }
        }
    }
}
