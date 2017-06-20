package PageObjects.ServiceDesk.old;

import PageObjects.Base.AbstractJiraPage;
import core.ElementsOnPages.ProfilePageFields;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Piotr Majewski on 2017-05-25.
 */
public class ProfilePage extends AbstractJiraPage {

    @FindBy(xpath = "//*[@class='field-group']//*[@name='availability']")
    protected WebElement availability;

    @FindBy(xpath = "//*[@class='field-group']//*[@name='availabilityFromUTC']")
    protected WebElement availabilityFromUTC;

    @FindBy(xpath = "//*[@class='field-group']//*[@name='availabilityToUTC']")
    protected WebElement availabilityToUTC;

    @FindBy(xpath = "//*[@class='field-group']//*[@name='POC']")
    protected WebElement poc;

    @FindBy(xpath = "//*[@class='field-group']//*[@name='phone']")
    protected WebElement phone;

    @FindBy(xpath = "//*[@class='field-group']//*[@class='aui-button']")
    protected WebElement saveButton;


    public ProfilePage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/secure/ViewProfile.jspa");
    }

    public void clickInSaveButton() {
        saveButton.click();
    }

    public void typeInFields(ProfilePageFields fields, String input) {
        switch (fields) {
            case POC: {
                poc.clear();
                poc.sendKeys(input);
                break;
            }
            case AVAILABILITY: {
                availability.clear();
                availability.sendKeys(input);
                break;
            }
            case PHONE: {
                phone.clear();
                phone.sendKeys(input);
                break;

            }
            case AVAILABILITY_TO_UTC: {
                availabilityToUTC.clear();
                availabilityToUTC.sendKeys(input);
                break;
            }
            case AVAILABILITY_FROM_UTC: {
                availabilityFromUTC.clear();
                availabilityFromUTC.sendKeys(input);
                break;
            }
        }
    }

    public String getTextFromFields(ProfilePageFields fields) {
        String text = "";

        switch (fields) {
            case POC: {
                text = poc.getAttribute("value");
                break;
            }
            case AVAILABILITY: {
                text = availability.getAttribute("value");
                break;
            }
            case PHONE: {
                text = phone.getAttribute("value");
                break;

            }
            case AVAILABILITY_TO_UTC: {
                text = availabilityToUTC.getAttribute("value");
                break;
            }
            case AVAILABILITY_FROM_UTC: {
                text = availabilityFromUTC.getAttribute("value");
                break;
            }
        }
        return text;
    }
}
