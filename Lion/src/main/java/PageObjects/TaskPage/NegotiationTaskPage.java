package PageObjects.TaskPage;

import Elements.Task.TaskButton;
import Elements.Task.TaskStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class NegotiationTaskPage extends AbstractTaskViewPage {

    @FindBy(id = "action_id_11")
    private WebElement accept;

    @FindBy(id = "action_id_71")
    private WebElement Reject;


    public NegotiationTaskPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button){
            case ACCEPT:{
                accept.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ACCEPTED));
            }
        }

    }


}
