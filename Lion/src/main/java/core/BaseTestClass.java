package core;

import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Piotr Majewski on 2017-05-23.
 * klasa Bazowa dla wszystkich testow
 */
public class BaseTestClass {
    protected static WebDriver driver;
    protected LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());

    //@Parameters({"nodeUrl", "browser"})
    //String nodeUrl, String browser)
    @BeforeMethod(alwaysRun = true)
    public void setUp() throws MalformedURLException {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.VPN);
        driver = enviromentSettings.setUpDriver(null, "chrome", false);

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }

    public static WebDriver getDriver() {

        if (driver == null) {

            Assert.fail("Brak Drivera", new WebDriverException("Brak Drivera"));
        }
        return driver;
    }


    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

        //Call getScreenshotAs method to create image file

        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File DestFile = new File(fileWithPath);

        //Copy file at destination

        FileUtils.copyFile(SrcFile, DestFile);


    }


}
