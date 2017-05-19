package PageObjects.TaskPage;

import Elements.Task.TaskButton;
import Elements.Task.TaskTab;
import PageObjects.Base.PageObject;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;
import PageObjects.TaskPage.TaskPage_Tab.TranslationTabPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class TaskPage extends AbstractTaskViewPage  {
    @FindBy(xpath = "//*[@id='customfield_11100-val']//span[@class='tinylink']")
    private List<WebElement> translatorPool1;

    @FindBy(id = "action_id_21")
    private WebElement ReguestNewDedline;



    public TaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));

    }

    @Override
    public void clickOnButton(TaskButton button) {

    }

    public String getStatus() {
        return status.getText();
    }

    public int getTranslatorPool1Count() {
        return translatorPool1.size();
    }



}
