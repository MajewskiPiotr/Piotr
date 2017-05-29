package Tests;

import Tools.Configuration.BrowserType;
import Tools.Configuration.EnviromentSettings;
import Tools.Configuration.TestEnviroments;
import Web.PageObjects.Elements.ProfilePageFields;
import Web.PageObjects.main.LoginPage;
import Web.PageObjects.main.ProfilePage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Created by Piotr Majewski on 2017-05-25.
 */
public class ProfileUpdateTest extends BaseTestClass {

    @BeforeMethod
    public void setUp() {
        EnviromentSettings enviromentSettings = new EnviromentSettings();
        enviromentSettings.SetTestEnviroment(TestEnviroments.STAGE1);
        driver = enviromentSettings.setUpDriver(BrowserType.CHROME);
    }

    @Test(priority = 50)
    public void zmodyfikujProfil() {
        //Dane Testowe
        data.setAvailability(String.valueOf((new Random().nextInt() / 1000)));
        data.setAvailabilityFromUtc(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        data.setAvailabilityToUtc(time.plusHours(5).format(DateTimeFormatter.ISO_LOCAL_TIME));
        data.setPoc(new Random().nextInt() + "@test.pl");
        data.setPhnone(String.valueOf(new Random().nextInt()));

        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        ProfilePage profilePage = loginAsTranslator.logInToJiraAndGoToProfile("001-HU_00588888", "lion");
        //wprowadzam dane
        profilePage.typeInFields(ProfilePageFields.AVAILABILITY, data.getAvailability());
        profilePage.typeInFields(ProfilePageFields.AVAILABILITY_FROM_UTC, data.getAvailabilityFromUtc());
        profilePage.typeInFields(ProfilePageFields.AVAILABILITY_TO_UTC, data.getAvailabilityToUtc());
        profilePage.typeInFields(ProfilePageFields.POC, data.getPoc());
        profilePage.typeInFields(ProfilePageFields.PHONE, data.getPhnone());

        profilePage.clickInSaveButton();
    }

    @Test(priority = 51)
    public void sprawdzDaneProfilu() {
        System.out.println(data.toString());
        LoginPage loginAsTranslator = new LoginPage(driver);
        loginAsTranslator.open();
        ProfilePage profilePage = loginAsTranslator.logInToJiraAndGoToProfile("001-HU_00588888", "lion");

        Assert.assertEquals(profilePage.getTextFromFields(ProfilePageFields.AVAILABILITY), data.getAvailability());
        Assert.assertEquals(profilePage.getTextFromFields(ProfilePageFields.AVAILABILITY_FROM_UTC), data.getAvailabilityFromUtc());
        Assert.assertEquals(profilePage.getTextFromFields(ProfilePageFields.AVAILABILITY_TO_UTC), data.getAvailabilityToUtc());
        Assert.assertEquals(profilePage.getTextFromFields(ProfilePageFields.POC), data.getPoc());
        Assert.assertEquals(profilePage.getTextFromFields(ProfilePageFields.PHONE), data.getPhnone());

    }

    @AfterMethod
    public void tearDown() {

        driver.close();
    }
}
