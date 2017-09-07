package core.Listeners;

import Tests.BaseTestClass;

import core.Tools.Configuration.Property;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr Majewski on 2017-05-23.
 */
public class Listeners implements ITestListener {

    List<ITestResult> listabledow = new ArrayList();

    private String getPath(ITestResult result) {
        String basePath = Property.getProperty("basePath");
        String path = "";
        path = basePath + "\\Lion\\DaneTestowe\\" +
                result.getInstance().getClass().getSimpleName() + "\\" + result.getMethod().getMethodName() + ".jpg";
        return path;
    }


    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            BaseTestClass.takeSnapShot(BaseTestClass.getDriver(), getPath(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        listabledow.add(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            BaseTestClass.takeSnapShot(BaseTestClass.getDriver(), getPath(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
        listabledow.add(result);

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
        System.out.println("Result : " + listabledow.toString());
       // RaporPDF raporPDF = new RaporPDF();

       //     raporPDF.create(listabledow);


    }
}
