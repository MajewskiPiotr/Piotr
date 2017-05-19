package PageObjects.TaskPage;

import Elements.Task.TaskButton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class TaskPage extends AbstractTaskPage {
    @FindBy(xpath = "//*[@id='customfield_11100-val']//span[@class='tinylink']")
    protected List<WebElement> translatorPool1;

    @FindBy(id = "action_id_21")
    protected WebElement ReguestNewDedline;

    @FindBy(id = "action_id_391")
    protected WebElement assignToTranslator;


    public TaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));

    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button){
            case ASSIGN_TO_TRANSLATOR:{
                assignToTranslator.click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("workflow-transition-391-dialog"))));
            }
        }

    }

    public String getStatus() {
        return status.getText();
    }

    public int getTranslatorPool1Count() {
        return translatorPool1.size();
    }


}
