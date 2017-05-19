package PageObjects.main;

import PageObjects.Base.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Piotr Majewski on 2017-05-16.
 * KLasa reprezntuje Dashboard w aplikacji JIRA
 * widok pojawia sie po zalogowaniu sie do app.
 */
public class DashboardPage extends PageObject {


    @FindBy(className = "aui-page-header-main")
    private WebElement systemDashboard;


    public DashboardPage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/secure/Dashboard.jspa");
        wait.until(ExpectedConditions.visibilityOf(systemDashboard));

    }

    public KanbanPage GoToKanban() throws InterruptedException {
        return new KanbanPage(driver);
    }




}
