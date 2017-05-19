package PageObjects;

import PageObjects.Base.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by Piotr Majewski on 2017-05-19.
 */
public class AssignePage extends PageObject {
    @FindBy(id = "assignee")
    protected WebElement assigneeList;


    public AssignePage(WebDriver driver){
        super(driver);
    }

    public void chooseAssigne(){
        Select assigneList = new Select(assigneeList);
        assigneList.selectByIndex(3);

    }




}
