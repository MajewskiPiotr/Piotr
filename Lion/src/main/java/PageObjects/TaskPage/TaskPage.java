package PageObjects.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import core.ElementsOnPages.Task.TaskButton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class TaskPage extends AbstractTaskPage {


    public TaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void clickOnButtonAndDontWait(TaskButton button) {

        switch (button) {
            case COMPLETED_EDITOR: {
                Assert.assertTrue(completedEditorButton.isEnabled());
                completedEditorButton.click();
                break;
            }


        }

    }
}
