package PageObjects.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class RelatedTaskPage extends AbstractTaskPage {
    @FindBy(xpath ="//*[@id='addcomment']" )
    protected WebElement poleWprowadzaniaKomentarza;

    public RelatedTaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





}
