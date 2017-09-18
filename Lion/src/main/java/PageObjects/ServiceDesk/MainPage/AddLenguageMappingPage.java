package PageObjects.ServiceDesk.MainPage;

import PageObjects.Base.AbstractJiraPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Piotr Majewski on 2017-08-24.
 */
public class AddLenguageMappingPage extends AbstractJiraPage {

    @FindBy(xpath = "//*[@id='translationLanguage']")
    private WebElement language;

    @FindBy(xpath = "//*[@id='translationKey']")
    private WebElement keyInput;

    @FindBy(xpath = "//*[@id='translationValue']")
    private WebElement valueInput;

    @FindBy(xpath = "//*[@id='add-set-submit']")
    private WebElement addButton;

    @FindBy(xpath = "//*[@id='translationShared']")
    private WebElement sharedCheckBox;


    public AddLenguageMappingPage(WebDriver driver) {
        super(driver);
    }

    public void setLanguage(String lan) throws Exception {
        Select select = new Select(language);

        switch (lan) {
            case "pl":
                select.selectByValue("pl");
            case "en":
                select.selectByValue("pl");
            default: throw new Exception("Nie wybrano języka na jaki nalezy dokonać tłumaczenia");
        }
    }

    public void setKey(String key) {
        keyInput.sendKeys(key);
    }

    public void setValue(String value) {
        valueInput.sendKeys(value);
    }

    public void setShared() {
        sharedCheckBox.click();
    }

    public void addMapping() {
        addButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
