package PageObjects.CustomerService;

import core.Tools.Tools;
import org.openqa.selenium.By;
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


    //Elementy wprowadzania danych do RFA

    //@FindBy(xpath = "//*[@id='select2-chosen-2']")
    @FindBy(xpath = "//*[@class='field-group']/label[text()='User company']//..//span[contains(.,'Search for an object')]")
    protected WebElement userCompanyField;

    //@FindBy(xpath = "//*[@id='select2-chosen-1']")
    @FindBy(xpath = "//*[@class='field-group']/label[text()='User name (login)']//..//span[contains(.,'Search for an object')]")
    protected WebElement userNameInRFAField;

    @FindBy(xpath = "//*[text()='Confluence space name']/../div[@class='field-container']/textarea")
    protected WebElement confSpaceName;


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

    public String createRFA_1(String user) {
        WebElement rfaLink = driver.findElement(By.xpath("//*[@id='cv-request-content']//strong[contains(.,'Access to Bizon')]"));
        rfaLink.click();
        new Actions(driver).click(userCompanyField).sendKeys("Evercode").build().perform();
        Tools.waitForProcesing(1000);
        new Actions(driver).sendKeys(Keys.TAB).build().perform();
        //Usatwiamy  User Name - Osoba dla której tworzone jest RFA
        new Actions(driver).click(userNameInRFAField).sendKeys(user).build().perform();
        Tools.waitForProcesing(1000);
        new Actions(driver).sendKeys(Keys.TAB).build().perform();
        createButton.click();
        String nrZgloszenia = zgloszenieNr.getText();
        System.out.println("RFA numer " + nrZgloszenia);
        driver.navigate().to(CustomerURL);
        return nrZgloszenia;
    }

    public String createRFA_2(String user) {

        WebElement rfaLink = driver.findElement(By.xpath("//*[@id='cv-request-content']//strong[contains(.,'Access to Confluence')]"));
        rfaLink.click();
        new Actions(driver).click(userCompanyField).sendKeys("Evercode").build().perform();
        Tools.waitForProcesing(2000);
        new Actions(driver).sendKeys(Keys.TAB).build().perform();

        //Usatwiamy  User Name - Osoba dla której tworzone jest RFA
        new Actions(driver).click(userNameInRFAField).sendKeys(user).build().perform();
        Tools.waitForProcesing(2000);

        new Actions(driver).sendKeys(Keys.TAB).build().perform();

        confSpaceName.sendKeys("testowo");

        createButton.click();


        String nrZgloszenia = zgloszenieNr.getText();
        System.out.println("RFA numer " + nrZgloszenia);
        driver.navigate().to(CustomerURL);
        return nrZgloszenia;
    }
}

