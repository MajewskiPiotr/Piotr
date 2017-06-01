package PageObjects.TaskPage;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
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
        for (int i = 0; i < 10; i++) {
            if (!getStatus().equals(TaskStatus.WAITING_FOR_PACKAGING)) {
                System.out.println("robimy refresh");
                System.out.println(getStatus());
                driver.navigate().refresh();
            }else {break;}
        }

    }


}




