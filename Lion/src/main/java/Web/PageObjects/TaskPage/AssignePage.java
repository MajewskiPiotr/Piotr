package Web.PageObjects.TaskPage;

import Web.PageObjects.Elements.Task.TaskButton;
import Web.PageObjects.Elements.Task.TaskStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 * Klasa odzwierciedla okno ASSIGN TO TRANSLATOR
 */
public class AssignePage extends AbstractTaskPage {

    @FindBy(id = "issue-workflow-transition-submit")
    protected WebElement assignToTranslatorButton;

    @FindBy(xpath = "//*[@id='assignee-single-select']/span")
    protected WebElement assigneeDropDown;

    public AssignePage(WebDriver driver) {
        super(driver);
    }


    //Wybieram pierwsza osobe z listy
    //Zwracam login wybranego usera
    public String chooseAssigne() {
        wait.until(ExpectedConditions.visibilityOf(assignToTranslatorButton));
        assigneeDropDown.click();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //lista userow do przypisania taska.Wybieram pierwszy z listy
        List<WebElement> lista = driver.findElements(By.xpath("//*[@id='all-users']/*"));
        String login = lista.get(0).getText();
        int separator = login.indexOf(" ");
        lista.get(0).click();
        //przypisuje Task do wybranego usera
        clickOnButton(TaskButton.ASSIGN_TO_TRANSLATOR);
        //zwracam Login usera na ktorego przypisalem Task
        return login.substring(0, separator);
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case ASSIGN_TO_TRANSLATOR: {
                assignToTranslatorButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.ASSIGNED_TO_TRANSLATOR));
            }
        }

    }

}
