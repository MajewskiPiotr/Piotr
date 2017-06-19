package Tests;

import PageObjects.MainPage.DashboardPage;
import PageObjects.MainPage.Insightpage;
import PageObjects.MainPage.LoginPage;
import PageObjects.MainPage.QueQuePage;
import PageObjects.TaskPage.TaskPage;
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
public class WeryfikacjaPrzypisaniaCzasuSLA extends BaseTestClass {
    private String category;
    private List<String> productClassList = new ArrayList<>();
    private int solutionTimeFromInsight;
    private int solutionTimeFromTask;

    @Test
    public void weryfikacjaPoprawnosciCzasuSlaTaska() {
        //logujemy sie do aplikacji i wchodzimy na zadanie
        LoginPage login = new LoginPage(driver);
        DashboardPage dashboardPage = login.loginAsAdmin();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        queQuePage.clickOnButton(TaskButton.HELPDESK_DISPATCHER);
        TaskPage taskPage = (TaskPage) queQuePage.goToTask("DLSD-158");

        //pobieramy potrrzebne dane z Taska
        productClassList = taskPage.getProductClass();
        category = taskPage.getTextFromField(TaskField.Category);
        solutionTimeFromTask = Integer.parseInt(taskPage.getTextFromField(TaskField.SLA));

        System.out.println("productClass " + productClassList.toString());
        System.out.println("category " + category.toString());
        System.out.println("solutionTimeFromTask " + solutionTimeFromTask);

        //Przechodzimy do insight i sprawdzamy jakie SLA popwinno mieć zgłoszenie o podanych parametrach
        DashboardPage dashboardPage1 = taskPage.goToDashboard();

        Insightpage insightpage = dashboardPage1.goToInsightPage();
        insightpage.clickOnButton(TaskButton.SLA);
        insightpage.switchViewToList();
        solutionTimeFromInsight = insightpage.findSolutionTime(productClassList, category);
        System.out.println("wyniki : " + solutionTimeFromInsight);

        //weryfikacja SLA z Taska i SLA z INSIGHTA
        Assert.assertEquals(solutionTimeFromInsight, solutionTimeFromTask);
    }
}
