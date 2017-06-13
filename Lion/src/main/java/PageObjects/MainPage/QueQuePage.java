package PageObjects.MainPage;

import PageObjects.Base.AbstractJiraPage;
import PageObjects.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import core.Tools.FindInTaskTab;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-16.
 * KLasa reprezntuje Dashboard w aplikacji JIRA
 * widok pojawia sie po zalogowaniu sie do app.
 */
public class QueQuePage extends AbstractJiraPage {
    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[text()='Helpdesk Dispatcher']")
    private WebElement helpDesk_DISPATCHER;
    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[@title[contains(.,'TeamA')]]")
    private WebElement helpDesk_TEAM_A;
    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[@title[contains(.,'TeamB')]]")
    private WebElement helpDesk_TEAM_B;
    @FindBy(xpath = "//*[@id='pinnednav-opts-sd-queues-nav']//*[@title[contains(.,'TeamC')]]")
    private WebElement helpDesk_TEAM_C;
    @FindBy(xpath = "//*[@id='issuetable']/tbody/*")
    private List<WebElement> taskList;

    //Napis SystemDashboard
    @FindBy(className = "aui-page-header-main")
    private WebElement systemDashboard;


    public QueQuePage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/projects/DLSD/queues/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("aui-page-header-main")));
        System.out.println(ExpectedConditions.presenceOfElementLocated(By.className("aui-page-header-main")).toString());
    }

    public TaskPage goToFirstTaskOnList() {
        FindInTaskTab.getTaskLink(getTaskList().get(0)).click();
        System.out.println("wybrałem task");
        return new TaskPage(driver);
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case HELPDESK_DISPATCHER: {
                helpDesk_DISPATCHER.click();
                break;
            }
            case HELPDESK_TEAM_A: {
                helpDesk_TEAM_A.click();
                break;
            }
            case HELPDESK_TEAM_B: {
                helpDesk_TEAM_B.click();
                break;
            }
            case HELPDESK_TEAM_C: {
                helpDesk_TEAM_C.click();
                break;
            }
        }
        waitt();

    }

    private void waitt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<WebElement> getTaskList() {
        return taskList;
    }
}