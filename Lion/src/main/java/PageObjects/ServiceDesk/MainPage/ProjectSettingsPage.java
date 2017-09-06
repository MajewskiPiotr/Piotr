package PageObjects.ServiceDesk.MainPage;

import PageObjects.Base.AbstractJiraPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Piotr Majewski on 2017-08-24.
 */
public class ProjectSettingsPage extends AbstractJiraPage {

    @FindBy(xpath = "//*[@id='add-issue-type']")
    private WebElement addNewTranslation;

    public ProjectSettingsPage(WebDriver driver) {
        super(driver);
    }

    public AddLenguageMappingPage addNewTranslation() {
        addNewTranslation.click();
        return new AddLenguageMappingPage(driver);
    }
}
