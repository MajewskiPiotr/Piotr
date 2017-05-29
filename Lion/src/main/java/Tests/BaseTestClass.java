package Tests;

import Reports.TestData;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by Piotr Majewski on 2017-05-23.
 * klasa Bazowa dla wszystkich testow
 */
public class BaseTestClass {
    protected static WebDriver driver;
    static TestData data;
    protected LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());

    public static TestData getData() {
        return data;
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
        System.out.println("before");
        data = TestData.readTestData(this.getClass().getSimpleName());

        if (data.isZakonczono()) {
            TestData.flush();
        }
        if (data.getScenariusz() == null) {
            data.setScenariusz(this.getClass().getSimpleName());
        }
        System.out.println("Scenariusz to: " + data.getScenariusz());

    }
}
