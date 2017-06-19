package Tests;

import PageObjects.MainPage.DashboardPage;
import PageObjects.MainPage.LoginPage;
import PageObjects.MainPage.QueQuePage;
import PageObjects.TaskPage.RelatedTaskPage;
import PageObjects.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import org.testng.annotations.Test;

/**
 * Created by Piotr on 2017-06-16.
 */

//Test weryfikuje czy komentarze poprawnie zostają przeniesione z Taska utworzonego w ramach zgłoszenia.
public class PropagacjaKomentarzy extends BaseTestClass {
    @Test
    public void propagacjaPomiedzyZaleznymiTaskami() {

        //Z poziomu Taska założyc nowe zgłoszenie do 3 linii
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.loginAsAdmin();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        TaskPage taskPage = queQuePage.goToTask("DLSD-164");

        String relatedIssue = taskPage.createLinkedIssue();
        RelatedTaskPage relatedIssueTask = taskPage.goToRelatedIssue(relatedIssue);
        relatedIssueTask.clickOnButton(TaskButton.COMMENT);
        relatedIssueTask.wprowadzKomentarz("komentarz testowy");


    }
}
