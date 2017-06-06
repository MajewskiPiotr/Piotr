package PageObjects.TaskPage.TaskPage_Tab;

import PageObjects.Elements.Task.TaskButton;
import PageObjects.Elements.Task.TaskStatus;
import PageObjects.TaskPage.TaskPage;
import core.Tools.HelpersClass.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Piotr Majewski on 2017-05-17.
 * KLasa odzwierciedla zakladke Translation_Task
 */
public class TranslationTabPage extends TaskPage {

    @FindBy(xpath = "//*[@id='translation-tasks-template-field']/form/table/tbody/*")
    private List<WebElement> listaTaskow;


    public TranslationTabPage(WebDriver driver) {
        super(driver);
    }

    public TaskPage goToFirstTask() {
        getTaskfromList(0).getKey().click();
        return new TaskPage(driver);
    }

    public List<Task> getlistaTaskow() {
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i <= listaTaskow.size() - 1; i++) {
            WebElement key = listaTaskow.get(i).findElement(By.partialLinkText("GO"));
            String status = listaTaskow.get(i).findElement(By.className("jira-issue-status-lozenge")).getText();
            taskList.add(new Task(key, status));
        }
        return taskList;
    }

    public int countTranslatorTask() {
        return listaTaskow.size();
    }

    private Task getTaskfromList(int witchTask) {
        List<Task> list = getlistaTaskow();
        return list.get(witchTask);
    }

    public boolean otworzWszystkieTranslationTask() {
        List<Task> taskList = getlistaTaskow();
        for (Task task : taskList) {
            WebElement taskLink = task.getKey();
            new Actions(driver).keyDown(Keys.LEFT_CONTROL).click(taskLink).keyUp(Keys.LEFT_CONTROL).build().perform();
        }

        Set<String> windowHandles = driver.getWindowHandles();
        Object[] objects = windowHandles.toArray();
        for (int i = 1; i <= windowHandles.size() - 1; i++) {
            driver.switchTo().window((String) objects[i]);
            clickOnButtonAndDontWait(TaskButton.COMPLETED_EDITOR);
            // driver.findElement(By.id("id=\"comment-wiki-edit")).sendKeys("testowt");
        }
        wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElement(status, TaskStatus.COMPLETED), ExpectedConditions.textToBePresentInElement(status, TaskStatus.QA)));
        return true;
    }

    public boolean checkIsStatusChange() {
        int accepted = 0;
        List<Task> list = getlistaTaskow();
        for (Task task : list) {
            if (task.getStatus().equals(TaskStatus.ASSIGNED_TO_TRANSLATOR) || task.getStatus().equals(TaskStatus.ASSIGNED_TO_AG)) {
                accepted++;
            }
        }
        return accepted == 1;
    }
}
