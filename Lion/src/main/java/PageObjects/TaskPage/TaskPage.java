package PageObjects.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import core.ElementsOnPages.Task.TaskButton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class TaskPage extends AbstractTaskPage {


    public TaskPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOf(status));
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    public String createLinkedIssue() {

        new Actions(driver).click(moreButton).click(createLinkedIssueButton).perform();

        CreateLinkedIssue createissueLink = new CreateLinkedIssue(driver);
        createissueLink.setProject();
        createissueLink.setIssueType();
        createissueLink.setDescription();
        createissueLink.create();
        String temp = allert.getText();
        String relatedLink = temp.substring(0, temp.indexOf(" "));
        System.out.println("related Link "+ relatedLink);
        return relatedLink;

    }

    public RelatedTaskPage goToRelatedIssue(String url){
       return (RelatedTaskPage) goToTask(baseUrl+"/browse/" + url);
    }
}
