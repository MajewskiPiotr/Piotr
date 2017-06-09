package PageObjects.TaskPage.TaskPage_Tab;

import PageObjects.Base.PageObject;
import PageObjects.TaskPage.TaskPage;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-09.
 */
public class IJiraTab extends PageObject{


    TaskPage goToTask(WebElement element){return null;};

    List<WebElement> getlistaTaskow(){return null;};

    int countTranslatorTask(List<WebElement> elements){return 0;};

    WebElement getTaskfromList(int witchTask, List<WebElement> list){return null;};

    WebElement getLinkFromTask(WebElement element){return null;};

    void otworzWszystkieTranslationTask(){};
    //    new Actions(driver).keyDown(Keys.LEFT_CONTROL).click(taskLink).keyUp(Keys.LEFT_CONTROL).build().perform();


    boolean checkIsStatusChange(){return false;};
}
