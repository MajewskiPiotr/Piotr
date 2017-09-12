package Tests;

import org.testng.annotations.Test;

/**
 * Created by Piotr Majewski on 2017-08-22.
 */
public class testClass {

    @Test
    public void test1(int number) {
        System.out.println("test1 "+ number);
    }

    @Test(groups = {"smoke"})
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

    @Test
    public void test4() {
        System.out.println("test4");
    }

    @Test(groups = {"smoke"})
    public void test5() {
        System.out.println("test5");
    }

    @Test
    public void test6() {
        System.out.println("test6");
    }

}
