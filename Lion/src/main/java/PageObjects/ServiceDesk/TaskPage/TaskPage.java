package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import core.Tools.Configuration.TestEnviroments;
import core.Tools.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * Created by Piotr Majewski on 2017-05-15.
 */
public class TaskPage extends AbstractTaskPage {
    @FindBy(xpath = "//*[@id='sla-web-panel']//*/span[contains(@class,'pause')]")
    protected WebElement pauseIcon;
    @FindBy(id = "summary-val")
    protected WebElement summary;
    //TODO z tego zrobić liste albo coś :)
    @FindBy(xpath = "//*[@class='sd-comment-secondary-details']")
    protected WebElement commentLabel;

    //Konstruktor
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
        createissueLink.setCousedBy();
        createissueLink.setSoftwareTeam();
        createissueLink.create();
        String relatedLink = getNewCreatedIssueNumber();
        System.out.println("related Link " + relatedLink);
        return relatedLink;
    }

    public TaskPage goToRelatedIssue(String url) {
        goToUrl(TestEnviroments.STAGE1 + "/browse/" + url);
        return new TaskPage(driver);
    }

    public void wprowadzKomentarz(String komentarz) {
        new Actions(driver).moveToElement(poleWprowadzaniaKomentarza).sendKeys(poleWprowadzaniaKomentarza, komentarz).perform();
        driver.findElement(By.id("issue-comment-add-submit")).click();
        Tools.waitForProcesing(3000);
    }

    public void wprowadzKomentarzWidocznyDlaCustomera(String komentarz) {
        WebElement sendToClientChecBox = driver.findElement(By.xpath("//*[@type='checkbox']"));
        new Actions(driver).moveToElement(poleWprowadzaniaKomentarza).sendKeys(poleWprowadzaniaKomentarza, komentarz).click(sendToClientChecBox).perform();
        driver.findElement(By.id("issue-comment-add-submit")).click();
        Tools.waitForProcesing(3000);
    }

    public boolean verifyCommentExist(String badanyKomentarz) {
        boolean isPresent = false;
        for (String komentarze : getKomentarze()) {
            if (komentarze.contains(badanyKomentarz)) {
                isPresent = true;
            }
        }
        return isPresent;
    }

    public EditIssuePage edytujIssue() {
        editButton.click();
        return new EditIssuePage(driver);
    }

    public void respondToCustomer(String komentarz) {
        respondToCustomerButton.click();
        new RespondToCustomerPage(driver).przekazDoCustomera(komentarz);
        Tools.waitForProcesing(2000);
        //TODO zweryfikować czy labelka sie ustawiła
    }

    public boolean isPause() {
        return pauseIcon.isDisplayed();
    }

    public String getSummary() {
        return summary.getText();
    }


}
