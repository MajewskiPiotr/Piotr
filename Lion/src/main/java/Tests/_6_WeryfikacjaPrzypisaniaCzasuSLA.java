package Tests;

import PageObjects.CustomerService.CustomerServiceLoginPage;
import PageObjects.CustomerService.CustomerServicePage;
import PageObjects.ServiceDesk.MainPage.DashboardPage;
import PageObjects.ServiceDesk.MainPage.Insightpage;
import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.EditIssuePage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import core.ElementsOnPages.Task.TaskButton;
import core.ElementsOnPages.Task.TaskField;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-08.
 */
//test weryfikuje poprawność wyznaczenia czasu SLA na podstawie macieży ProduktClass i Category z Insight
public class _6_WeryfikacjaPrzypisaniaCzasuSLA extends BaseTestClass {
    private String category;
    private List<String> productClassList = new ArrayList<>();
    private int solutionTimeFromInsight;
    private int solutionTimeFromTask;
    private String issue;

    @Test(priority = 60)
    public void generowanieBledu() {
        CustomerServiceLoginPage customerServiceLoginPage = new CustomerServiceLoginPage(driver);
        CustomerServicePage customerServicePage = customerServiceLoginPage.logInToCustomer();
        issue = customerServicePage.zglosBlad("Zgłoszenie błedu - Test Automatyczne - Weryfikacja Przypisania Czasu SLA", "Opis błedu dla automatu");
        System.out.println("issue number : " + issue);
        Assert.assertTrue(!issue.equals(""), "nie udało sie poprawnie obsluzyc ISSUE");

    }

    @Test(priority = 61, dependsOnMethods = {"generowanieBledu"})
    public void weryfikacjaPoprawnosciCzasuSlaTaska() {
        //logujemy sie do aplikacji i wchodzimy na zadanie
        ServiceDeskLoginPage login = new ServiceDeskLoginPage(driver);
        DashboardPage dashboardPage = login.loginAsAgent();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        queQuePage.clickOnButton(TaskButton.HELPDESK_DISPATCHER);
        TaskPage taskPage = queQuePage.goToTask(issue);
        EditIssuePage editIssuePage = taskPage.edytujIssue();
        editIssuePage.issueClasification();


        //pobieramy potrrzebne dane z Taska
        productClassList = taskPage.getProductClass();
        category = taskPage.getTextFromField(TaskField.Category);
        solutionTimeFromTask = Integer.parseInt(taskPage.getTextFromField(TaskField.SLA));


        //Przechodzimy do insight i sprawdzamy jakie SLA popwinno mieć zgłoszenie o podanych parametrach
        DashboardPage dashboardPage1 = taskPage.goToDashboard();

        Insightpage insightpage = dashboardPage1.goToInsightPage();
        insightpage.clickOnButton(TaskButton.SLA);
        insightpage.switchViewToList();
        solutionTimeFromInsight = insightpage.findSolutionTime(productClassList, category);
        System.out.println("wyniki : " + solutionTimeFromInsight);

        //weryfikacja SLA z Taska i SLA z INSIGHTA
        Assert.assertEquals(solutionTimeFromInsight, solutionTimeFromTask, "Czas SLA został wyliczony nie poprawnie");
    }
}
