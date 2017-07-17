package Tests.BaseTest;

import core.Reports.TestData;
import core.Tools.Configuration.BrowserType;
import core.Tools.Configuration.EnviromentSettings;
import core.Tools.Configuration.TestEnviroments;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Piotr Majewski on 2017-05-23.
 * klasa Bazowa dla wszystkich testow
 */
public class BaseTestClass {
    protected static WebDriver driver;
    protected static TestData data;
    protected LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());

    public static TestData getData() {
        return data;
    }

    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.LOKAL_JAKUB);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }


    @AfterMethod
    public void tearDown() {
        driver.close();
    }
    public static WebDriver getDriver() {

        if (driver == null) {

            Assert.fail("Brak Drivera", new WebDriverException("Brak Drivera"));
        }
        return driver;
    }

    /**
     * This function will take screenshot
     *
     * @param webdriver
     * @param fileWithPath
     * @throws Exception
     */

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




    @BeforeClass
    public void setUpTestData() {
        data = TestData.readTestData(this.getClass().getSimpleName());
        if (data.getScenariusz() == null) {
            data.setScenariusz(this.getClass().getSimpleName());
        }

    }

    @AfterClass
    public void clean() {
        TestData.flush(this.getClass().getSimpleName());

    }
}
