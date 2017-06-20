package PageObjects.CustomerService;

import PageObjects.Base.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class CustomerServicePage extends PageObject {

    @FindBy(xpath = "//*[@id='cv-request-content']//strong[contains(.,'envir')]")
    protected WebElement enviromentIncidentButton;

    @FindBy(id = "summary")
    protected WebElement subjectInput;
    @FindBy(id = "description")
    protected WebElement descriptionInput;
    @FindBy(xpath = "//input[@value='Create']")
    protected WebElement createButton;
    @FindBy(xpath = "//*[@class='cv-breadcrumb-item'][3]")
    protected WebElement zgloszenieNr;


    public CustomerServicePage(WebDriver driver) {
        super(driver);
    }

    //Zgłaszam prosty bład i zwracam jesto numer.
    public String zglosBlad(String subject, String description) {
        enviromentIncidentButton.click();
        subjectInput.sendKeys(subject);
        descriptionInput.sendKeys(description);
        createButton.click();
        return zgloszenieNr.getText();
    }

}
