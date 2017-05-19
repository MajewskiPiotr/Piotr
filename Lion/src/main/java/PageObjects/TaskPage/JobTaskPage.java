package PageObjects.TaskPage;

import Elements.Task.TaskButton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Piotr Majewski on 2017-05-16.
 */
public class JobTaskPage extends AbstractTaskViewPage {
    @FindBy(id = "comment-issue")
    private WebElement commentButton;

    @FindBy(xpath = "//*[@id='status-val']/span")
    private WebElement status;

    @FindBy(id = "action_id_61")
    private WebElement processingButton;

    @FindBy(id = "action_id_31")
    private WebElement instructionButton;


    @FindBy(className = "jira-dialog-content")
    private WebElement alert;

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
            }
        }
        waitForProcessing();

    }


    public void waitForProcessing() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int plpCount() {
        return Integer.parseInt(plpCount.getText());
    }


}




