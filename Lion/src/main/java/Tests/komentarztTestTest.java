package Tests;

import PageObjects.ServiceDesk.MainPage.QueQuePage;
import PageObjects.ServiceDesk.MainPage.ServiceDeskLoginPage;
import PageObjects.ServiceDesk.TaskPage.TaskPage;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-06-26.
 */
public class komentarztTestTest extends BaseTestClass {


    @Test
    public void cos() {
        ServiceDeskLoginPage serviceDeskLoginPage = new ServiceDeskLoginPage(driver);
        QueQuePage queQuePage = serviceDeskLoginPage.loginAsAgentAngGoToQueque();
        TaskPage taskPage = queQuePage.goToTask("DLSD-332");


    }

}
