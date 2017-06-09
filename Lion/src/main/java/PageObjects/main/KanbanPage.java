package PageObjects.main;

import PageObjects.Base.AbstractJiraPage;
import PageObjects.Base.PageObject;
import PageObjects.ElementsOnPages.KanbanHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-15.
 * Klasa reprezentuje strone KANBAN w aplikacji JIRA
 */
public class KanbanPage extends AbstractJiraPage {

    @FindBy(xpath = "//*[@id='ghx-pool']/div[2]/ul/li[1]/div[*]/div[1]")
    List<WebElement> listofNew;
    @FindBy(xpath = "//*[@id='ghx-pool']/div[2]/ul/li[2]/div[*]/div[1]")
    List<WebElement> listofToDo;
    @FindBy(xpath = "//*[@id='ghx-pool']/div[2]/ul/li[3]/div[*]/div[1]")
    List<WebElement> listofInProgress;


    public KanbanPage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/secure/RapidBoard.jspa");


    }


    /*Wybieramy i klikamy w Task z listy
        @Param fromWitchColumn - okreslamy kolumne z ktorej bedziemy wybierac Task
        @Param witch - okreslamy ktory task z listy wybrac
      */
    public PageObject chooseTask(KanbanHeader fromWitchColumn, int witch) {
        List<WebElement> tempList = setListToSearch(fromWitchColumn);
        if (!tempList.isEmpty()) {
            WebElement element = tempList.get(witch - 1);
            element.click();
            driver.navigate().to(baseUrl + "/browse/" + element.getText());
            return new PageObject(driver);
        } else {
            Assert.fail("Lista tasków na Kanban jest pusta / Nie udało sie wejść na Kanban?");
        }
        return null;
    }

    //zwraca liste elementow w danej kolumnie Kanbana
    private List<WebElement> listOfWebElerments(List<WebElement> list) {
        List<WebElement> newList = new ArrayList<WebElement>();
        for (WebElement l : list) {
            newList.add(l.findElement(By.className("js-key-link")));
        }
        return newList;
    }


    //metoda pomocnicza. zwraca liste odpowiednia liste z Kanbana
    private List<WebElement> setListToSearch(KanbanHeader kanbanHeader) {
        List<WebElement> tempList = null;
        switch (kanbanHeader) {
            case NEW: {
                tempList = listOfWebElerments(listofNew);
                break;
            }
            case TODO:
                break;
            case INPROGRESS:
                break;
            case SELFQA:
                break;
            case ONHOLD:
                break;
            case DONE:
                break;
        }

        return tempList;
    }


}
