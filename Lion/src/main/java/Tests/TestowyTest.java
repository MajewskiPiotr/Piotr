package Tests;

import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

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
    public void zmodyfikujProfil() throws IOException {
        Assert.assertTrue(false);
    }

    @Test(priority = 51)
    public void zmodyfikujProfi3() throws IOException {
        Assert.assertTrue(true);

    }


    @AfterMethod
    public void tearDown() {

       driver.close();
    }
}
