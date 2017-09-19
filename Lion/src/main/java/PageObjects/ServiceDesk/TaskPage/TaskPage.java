package PageObjects.ServiceDesk.TaskPage;

import PageObjects.Base.AbstractTaskPage;
import core.ElementsOnPages.Task.TaskStatus;
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
    @FindBy(xpath = "//span[contains(@class,'evercode-label-comment')]")
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

    public String getRelatedProjectName() {
        new Actions(driver).click(moreButton).click(createLinkedIssueButton).perform();
        WebElement project = driver.findElement(By.xpath("//*[@id='project-field']"));
        //String projectName = driver.findElement(By.xpath("//*[@id='project-field']"));
        System.out.println(project.getTagName());
        return project.getText();
    }

    public String createLinkedIssue() {
        new Actions(driver).click(moreButton).click(createLinkedIssueButton).perform();
        CreateLinkedIssue createissueLink = new CreateLinkedIssue(driver);
        // createissueLink.setProject();
        //createissueLink.setIssueType();
        //createissueLink.setCousedBy();
        createissueLink.setSoftwareTeam();
        createissueLink.create();
        String relatedLink = getNewCreatedIssueNumber();
        System.out.println("related Link " + relatedLink);
        return relatedLink;
    }

    public TaskPage goToRelatedIssue(String url) {
        goToUrl(TestEnviroments.VPN + "/browse/" + url);
        return new TaskPage(driver);
    }

    public String wprowadzKomentarz(String komentarz) {
        new Actions(driver).moveToElement(poleWprowadzaniaKomentarza).sendKeys(poleWprowadzaniaKomentarza, komentarz).perform();
        driver.findElement(By.id("issue-comment-add-submit")).click();
        Tools.waitForProcesing(3000);
        driver.navigate().refresh();
        return getLabel(komentarz);
    }


    public void wprowadzKomentarzWidocznyDlaCustomera(String komentarz) {
        WebElement sendToClientChecBox = driver.findElement(By.xpath("//*[@type='checkbox']"));
        new Actions(driver).moveToElement(poleWprowadzaniaKomentarza).sendKeys(poleWprowadzaniaKomentarza, komentarz).click(sendToClientChecBox).perform();
        driver.findElement(By.id("issue-comment-add-submit")).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//*[@id='issue-comment-add-cancel']"))));
        //TODO wait na coś
        driver.navigate().refresh();
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
        driver.navigate().refresh();
        //TODO zweryfikować czy labelka sie ustawiła
        driver.navigate().refresh();
    }

    public boolean isPause() {
        return pauseIcon.isDisplayed();
    }

    public String getSummary() {
        return summary.getText();
    }

    public String getLabel(String komentarz) {
        driver.navigate().refresh();
        Tools.waitForProcesing(1000);
        String comment = komentarz;
        WebElement label = driver.findElement(By.xpath("//div[contains(@class,'twixi-wrap')]//p[text()='" + comment + "']/../../..//span[contains(@class,'evercode-label-comment')]"));
        System.out.println(label.getText() + "  taka jest labelka");
        return label.getText();
    }

    public void closeRelatedIssue() {
        if (doneButton.isDisplayed()) {
            doneButton.click();
        } else {
            driver.findElement(By.id("opsbar-transitions_more")).click();
            driver.findElement(By.xpath("//*[@id='action_id_41']/span")).click();
        }
        //wypełniamy pole (Incident Duration)  w PopUpie DONE
        WebElement incidentDurationInput = driver.findElement(By.xpath("//*[@id='customfield_11002']"));
        incidentDurationInput.sendKeys("10");
        incidentDurationInput.click();
        //klikamy na guzik DONE na otwartym popUpie
        driver.findElement(By.xpath("//*[@id='issue-workflow-transition-submit']")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.DONE.getStatus()));

    }


}
