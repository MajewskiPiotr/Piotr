package Web.PageObjects.TaskPage;

import Web.PageObjects.Base.PageObject;
import Web.PageObjects.Elements.Task.TaskLink;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


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
        PageFactory.initElements(driver, this);


    }

    @Override
    public PageObject clickInLink(TaskLink link) {
        switch (link) {
            case PACKAGE_REFERENCE: {
                packageReference.click();
                break;
            }
            case TRANSLATION_TASK_REF: {
                translationTaskRef.click();
                break;
            }

        }
        return new TaskPage(driver);

    }

    @Override


    public String getStatus() {
        return status.getText();
    }

    public int getTranslatorPool1Count() {
        return translatorPool1.size();
    }


}
