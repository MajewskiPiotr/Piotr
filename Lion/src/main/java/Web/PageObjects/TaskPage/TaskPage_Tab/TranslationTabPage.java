package Web.PageObjects.TaskPage.TaskPage_Tab;

import Tools.HelpersClass.Task;
import Web.PageObjects.Elements.Task.TaskStatus;
import Web.PageObjects.TaskPage.JobTaskPage;
import Web.PageObjects.TaskPage.TaskPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-17.
 * KLasa odzwierciedla zakladke Translation_Task
 */
public class TranslationTabPage extends JobTaskPage {

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


        for (int i = 0; i < listaTaskow.size() - 1; i++) {
            WebElement key = listaTaskow.get(i).findElement(By.xpath("//*[@id='translation-tasks-template-field']/form/table/tbody/tr[" + (i + 1) + "]/td[3]/a"));
            String status = listaTaskow.get(i).findElement(By.xpath("//*[@id='translation-tasks-template-field']/form/table/tbody/tr[" + (i + 1) + "]/td[4]/span")).getText();

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
