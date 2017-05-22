package PageObjects.TaskPage;

import Elements.Task.TaskButton;
import Elements.Task.TaskStatus;
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

    @FindBy(id = "action_id_51")
    protected WebElement inProgressButton;

    @FindBy(id = "action_id_321")
    protected WebElement selfQAButton;

    @FindBy(id = "action_id_61")
    protected WebElement comletedTranslator;

    public TaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));

    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case ASSIGN_TO_TRANSLATOR: {
                assignToTranslator.click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("workflow-transition-391-dialog"))));
                break;
            }
            case IN_PROGRESS: {
                inProgressButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.IN_PROGRESS));
                break;

            }
            case SELF_QA: {
                selfQAButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.SELF_QA));
                break;
            }
            case COMPLETED_TRANSLATOR:{
                comletedTranslator.click();
                wait.until(ExpectedConditions.invisibilityOfElementWithText(By.xpath("//*[@id='status-val']/span"), TaskStatus.SELF_QA));
                break;            }
        }

    }

    public String getStatus() {
        return status.getText();
    }

    public int getTranslatorPool1Count() {
        return translatorPool1.size();
    }


}
