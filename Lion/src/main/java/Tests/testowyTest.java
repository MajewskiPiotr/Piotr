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
public class testowyTest extends BaseTestClass {
    private String category;
    private List<String> productClassList = new ArrayList<>();
    private String solutionTimeFromInsight;
    private String solutionTimeFromTask;

    @Test
    public void test() {
        //logujemy sie do aplikacji i wchodzimy na zadanie
        LoginPage login = new LoginPage(driver);
        DashboardPage dashboardPage = login.loginAsAdmin();
        QueQuePage queQuePage = dashboardPage.goToQueQue();
        queQuePage.clickOnButton(TaskButton.HELPDESK_DISPATCHER);
        TaskPage taskPage = queQuePage.goToTask();

        //pobieramy potrrzebne dane z Taska
        productClassList = taskPage.getProductClass();
        category = taskPage.getTextFromField(TaskField.Category);
        solutionTimeFromTask = taskPage.getTextFromField(TaskField.SLA);


        System.out.println("productClass "+productClassList.toString());
        System.out.println("category "+category.toString());
        System.out.println("solutionTimeFromTask "+solutionTimeFromTask.toString());


        //Przechodzimy do insight
        DashboardPage dashboardPage1 = taskPage.goToDashboard();

        Insightpage insightpage = dashboardPage1.goToInsightPage();
        insightpage.clickOnButton(TaskButton.SLA);
        insightpage.switchViewToList();
        solutionTimeFromInsight = insightpage.findSolutionTime(productClassList, category);
        System.out.println("wyniki : "+solutionTimeFromInsight);
        Assert.assertEquals(solutionTimeFromInsight, solutionTimeFromTask);
    }
}
