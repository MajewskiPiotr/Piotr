package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */

//klasa reprezentuje relatedTask jaki podłączamy do głównego Taska
//różnica w lokatorach dla komentarzy
public class RelatedTaskPage extends AbstractTaskPage {
    @FindBy(xpath = "//*[@id='addcomment']")
    protected WebElement poleWprowadzaniaKomentarza;

    //Guziki
    @FindBy(xpath = "//*[@id='addcomment']/sd-comment//button[contains(.,'Udostępnij Klientowi')]")
    protected WebElement udostepnijKlientowiButton;
    @FindBy(xpath = "//*[@id='addcomment']/sd-comment//button[contains(.,'Komentarz prywatny')]")
    protected WebElement komentarzPrywatnyButton;


    public RelatedTaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void wprowadzKomentarzDlaKlienta(String komentarz) {
        new Actions(driver).moveToElement(poleWprowadzaniaKomentarza).sendKeys(poleWprowadzaniaKomentarza, komentarz).perform();
        udostepnijKlientowiButton.click();
    }


}
