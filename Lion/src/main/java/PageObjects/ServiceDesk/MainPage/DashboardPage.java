package PageObjects.ServiceDesk.MainPage;

import PageObjects.Base.AbstractJiraPage;
import PageObjects.ServiceDesk.zold.PackagePluginSettings;
import PageObjects.ServiceDesk.zold.ProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Piotr Majewski on 2017-05-16.
 * KLasa reprezntuje Dashboard w aplikacji JIRA
 * widok pojawia sie po zalogowaniu sie do app.
 */
public class DashboardPage extends AbstractJiraPage {

    //Napis SystemDashboard
    @FindBy(className = "aui-page-header-main")
    private WebElement systemDashboard;


    public DashboardPage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/secure/Dashboard.jspa");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("aui-page-header-main")));
    }

    public PackagePluginSettings goToPackegePluginSettings() {
        goToUrl("/secure/admin/PackagePluginSettings.jspa");
        return new PackagePluginSettings(driver);

    }
    public QueQuePage goToQueQue(){
        return new QueQuePage(driver);
    }

    public ProfilePage goToProfilePage() {
        return new ProfilePage(driver);
    }
    public Insightpage goToInsightPage(){

        return new Insightpage(driver);
    }
    public Insightpage goToInsightPage(String insightObjectScheme){
        return new Insightpage(driver, insightObjectScheme);
    }
}
