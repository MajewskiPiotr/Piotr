package Listeners;

import Reports.TestData;
import Tests.BaseTestClass;
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
        String path = "";

        LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
        String currentTimeStamp = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).
                replace("T", "_").replace(":", "-").substring(0, 13);

        if (result.isSuccess()) {

            path = "C:\\Lion_automatyzacja\\Lion\\DaneTestowe\\" +
                    result.getInstance().getClass().getSimpleName() + "\\ScreenShots" + "\\" + currentTimeStamp + "\\PASS_" + result.getMethod().getMethodName() + ".jpg";

        } else {
            path = "C:\\Lion_automatyzacja\\Lion\\DaneTestowe\\" +
                    result.getInstance().getClass().getSimpleName() + "\\ScreenShots" + "\\" + currentTimeStamp + "\\FAIL_" + result.getMethod().getMethodName() + ".jpg";
        }
        System.out.println(path);
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
