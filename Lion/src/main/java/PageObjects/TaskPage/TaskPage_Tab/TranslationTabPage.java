package PageObjects.TaskPage.TaskPage_Tab;

import PageObjects.Base.AbstractJiraPage;
import PageObjects.TaskPage.TaskPage;
import core.Tools.FindInTaskTab;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

}
