package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.CustomerService.CustomerTaskPage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.BaseTestClass;
import core.ElementsOnPages.Task.TaskButton;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by Piotr on 2017-06-16.
 */

//Test weryfikuje czy komentarze poprawnie zostają przeniesione z Taska utworzonego w ramach zgłoszenia.
public class _4_PropagacjaKomentarzy extends BaseTestClass {
    //Dane testowe
    private String issueURL;
    private String relatedIssue;
    private String komentarzWidocznyDlaCustomera = "Testowy comment widoczny dla Customera " + new Date().getTime();
    private String komentarzNieWidocznyDlaCustomera = "Testowy comment NIE widoczny dla Customera" + new Date().getTime();

    private String nrTaska;

    @Test(priority = 40)
    public void propagacjaPomiedzyZaleznymiTaskami() {
        //Z poziomu Taska założyc nowe zgłoszenie do 3 linii
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToFirstTaskOnList();

        //Zbieram dane do kolejnych testów
        issueURL = taskPage.getUrl();
        System.out.println("Task: " + issueURL);
        relatedIssue = taskPage.createLinkedIssue();
        nrTaska = taskPage.getTaskNumber();

        //Na nowo utworzonym Tasku wprowadzamy komentarz
        TaskPage relatedIssueTask = taskPage.goToRelatedIssue(relatedIssue);
        relatedIssueTask.clickOnButton(TaskButton.COMMENT);
        relatedIssueTask.wprowadzKomentarzWidocznyDlaCustomera(komentarzWidocznyDlaCustomera);
        relatedIssueTask.clickOnButton(TaskButton.COMMENT);
        relatedIssueTask.wprowadzKomentarz(komentarzNieWidocznyDlaCustomera);

        //Wracamy do Taska głównego i weryfikujemy czy komenarz został rozpropagowany
        relatedIssueTask.goToUrl(issueURL);
        TaskPage mainPage = new TaskPage(driver);
        Assert.assertTrue(mainPage.verifyCommentExist(komentarzWidocznyDlaCustomera), "nie udała sie propagacja komentarzy");
    }

    @Test(priority = 41, dependsOnMethods = {"propagacjaPomiedzyZaleznymiTaskami"})
    public void weryfikacjaPropagacjiKomentarzyDoCustomera() {
        //Uruchamiamy aplikacje Customer i weryfikujemy czy komentarz został tam rozpropagowany
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        CustomerTaskPage customerTaskPage = customerServiceLoginPage.goToTask(nrTaska);
        //Weryfikujemy czy komentarz ustawiony jako Widoczny dla klienta jest widoczny
        Assert.assertTrue(customerTaskPage.verifyCommentExist(komentarzWidocznyDlaCustomera), "Komenarz nie został rozpropagowany do Customera");
        //Weryfikujemy czy wewenętrzny komentarz nie został przekazany do klienta
        Assert.assertTrue(!customerTaskPage.verifyCommentExist(komentarzNieWidocznyDlaCustomera), "Komenarz został nie prawidłowo rozpropagowany do Customera");
    }

    //TODO trzeba by jeszcze sprawdzić czy w SD są te komentarze z odpowiednimi Labelkami
}
