package Tests;

import Tools.Configuration.BrowserType;
import Tools.Configuration.EnviromentSettings;
import Tools.Configuration.TestEnviroments;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-05-26.
 */
public class TestowyTest extends BaseTestClass {

    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    @Test(priority = 50)
    public void zmodyfikujProfil() {
        driver.navigate().to("https://docs.oracle.com/javase/tutorial/essential/environment/properties.html");
    }


    @AfterMethod
    public void tearDown() {

        driver.close();
    }
}
