package Tests;

import Elements.Task.TaskButton;
import Elements.Task.TaskStatus;
import PageObjects.TaskPage.JobTaskPage;
import PageObjects.main.CurrentSearchPage;
import PageObjects.main.LoginPage;
import Tools.JsScript;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class TestBase {


    public String generujJoba(WebDriver driver){
        LoginPage loginAsAdmin = new LoginPage(driver);
        loginAsAdmin.open();
        loginAsAdmin.logInToJira("piotr.majewski", "piotr.majewski");

        //tworzymy skryptem Task (JOBa)
        try {
            JsScript.createJob(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //wyszukuje utworzonego Taska (JOBa)
        CurrentSearchPage searchPage = new CurrentSearchPage(driver);
        JobTaskPage taskJobViewPage = searchPage.searchForNewTransactionJOB();
        //na potrzeby kolejnego testu przekazuje adres do utworzonego zadania
        String jobTaskURL = searchPage.getTaskNumberURL();
        //weryfikacja stanu utworzonego Taska
        Assert.assertEquals(taskJobViewPage.getStatus(), TaskStatus.WAITING_FOR_PACKAGING);

        taskJobViewPage.clickOnButton(TaskButton.PROCESSING);


        //weryfikacja stanu
        Assert.assertEquals(taskJobViewPage.getStatus(), TaskStatus.IN_PROCESSING);
        return  jobTaskURL;
    }
}
