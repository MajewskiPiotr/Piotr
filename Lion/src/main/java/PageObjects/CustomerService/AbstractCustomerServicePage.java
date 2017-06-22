package PageObjects.CustomerService;

import PageObjects.Base.PageObject;
import core.Tools.Configuration.TestEnviroments;
import org.openqa.selenium.WebDriver;

/**
 * Created by Piotr Majewski on 2017-06-21.
 */
public class AbstractCustomerServicePage extends PageObject {
    public AbstractCustomerServicePage(WebDriver driver) {
        super(driver);
    }

    public CustomerTaskPage goToTask(String url) {
        driver.navigate().to(TestEnviroments.CUSTOMER + url);
        return new CustomerTaskPage(driver);
    }
}
