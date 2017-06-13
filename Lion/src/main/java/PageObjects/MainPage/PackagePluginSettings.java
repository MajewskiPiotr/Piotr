package PageObjects.MainPage;

import PageObjects.Base.AbstractJiraPage;
import core.ElementsOnPages.Task.TaskButton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Piotr Majewski on 2017-05-22.
 */
public class PackagePluginSettings extends AbstractJiraPage {

    @FindBy(xpath = "//*[@id='content']//*[@value='Execute']")
    protected WebElement executeButton;

    @FindBy(xpath = "//*[@id='lion-package-results']")
    protected WebElement packageResult;

    @FindBy(id = "operation")
    protected WebElement selectOperationList;

    public PackagePluginSettings(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(selectOperationList));
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {

        }
    }

    public boolean isPackageResult() {
        return packageResult.isDisplayed();
    }

    public void changeSelectOperationOnPack() {
        Select select = new Select(selectOperationList);
        select.selectByVisibleText("Pack");
    }
}


