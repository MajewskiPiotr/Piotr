package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by Piotr on 2017-06-16.
 */

//Test weryfikuje czy komentarze poprawnie zostają przeniesione z Taska utworzonego w ramach zgłoszenia.
public class PropagacjaKomentarzy extends BaseTestClass {
    //Dane testowe
    private String issueURL;
    private String relatedIssue;
    private String komentarz = "Komenarz DO Customera " + new Date().getTime();
    private String nrTaska;

    @Test(priority = 10)
    public void propagacjaPomiedzyZaleznymiTaskami() {
        //Z poziomu Taska założyc nowe zgłoszenie do 3 linii
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToFirstTaskOnList();
        issueURL = taskPage.getUrl();
        relatedIssue = taskPage.createLinkedIssue();
        nrTaska = taskPage.getTaskNumber();

        //Na nowo utworzonym Tasku wprowadzamy komentarz
        TaskPage relatedIssueTask = taskPage.goToRelatedIssue(relatedIssue);
        relatedIssueTask.clickOnButton(TaskButton.COMMENT);
        relatedIssueTask.wprowadzKomentarzWidocznyDlaCustomera(komentarz);

        //Wracamy do Taska głównego i weryfikujemy czy komenarz został rozpropagowany
        relatedIssueTask.goToUrl(issueURL);
        TaskPage mainPage = new TaskPage(driver);
        Assert.assertTrue(mainPage.verifyCommentExist(komentarz), "nie udała sie propagacja komentarzy");
    }

    @Test(priority = 20)
    public void weryfikacjaPropagacjiKomentarzyDoCustomera() {
        //Uruchamiamy aplikacje Customer i weryfikujemy czy komentarz został tam rozpropagowany
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        CustomerTaskPage customerTaskPage = customerServiceLoginPage.goToTask(nrTaska);
        Assert.assertTrue(customerTaskPage.verifyCommentExist(komentarz), "Komenarz nie został rozpropagowany do Customera");
    }
}
