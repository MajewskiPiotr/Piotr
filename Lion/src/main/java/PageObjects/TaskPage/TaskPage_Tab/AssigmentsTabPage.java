package PageObjects.TaskPage.TaskPage_Tab;

import PageObjects.Base.AbstractJiraPage;
import core.ElementsOnPages.Task.Tab.AssignmentsType;
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

    @FindBy(xpath = "//*[@id='customfield_12000-val']//tbody/tr[*]")
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
        List<WebElement> newWebElementList = new ArrayList<>();
        for (int i = 0; i < assignments.size(); i++) {
            WebElement webElementUnderTest = assignments.get(i);
            if (FindInTaskTab.getTaskTypeFromAssignmentTab(webElementUnderTest, i+1).getText().equals(type.getTaskType())) {
                newWebElementList.add(webElementUnderTest);
            }
        }
        return newWebElementList;
    }

    private List<WebElement> getAssignments() {
        if (assignments == null) {
            Assert.fail("lista Assignments nie została zainicjowana bądź jest pusta");
        }
        return assignments;
    }

    //wewnętrzna funkcja wypisywania


}
