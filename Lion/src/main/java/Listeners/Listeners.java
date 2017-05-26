package Listeners;

import Reports.Report;
import Reports.TestData;
import Tests.BaseTestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Created by Piotr Majewski on 2017-05-23.
 */
public class Listeners implements ITestListener {

    private String getPath(ITestResult result) {
        return Report.getPath(result);
    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            BaseTestClass.takeSnapShot(BaseTestClass.getDriver(), getPath(result));

            TestData.saveTestData(BaseTestClass.getData());

            System.out.println(BaseTestClass.getData().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            System.out.println("fail");
            BaseTestClass.takeSnapShot(BaseTestClass.getDriver(), getPath(result));
            TestData.saveTestData(BaseTestClass.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
