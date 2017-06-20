package PageObjects.ServiceDesk.MainPage;

import PageObjects.Base.AbstractJiraPage;
import core.ElementsOnPages.Task.TaskButton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-06-13.
 */
public class Insightpage extends AbstractJiraPage {
    //linki w lewym menu
    @FindBy(xpath = "//*[@id='rlabs_jstree_7']/a")
    private WebElement sla;

    @FindBy(xpath = "//*[@id='rlabs-object-list-table-values']/*")
    private List<WebElement> matrix;




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
        }
    }

    public void switchViewToList() {
        driver.navigate().to(getUrl() + "&view=list");
    }

    //funkcja zwraca tablice elementów SLA z INSIGHTA
    public List<WebElement> getMatrix() {

        return matrix;
    }

    //Funckcja na podstawie parametrów wyszukuje najbardziej restrykcyjne SLA
    public int findSolutionTime(List<String> productClassList, String category) {
        int minSlaTime=10000;
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
                if (thisTime<minSlaTime){
                    minSlaTime=thisTime;
                }

            }
        }

        return minSlaTime;
    }

//funkcja pozawala na porównywanie dni z godzinami
    private int okreslWartoscSLA(int czas, String jednostka) {
        if (jednostka.equals("days")) {
            return czas*24;
        } else {
            return czas ;
        }
    }

}
