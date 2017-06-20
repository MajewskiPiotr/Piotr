package Tests;

import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
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
    private String komentarz = "testowykomentarz" + new Date().getTime();

    @Test
    public void propagacjaPomiedzyZaleznymiTaskami() {

        //Z poziomu Taska założyc nowe zgłoszenie do 3 linii
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = serviceDeskLoginPage.loginAsAdmin();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask("DLSD-164");
        issueURL = taskPage.getUrl();
        relatedIssue = taskPage.createLinkedIssue();

        //Na nowo utworzonym Tasku wprowadzamy komentarz
        TaskPage relatedIssueTask = taskPage.goToRelatedIssue(relatedIssue);
        relatedIssueTask.clickOnButton(TaskButton.COMMENT);
        relatedIssueTask.wprowadzKomentarz(komentarz);

        //Wracamy do Taska głównego i weryfikujemy czy komenarz został rozpropagowany
        relatedIssueTask.goToUrl(issueURL);
        TaskPage mainPage = new TaskPage(driver);
        Assert.assertTrue(mainPage.zweryfikujKomentarz(komentarz), "nie udała sie propagacja komentarzy");

        //Uruchamiamy aplikacje Customer i weryfikujemy czy komentarz został tam rozpropagowany
        mainPage.uruchomAplikacjeCustomer(mainPage.getTaskNumber());


    }
}
