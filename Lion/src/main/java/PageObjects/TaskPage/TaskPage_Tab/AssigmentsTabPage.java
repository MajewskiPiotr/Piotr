package PageObjects.TaskPage.TaskPage_Tab;

import PageObjects.Base.AbstractJiraPage;
import PageObjects.ElementsOnPages.Task.Tab.AssignmentsType;
import core.Tools.FindInTaskTab;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-18.
 * Klasa odzwierciedla zakladke ASSIGMENT
 */
public class AssigmentsTabPage extends AbstractJiraPage {

    @FindBy(xpath = "//*[@id='customfield_12000-val']//tbody/tr")
    private List<WebElement> assignments;


    public AssigmentsTabPage(WebDriver driver) {
        super(driver);
        //czekamy na pojawienie się listy z assignments

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("rowForcustomfield_12000")));
        } catch (TimeoutException e) {
            Assert.fail("Lista zadań nie została załadowana poprawnie");
        }


    }

    public List<WebElement> getAssigmentsListByType(AssignmentsType type) {
        List<WebElement> newLista = new ArrayList<>();
        List<WebElement> oldLista = getAssignments();
        for (WebElement element : oldLista) {
            System.out.println("badany :" + FindInTaskTab.getTaskType(element).getText());
            if (FindInTaskTab.getTaskType(element).getText().equals(type.getTaskType())) {
                System.out.println("dodaje");
                newLista.add(element);
            }
        }
        for (WebElement lis : newLista) {
            System.out.println("new Lista " + FindInTaskTab.getTaskType(lis).getText());
        }
        return newLista;
    }

    private List<WebElement> getAssignments() {
        if (assignments == null) {
            Assert.fail("lista Assignments nie została zainicjowana bądź jest pusta");
        }
        wypiszListe(assignments);
        return assignments;
    }

    //wewnętrzna funkcja wypisywania
    private void wypiszListe(List<WebElement> elements) {
        System.out.println("Old lista");
        for (WebElement w : elements) {
            System.out.println(w.getText());
        }

    }

}
