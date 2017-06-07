package PageObjects.main;

import PageObjects.Base.PageObject;
import PageObjects.TaskPage.JobTaskPage;
import PageObjects.TaskPage.NegotiationTaskPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-16.
 * KLasa odzwierciedlaj�ca strone zaawansowanego szukania.
 */
public class CurrentSearchPage extends PageObject {

    @FindBy(className = "mode-switcher")
    private WebElement modeswither;

    @FindBy(className = "aui-icon aui-icon-small aui-iconfont-search")
    private WebElement szukaj;

    @FindBy(xpath = "//*[@class='issue-link-key']")
    private List<WebElement> taskList;

    private String taskNumberURL;

    public CurrentSearchPage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/issues");
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(modeswither));
        try {
            driver.findElement(By.xpath("//*[@id='content']//*[@title='Columns']"));
            System.out.println("strona Wyszukiwania źle zostala załadowala");
        } catch (Exception e) {

        }
    }

    //Wyszukuje Taski z Value: UI
    public NegotiationTaskPage searchForNewNegociationTask() {
        //zmiana na wyszukiwania zaawansowane
        if (modeswither.getText().equals("Advanced")) {
            modeswither.click();
        }
        WebElement advanceSearchField = driver.findElement(By.id("advanced-search"));
        advanceSearchField.clear();
        advanceSearchField.sendKeys("issuetype = \"New Work Available\" AND status = New AND cf[10009] = UI" + Keys.ENTER);
<<<<<<< HEAD
        //czekam na wykonanie zapytania

        wait.until(ExpectedConditions.urlContains("browse"));
=======
        //TODO czekanie na wyniki
>>>>>>> SendEmail-feature
        //List<WebElement> lista = driver.findElements(By.xpath("//*[@class='issue-link-key']"));
        WebElement element = taskList.get(0);

        System.out.println("Task negocjacyjny: " + element.getText());
        setTaskNumberURL(baseUrl + "/browse/" + element.getText().trim());
        driver.navigate().to(getTaskNumberURL());
        return new NegotiationTaskPage(driver);
    }

    //metoda wyszukuje najnowszego Taska (TranslationJob)
    @Deprecated//Użyj funkcji generowania Job-ów
    public JobTaskPage searchForNewTransactionJOB() {
        //zmiana na wyszukiwania zaawansowane
        if (modeswither.getText().equals("Advanced")) {
            modeswither.click();
        }
        WebElement advanceSearchField = driver.findElement(By.id("advanced-search"));
        advanceSearchField.clear();
        advanceSearchField.sendKeys("summary ~ Automaty AND issuetype = \"Translation Job\" ORDER BY created" + Keys.ENTER);
        //List<WebElement> lista = driver.findElements(By.xpath("//*[@class='issue-link-key']"));
        wait.until(ExpectedConditions.urlContains("browser"));

        System.out.println("size " + taskList.size());
        WebElement element = taskList.get(0);
        setTaskNumberURL(baseUrl + "/browse/" + element.getText().trim());
        driver.navigate().to(getTaskNumberURL());
        System.out.println("wyszukany task negocjacyjny :" + getTaskNumberURL());
        return new JobTaskPage(driver);
    }

    public String getTaskNumberURL() {
        return taskNumberURL;
    }

    public void setTaskNumberURL(String taskNumber) {
        this.taskNumberURL = taskNumber;
    }
}
