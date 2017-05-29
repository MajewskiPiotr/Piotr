package Web.PageObjects.TaskPage;

import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskStatus;
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
                break;
            }
        }

    }


    public int plpCount() {
        return Integer.parseInt(plpCount.getText());
    }

    public void waitForJobProcessing() {
        System.out.println("robimy refresh 1 ");
        for (int i = 0; i < 10; i++) {
            if (getStatus() == TaskStatus.NEW) {
                System.out.println("robimy refresh");
                driver.navigate().refresh();
                break;
            }
        }

    }


}




