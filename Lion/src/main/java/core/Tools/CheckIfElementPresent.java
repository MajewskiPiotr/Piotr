package core.Tools;

import Tests.BaseTestClass;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Piotr Majewski on 2017-06-14.
 */
public class CheckIfElementPresent {

    public static boolean is(WebElement element) {
        WebElement e = (new WebDriverWait(BaseTestClass.getDriver(), 20)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return element;
            }
        });


        boolean present;
        try {
            e.isEnabled();
            present = true;
        } catch (NoSuchElementException ex) {
            present = false;
        }

        return present;


    }

}

