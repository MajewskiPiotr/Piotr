package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.JiraWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class TaskPage extends AbstractTaskPage {
    @FindBy(xpath = "//*[@id='sla-web-panel']//*/span[contains(@class,'pause')]")
    protected WebElement pauseIcon;

    @FindBy(xpath = "//*[@id='issue_actions_container']//div[@class='action-body flooded']")
    protected List<WebElement> listaKomentarzy;

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
        goToUrl(TestEnviroments.STAGE1 + "/browse/" + url);
        return new TaskPage(driver);
    }

    public void wprowadzKomentarzWidocznyDlaCustomera(String komentarz) {
        WebElement sendToClientChecBox = driver.findElement(By.xpath("//*[@type='checkbox']"));
        new Actions(driver).moveToElement(poleWprowadzaniaKomentarza).sendKeys(poleWprowadzaniaKomentarza, komentarz).click(sendToClientChecBox).perform();
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

    public void przypiszZadanie() {

    }

    public EditIssuePage edytujIssue() {
        editButton.click();
        return new EditIssuePage(driver);
    }

    public void respondToCustomer(String komentarz) {
        respondToCustomerButton.click();
        new RespondToCustomerPage(driver).przekazDoCustomera(komentarz);
        JiraWait.waitForProcesing(2000);
    }

    public boolean isPause(){
        return pauseIcon.isDisplayed();
    }



}
