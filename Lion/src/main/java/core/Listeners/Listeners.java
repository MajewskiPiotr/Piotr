package core.Listeners;

import core.Reports.TestData;
import Tests.BaseTestClass;
import core.Tools.Configuration.Property;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by Piotr Majewski on 2017-05-23.
 */
public class Listeners implements ITestListener {

    private String getPath(ITestResult result) {
        String basePath = Property.getProperty("basePath");

        String path = "";
        LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
        String currentTimeStamp = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).
                replace("T", "_").replace(":", "-").substring(0, 13);

        if (result.isSuccess()) {
            path = basePath + "\\Lion\\DaneTestowe\\" +
                    result.getInstance().getClass().getSimpleName() + "\\ScreenShots" + "\\" + currentTimeStamp + "\\PASS_" + result.getMethod().getMethodName() + ".jpg";

        } else {
            path = basePath + "\\Lion\\DaneTestowe\\" +
                    result.getInstance().getClass().getSimpleName() + "\\ScreenShots" + "\\" + currentTimeStamp + "\\Fail_" + result.getMethod().getMethodName() + ".jpg";
        }
        return path;
    }


    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            BaseTestClass.takeSnapShot(BaseTestClass.getDriver(), getPath(result));
            TestData.saveTestData(BaseTestClass.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
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
