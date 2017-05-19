package PageObjects.TaskPage;

import Elements.Task.TaskTab;
import PageObjects.Base.PageObject;
import PageObjects.TaskPage.TaskPage_Tab.AssigmentsTabPage;

/**
 * Created by Piotr Majewski on 2017-05-18.
 */
public interface ITaskTab {

    PageObject goToTab(TaskTab tab);
}
