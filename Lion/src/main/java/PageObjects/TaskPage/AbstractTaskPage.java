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

/**
 * Created by Piotr Majewski on 2017-05-19.
 * KLasa abstracyjna. Kazda Page Task powinien dziedziczyc po tej klasie.
 */
public abstract class AbstractTaskPage extends PageObject {
    @FindBy(xpath = "//*[@id='status-val']/span")
    protected WebElement status;

   @FindBy(id = "action_id_11")
    protected WebElement acceptButton;

   @FindBy(xpath = "//*[@class='wrap']//a[@class=\"issue-link\"]")
   protected WebElement translationTaskRef;

    public AbstractTaskPage(WebDriver driver) {
        super(driver);
    }

    public PageObject goToTab(TaskTab tab) {
        PageObject obj = null;
        switch (tab) {
            case TRANSLATION_TASKS: {
                driver.findElement(By.xpath("//*[@class='tabs-menu']/*[2]")).click();
                obj = new TranslationTabPage(driver);
                break;
            }
            case ASSIGMENTS: {
                driver.findElement(By.xpath("//*[@class='tabs-menu']/*[3]")).click();
                obj = new AssigmentsTabPage(driver);
                break;
            }
        }
        return obj;
    }

    public abstract void clickOnButton(TaskButton button);

    public String getStatus(){return status.getText();}
}
