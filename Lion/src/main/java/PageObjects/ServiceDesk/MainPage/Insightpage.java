package PageObjects.ServiceDesk.MainPage;

import PageObjects.Base.AbstractJiraPage;
import core.ElementsOnPages.Task.TaskButton;
import core.Tools.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-13.
 */
public class Insightpage extends AbstractJiraPage {

    //linki w lewym menu
    @FindBy(xpath = "//*[@id='rlabs_jstree_7']/a")
    private WebElement sla;

    //guzik zmieniający szukanie w Insight na Advanced
    @FindBy(xpath = "//*[@id='rlabs-advancedsearch-switcher-item-to-advanced']")
    private WebElement advancedButton;

    @FindBy(xpath = "//*[@id='rlabs-advanced-search-input']")
    private WebElement jqlInput;

    @FindBy(xpath = "//*[@id='rlabs_jstree_30']/a")
    private WebElement substitutions;

    @FindBy(xpath = "//*[@id='rlabs-object-list-table-values']/*")
    private List<WebElement> matrix;


    public Insightpage(WebDriver driver, String insightObjectSchema) {
        super(driver);
        switch (insightObjectSchema) {
            case "Substitutions": {
                driver.navigate().to(baseUrl + "/secure/ObjectSchema.jspa?id=7");
            }
            case "Enova": {
                driver.navigate().to(baseUrl + "/secure/ObjectSchema.jspa?id=7&typeId=31&view=list&objectId=1305");

            }

        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='rlabs-actions-object-filter-button']")));
    }

    public Insightpage(WebDriver driver) {
        super(driver);
        driver.navigate().to(baseUrl + "/secure/ObjectSchema.jspa?id=1");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='rlabs-actions-object-filter-button']")));
    }

    @Override
    public void clickOnButton(TaskButton button) {
        switch (button) {
            case SLA: {
                sla.click();
                break;
            }
            case SUBSTITUTIONS: {
                substitutions.click();
                break;
            }
        }
    }

    public void switchViewToList() {
        driver.navigate().to(getUrl() + "&view=list");
    }


    //Funckcja na podstawie parametrów wyszukuje najbardziej restrykcyjne SLA
    public int findSolutionTime(List<String> productClassList, String category) {
        int minSlaTime = 1000000;
        List<WebElement> tempList = new ArrayList<>();

        //filtrowanie macierz po categorii
        int i = 1;
        for (WebElement element : matrix) {
            String xpathForCategory = "((//*[@id='rlabs-object-list-table-values']/*)[" + i + "]//*[@class='rlabs-value-container']/a)[2]";
            WebElement badany = driver.findElement(By.xpath(xpathForCategory));
            if (badany.getText().equals(category)) {
                tempList.add(element);
            }
            i++;
        }

        //z przefiltrowanej listy wybieramy szukam ProductClass z najkrótszym czasem obsługi
        for (WebElement webElement : tempList) {
            //przeszukuje liste w poszukiwaniu pozycji zgodnej z przekazaną listą
            if (productClassList.contains(webElement.findElement(By.className("rlabs-value-container")).findElement(By.xpath(".//a")).getText())) {
                String badaneSLA = webElement.findElement(By.className("js_tooltip")).getText();
                String jednostka = badaneSLA.substring(badaneSLA.indexOf(" ")).replace(" ", "");
                int timeFromString = Integer.parseInt(badaneSLA.substring(0, badaneSLA.indexOf(" ")));
                int thisTime = okreslWartoscSLA(timeFromString, jednostka);
                if (thisTime < minSlaTime) {
                    minSlaTime = thisTime;
                }

            }
        }

        return minSlaTime;
    }

    public String findSubstitution(String substTask) {
        switchToAdvanced();

        jqlInput.sendKeys(" \"Name\" = " + substTask + Keys.ENTER);
        Tools.waitForProcesing(3000);
        System.out.println(getMatrix().size());
        if (getMatrix().size() != 1) {
            Assert.fail("Ilość zastęps wynosi :" + getMatrix().size());
        }
        setAllCoumns();
        WebElement status = driver.findElement(By.xpath("//*[@id[contains(.,'rlabs-object')]]/div[8]/div"));
        return status.getText();
    }

    //funkcja pozawala na porównywanie dni z godzinami
    private int okreslWartoscSLA(int czas, String jednostka) {
        if (jednostka.equals("days")) {
            return czas * 24;
        } else {
            return czas;
        }
    }

    //ustawiamy widoczność wszystkich kolumn w Insight
    private void setAllCoumns() {
        driver.findElement(By.xpath("//*[@id='rlabs-columns-button']")).click();
        driver.findElement(By.xpath("//*[@id='rlabs-columns-select-all']")).click();
        driver.findElement(By.xpath("//*[@id='rlabs-columns-close']")).click();
        Tools.waitForProcesing(2000);
    }

    //funkcja zwraca tablice elementów  z INSIGHTA
    private List<WebElement> getMatrix() {

        return matrix;
    }

    public String findSupervisor(String user) {
        switchViewToList();
        switchToAdvanced();
        setColumnSupervisor();

        jqlInput.sendKeys("\"Name\" =(\"" + user + "\")" + Keys.ENTER);
        Tools.waitForProcesing(2000);
        if (getMatrix().size() != 1) {
            Assert.fail("Ilość userow wynosi :" + getMatrix().size());
        }

        WebElement supervisorID = driver.findElement(By.xpath("//*[@id[contains(.,'rlabs-object')]]/div[7]/div/span"));
        jqlInput.clear();
        jqlInput.sendKeys("\"NR_EW\" =(\"" + supervisorID.getText() + "\")" + Keys.ENTER);
        if (getMatrix().size() != 1) {
            Assert.fail("Ilość supervisorow wynosi :" + getMatrix().size());
        }
        Tools.waitForProcesing(1000);
        WebElement supervisorName = driver.findElement(By.xpath("(//*[@id[contains(.,'object-name')]])[2]"));
        Tools.waitForProcesing(2000);
        return supervisorName.getText();
    }

    private void setColumnSupervisor() {
        driver.findElement(By.xpath("//*[@id='rlabs-columns-button']")).click();

        driver.findElement(By.xpath("//*[@id='rlabs-columns-label-SupervisorEnovaId']/span")).click();

        driver.findElement(By.xpath("//*[@id='rlabs-columns-close']")).click();
        Tools.waitForProcesing(2000);
    }

    private void switchToAdvanced() {
        if (advancedButton.isEnabled()) {
            advancedButton.click();
        }
    }
}
