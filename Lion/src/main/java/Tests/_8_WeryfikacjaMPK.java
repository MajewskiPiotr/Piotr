package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.EditIssuePage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-07-26.
 */
public class _8_WeryfikacjaMPK extends BaseTestClass {
    private String issue;
    private String mpk;
    private String relatedProjectName;

    // 1. Zgłosić bład
    @Test(priority = 80)
    public void generowanieBledu() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        issue = customerServicePage.zglosBlad("Zgłoszenie błedu - Test Automatyczne - Weryfikacja Przypisania Czasu SLA_withProductAffected", "Opis błedu dla automatu");
        Assert.assertTrue(!issue.equals(""), "nie udało sie poprawnie obsluzyc ISSUE");
    }

    // 2. Wyedytować i ustawic losowy Product
    // 3. Zapamiętac numer MPK przypisany do Product
    @Test(priority = 81)
    public void edycjaIssue() {
        ServiceDeskLoginPage login = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = login.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        queQuePage.clickOnButton(TaskButton.HELPDESK_DISPATCHER);
        TaskPage taskPage = queQuePage.goToTask("DLSD-130");
        System.out.println("task " + taskPage.getTaskNumber());
        //EditIssuePage editIssuePage = taskPage.edytujIssue();
       // editIssuePage.issueClasification();
        mpk = taskPage.getMpkNumber();
        System.out.println("LOG: mpk "+ mpk );
        relatedProjectName = taskPage.getRelatedProjectName();
        System.out.println("LOG: relatedProjectName " + relatedProjectName);

    }


    // 4. Create Linek Issue
    // 5. zapamiętać jaki Projek został wybrany
    // 6. Przejść do Insigh do MPK
    // 7. Odszukac zapamiętany numer MPK
    // 8. zweryfikować projekt do którego jest on przypisany
}
