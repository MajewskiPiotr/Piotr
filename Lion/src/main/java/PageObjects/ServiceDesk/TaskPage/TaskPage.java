package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


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
        String allertTxt = allert.getText();
        String relatedLink = allertTxt.substring(0, allertTxt.indexOf(" "));
        System.out.println("related Link " + relatedLink);
        return relatedLink;

    }

    public TaskPage goToRelatedIssue(String url) {
        goToUrl(url);
        return new TaskPage(driver);
    }

    public void wprowadzKomentarz(String komentarz) {
        new Actions(driver).moveToElement(poleWprowadzaniaKomentarza).sendKeys(poleWprowadzaniaKomentarza, komentarz).perform();
        driver.findElement(By.id("issue-comment-add-submit")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean zweryfikujKomentarz(String badanyKomentarz) {
        boolean isPresent = false;
        for (String komentarze : getKomentarze()) {
            if (komentarze.contains(badanyKomentarz)) {
                isPresent = true;
            }
        }
        return isPresent;
    }
}
