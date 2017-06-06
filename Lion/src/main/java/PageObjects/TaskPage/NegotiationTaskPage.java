package PageObjects.TaskPage;

import PageObjects.Base.PageObject;
import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskLink;
import PageObjects.Elements.Task.TaskStatus;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class NegotiationTaskPage extends AbstractTaskPage {

    @FindBy(id = "action_id_71")
    private WebElement rejectButton;


    public NegotiationTaskPage(WebDriver driver) {

        super(driver);
        System.out.println("Task jest w Statusie: " + getStatus());
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
        try {
            switch (button) {
                case ACCEPT: {
                    System.out.println("sprawdzam jaki jest status " + getStatus());
                    if (getStatus().equals(TaskStatus.NEW)) {
                        acceptButton.click();
                        wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ACCEPTED));
                    } else {
                        System.out.println("task został automatycznie zaakceptowany");
                    }
                    break;
                }
                case TRANSLATION_TASK_REF: {
                    translationTaskRef.click();
                    break;
                }
                case REJECT: {
                    rejectButton.click();
                    wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.REJECTED));
                    break;
                }
            }
        } catch (TimeoutException time) {
            Assert.fail("TimeOut: oczekiwanie na zmiane statusu");
        }


    }


}
