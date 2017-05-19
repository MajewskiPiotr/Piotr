package PageObjects.TaskPage;

import Elements.Task.TaskButton;
import Elements.Task.TaskStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class JobTaskPage extends AbstractTaskPage {
    @FindBy(id = "comment-issue")
    private WebElement commentButton;


    @FindBy(id = "action_id_61")
    private WebElement processingButton;



    @FindBy(id = "customfield_11401-val")
    private WebElement plpCount;

    public JobTaskPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case PROCESSING: {
                processingButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.IN_PROCESSING));
            }
        }

    }




    public int plpCount() {
        return Integer.parseInt(plpCount.getText());
    }


}




