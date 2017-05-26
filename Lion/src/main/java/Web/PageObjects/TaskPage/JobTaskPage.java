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


    public JobTaskPage(WebDriver driver) {
        super(driver);
    }


    public int plpCount() {
        return Integer.parseInt(plpCount.getText());

    }


}




