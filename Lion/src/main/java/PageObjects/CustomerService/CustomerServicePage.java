package PageObjects.CustomerService;

import core.Tools.Tools;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */

//Page nie został podzielony na Strone Głowną i strone na której uzupełnia się dane z powodu małej ilości elementów i małej skomplikowaności
public class CustomerServicePage extends AbstractCustomerServicePage {
    //Elementy ze strony głownej portalu Klienta
    @FindBy(xpath = "//*[@id='cv-request-content']//strong[contains(.,'envir')]")
    protected WebElement enviromentIncidentButton;

    //Notification of replacement
    @FindBy(xpath = "//*[@id='cv-request-content']//strong[contains(.,'Notification')]")
    protected WebElement substitutionButton;


    //Elementy ze strony do wprowadzania danych
    @FindBy(id = "summary")
    protected WebElement subjectInput;
    @FindBy(id = "description")
    protected WebElement descriptionInput;
    @FindBy(xpath = "//input[@value='Create']")
    protected WebElement createButton;
    @FindBy(xpath = "//*[@class='cv-breadcrumb-item'][3]")
    protected WebElement zgloszenieNr;


    //Elementy wprowadzania danych do Zastępstwa
    @FindBy(id = "s2id_customfield_11003")
    protected WebElement userNameField;

    @FindBy(id = "s2id_customfield_11004")
    protected WebElement replacementNameField;


    public CustomerServicePage(WebDriver driver) {
        super(driver);
    }

    //Zgłaszam prosty bład i zwracam jesto numer.
    public String zglosBlad(String subject, String description) {
        enviromentIncidentButton.click();
        subjectInput.sendKeys(subject);
        descriptionInput.sendKeys(description);
        createButton.click();
        String nrZgloszenia = zgloszenieNr.getText();
        driver.navigate().to(CustomerURL);
        return nrZgloszenia;
    }

    //Tworzymy zgłoszenie zastępstwa
    public String setSubstitution(String userNname, String replacementUserName) {
        substitutionButton.click();
        //Ustawiamy UserName - Osoba która będzie zastępowana
        new Actions(driver).click(userNameField).sendKeys(userNname).build().perform();
        Tools.waitForProcesing(1000);
        new Actions(driver).sendKeys(Keys.TAB).build().perform();

        //Usatwiamy Replacement User Naem - Osoba która będzie zastępować
        new Actions(driver).click(replacementNameField).sendKeys(replacementUserName).build().perform();
        Tools.waitForProcesing(1000);
        new Actions(driver).sendKeys(Keys.TAB).build().perform();

        createButton.click();
        String nrZgloszenia = zgloszenieNr.getText();
        System.out.println("zgłoszenie zastępstwa numer " + nrZgloszenia);
        driver.navigate().to(CustomerURL);
        return nrZgloszenia;
    }
}

