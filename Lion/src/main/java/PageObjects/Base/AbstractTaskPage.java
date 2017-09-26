package PageObjects.Base;

import core.ElementsOnPages.Task.TaskButton;
import core.ElementsOnPages.Task.TaskField;
import core.ElementsOnPages.Task.TaskStatus;
import core.Tools.FindInTaskList;
import core.Tools.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-19.
 * KLasa abstracyjna. Kazda Page Task powinien dziedziczyc po tej klasie.
 */


public abstract class AbstractTaskPage extends AbstractJiraPage {


    //Guziki
    @FindBy(xpath = "//*[@id='action_id_5']")
    protected WebElement closeIssueButton;
    @FindBy(id = "comment-issue")
    protected WebElement commentButton;
    @FindBy(id = "opsbar-operations_more")
    protected WebElement moreButton;
    @FindBy(id = "create-linked-issue")
    protected WebElement createLinkedIssueButton;
    @FindBy(id = "edit-issue")
    protected WebElement editButton;
    @FindBy(id = "action_id_851")
    protected WebElement respondToCustomerButton;
    @FindBy(id = "action_id_31")
    protected WebElement doneButton;
    @FindBy(id = "action_id_5")
    protected WebElement resolveIssueButton;
    //Pola
    @FindBy(xpath = "//*[@id='rowForcustomfield_10615']//*[@class='tinylink']/*")
    protected List<WebElement> product;
    @FindBy(xpath = "//*[@id='customfield_10401-val']")
    protected WebElement category;
    @FindBy(xpath = "//*[@id='customfield_10400-val']/div")
    protected List<WebElement> productsAffected;
    @FindBy(xpath = "//*[@class='sla-view-info']/div[text()='Time to resolution']/../div[2]")
    protected WebElement sla;
    @FindBy(xpath = "//*[@id='key-val']")
    protected WebElement taskNr;


    //komentarze i pokrewne
    @FindBy(xpath = "//*[@id='issue-comment-add']//*[@id='comment-wiki-edit']//*[@id='comment']")
    protected WebElement poleWprowadzaniaKomentarza;
    @FindBy(xpath = "//*[@class='action-body flooded']")
    protected List<WebElement> listaKomentarzy;


    //stan Taska
    @FindBy(xpath = "//*[@id='status-val']/span")
    protected WebElement status;

    //Lista zaleznych Issue
    @FindBy(xpath = "//*[@class='links-container']/*/dd//*[@class='link-snapshot']/*")
    protected List<WebElement> relatedIssueList;

    //Konstuktor
    public AbstractTaskPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getKomentarze() {
        List<String> lista = new ArrayList<String>();
        for (WebElement element : listaKomentarzy) {
            String tempKom = element.getText();
            lista.add(tempKom);
        }
        System.out.println(lista.toString());
        return lista;
    }

    public String getTaskNumber() {
        return taskNr.getText();
    }

    public List<String> getProductClass() {
        Tools.waitForProcesing(1000);
        List<String> productClassArray = new ArrayList<>();

        By productclass = new By.ByXPath("//*[@id='rlabs-details']/div/div[5]//*[@class='rlabs-value']");
        if (product.size() > 0) {
            new Actions(driver).moveToElement(product.get(0)).perform();
            String text = driver.findElement(productclass).getText();
            productClassArray.add(text);
        }
        if (productsAffected.size() > 0) {
            driver.navigate().refresh();
            Tools.waitForProcesing(2000);
            for (int i = 0; i < productsAffected.size(); i++) {
                new Actions(driver).moveToElement(productsAffected.get(i)).build().perform();
                //TODO zmienić w przypadku zmiany środowiska
                productClassArray.add(driver.findElement(By.xpath(("//*[@id='customfield_10400-val']/div["+(i+1)+"]"))).getText());
            }
        }
        System.out.println("Tablica " + productClassArray.toString());
        return productClassArray;
    }

    public void clickOnButton(TaskButton button) {

        switch (button) {
            case COMMENT: {
                commentButton.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='comment']")));
                break;
            }
            case DONE: {
                doneButton.click();
                wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.DONE.getStatus()));
                break;
            }
        }
    }

    public String getStatus() {
        if (status.getText() == "") {
            Assert.fail("Nie udało się pobrać Statusu");
        }
        return status.getText();
    }


    public String getTextFromField(TaskField field)  {
        String textfromField = "";
        switch (field) {
            case Product: {
                textfromField = product.get(0).getText();
                break;
            }
            case Category: {
                textfromField = category.getText();
                break;
            }
            case SLA: {
                try {
                    String s = sla.getText();
                    textfromField = s.substring(s.indexOf(" ")).replace(" ", "").replace("h", "");
                    break;
                }
                catch (NoSuchElementException exception){
                    Assert.fail("wybrane produkty nie mają sklasyfikowanego SLA");
                }

            }
        }
        return textfromField;
    }

    public boolean checkIsRelatedIssueIsClosed() {
        boolean close = false;
        for (int i = 1; i <= relatedIssueList.size(); i++) {
            if (FindInTaskList.getStatusFromRelatedIssue(relatedIssueList.get(i - 1), i).getText().equals("RESOLVED")) {
                close = true;
            } else {
                close = false;
                break;
            }

        }
        return close;
    }

    public void resolveIssue() {
        resolveIssueButton.click();
        //wait until system show new screen with close parameters
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='issue-workflow-transition']")));
        //wypełniamy pole (Incident Duration)  w PopUpie DONE
        WebElement incidentDurationInput = driver.findElement(By.xpath("//*[@id='customfield_11002']"));
        incidentDurationInput.sendKeys("10");
        incidentDurationInput.click();
        //klikamy na guzik DONE na otwartym popUpie
        driver.findElement(By.xpath("//*[@id='issue-workflow-transition-submit']")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(status, TaskStatus.RESOLVED.getStatus()));
        driver.navigate().refresh();
    }

}
