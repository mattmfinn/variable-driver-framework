package examples;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import objects.testobjects.BaseTestObject;

public class FailingMultipleNavigationsTest extends BaseTestObject
{

    @Test(dataProvider = "environments")
    public void failingMultipleNavigationsTest(DesiredCapabilities capabilities)
    {
        driver.get("http://google.com");
        driver.get("http://bing.com");
        driver.get("http://google.com");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://bing.com"));
    }
}
