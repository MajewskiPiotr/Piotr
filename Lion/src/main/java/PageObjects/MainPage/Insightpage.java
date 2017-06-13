package PageObjects.MainPage;

import PageObjects.Base.AbstractJiraPage;
import core.ElementsOnPages.Task.TaskButton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Piotr Majewski on 2017-06-13.
 */
public class Insightpage extends AbstractJiraPage {
    //linki w lewym menu
    @FindBy(xpath = "//*[@id='rlabs_jstree_7']/a")
    private WebElement sla;

    public Insightpage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl+ "/secure/ObjectSchema.jspa?id=1");
    }
    @Override
    public void clickOnButton(TaskButton button) {
        switch (button){
            case SLA:{
                sla.click();
               break;
            }
        }
    }

    public void switchViewToList(){
        driver.navigate().to(getUrl()+"&view=list");
    }
}
