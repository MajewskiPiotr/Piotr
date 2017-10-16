package PageObjects.CustomerService;

import core.ElementsOnPages.Task.TaskStatus;
import core.Tools.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

/**
 * Created by Piotr Majewski on 2017-06-20.
 */
public class CustomerTaskPage extends AbstractCustomerServicePage {

    @FindBy(xpath = "//*[@id='comment-on-request']")
    protected WebElement poleWprowadzaniaTekstu;
    @FindBy(xpath = "//*[@id='comment-form']//*[@value='Add']")
    protected WebElement addButton;
    @FindBy(xpath = "//*[@id='content']//*[@class='vp-request-header']/span[2]")
    protected WebElement status;
    @FindBy(xpath = "//*[@class='vp-activity-list']//*//div[contains(@class, 'comment')]")
    protected List<WebElement> listaKomentarzy;

    @FindBy(xpath = "//*[@id='com.atlassian.servicedesk:workflow-transition-11']")
    protected WebElement closeSubstButton;
    @FindBy(xpath = "//*[@class[contains(.,'cv-approval-status')]]//ul[@class=\"cv-user-list\"]//span[@class='sd-user-value']")
    protected List<WebElement> securityApprovalUserList;
    @FindBy(xpath = "//*[@class[contains(.,'cv-approval-status')]]//h5[text()='Supervisor approval']//..//span[@class='sd-user-value']")
    protected WebElement supervisorApprovalUser;

    @FindBy(xpath = "//*[@class[contains(.,'cv-approval-status')]]//h5[text()='Specialists approval']//..//span[@class='sd-user-value']")
    protected List<WebElement> specialistApprovalUserList;


    @FindBy(xpath = "//*[@id='content']//button[contains(.,'Approve')]")
    protected WebElement approveButton;

    public CustomerTaskPage(WebDriver driver) {
        super(driver);
    }

    public String getStatus() {
        return status.getText();
    }

    public void respondToAgent(String komentarz) {
        new Actions(driver).moveToElement(poleWprowadzaniaTekstu).click(poleWprowadzaniaTekstu).sendKeys(komentarz)
                .moveToElement(addButton).click(addButton).perform();
        Tools.waitForProcesing(4000);
    }

    public boolean verifyCommentExist(String komentarz) {
        boolean exist = false;
        for (WebElement webElement : listaKomentarzy) {
            if (webElement.getText().contains(komentarz)) {
                exist = true;
                break;
            }
        }
        Tools.waitForProcesing(2000);
        driver.navigate().refresh();
        return exist;
    }

    public boolean isSlaExist() {
        boolean is = driver.findElement(By.xpath("//*[contains(@class,'sla-tag')]")).isDisplayed();
        System.out.println("czy jest element " + is);
        return is;
    }

    public void closeSubstitution() {
        closeSubstButton.click();
        Tools.waitForProcesing(1000);
        //kliknięcie w potwierdzenie zamknięcia zastępstwa
        driver.findElement(By.xpath("html/body/section/form/footer/div/button[1]")).click();
        System.out.println(getStatus());
        wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.CLOSED_SUBSTIITUTON.getStatus()));
        System.out.println(getStatus());

    }

    public String getSecurityApproval() {
        Random random = new Random();
        return securityApprovalUserList.get(random.nextInt(securityApprovalUserList.size() - 1)).getText();
    }


    public String getSupervisor(){
        String supervisor;
        supervisor = supervisorApprovalUser.getText();
        return supervisor;
    }
    //Akceptujemy RFA jako Security i zwracamy Supervisora
    public String approveRFAasSecurity() {
        String supervisor;
        approvedRFA();
        wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.SUPERVISOR_APPROVAL.getStatus()));
        driver.navigate().refresh();
        supervisor = supervisorApprovalUser.getText();
        return supervisor;

    }

    //Akceptujemy RFA jako Supervisor i zwracamy Specialist
    public String[] approveRFAasSupervisor() {
        approvedRFA();
        //ustalamy ile osob musi zaakceptować
        String approvals = driver.findElement(By.xpath("//*[@class[contains(.,'cv-approval-status')]]//h5[text()='Specialists approval']/../p[@class='cv-final-decision']")).getText().substring(0, 1);
        int approvalNeeded = Integer.parseInt(approvals);
        String[] specialist = new String[approvalNeeded];
        for (int i = 0; i < approvalNeeded; i++) {
            int x = (int) (Math.random()* specialistApprovalUserList.size());
            System.out.println("wylosowano : "+x);
            specialist[i] = specialistApprovalUserList.get(x).getText();
        }
        return specialist;
    }

    public void approvedRFA() {
        approveButton.click();
        Tools.waitForProcesing(3000);
    }
}


