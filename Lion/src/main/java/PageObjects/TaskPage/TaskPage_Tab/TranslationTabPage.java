package PageObjects.TaskPage.TaskPage_Tab;

import PageObjects.Base.AbstractJiraPage;
import PageObjects.ElementsOnPages.Task.TaskStatus;
import PageObjects.TaskPage.TaskPage;
import core.Tools.FindInTaskTab;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-17.
 * KLasa odzwierciedla zakladke Translation_Task
 */
public class TranslationTabPage extends AbstractJiraPage {
    @FindBy(xpath = "//*[@id='translation-tasks-template-field']/form/table/tbody/*")
    private List<WebElement> listaTaskow;

    public TranslationTabPage(WebDriver driver) {
        super(driver);
    }

    public TaskPage goToFirstTranslationTask() {
        FindInTaskTab.getTaskLink(listaTaskow.get(0)).click();
        return new TaskPage(driver);
    }

    //zwraca Liste WebElementów z listy Translation Task
    //@param TaskStatus
    public List<WebElement> getTranslationTask(TaskStatus status) {
        List<WebElement> newWebElementList = new ArrayList<>();
        for (int i = 0; i < listaTaskow.size(); i++) {
            WebElement webElementUnderTest = listaTaskow.get(i);
            if (FindInTaskTab.getTaskTypeFromTranlationTaskTab(webElementUnderTest, i + 1).getText().equals(status)) {
                newWebElementList.add(webElementUnderTest);
            }
        }
        return newWebElementList;
    }

    public List<WebElement> getTranslationTaskList() {
        if (listaTaskow == null) {
            Assert.fail("Lista Translation Task-ów jest pusta");
        }
        return listaTaskow;
    }
}
