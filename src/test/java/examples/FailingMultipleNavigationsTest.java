package examples;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import testobjects.BaseWebTestObject;

import java.net.MalformedURLException;

public class FailingMultipleNavigationsTest extends BaseWebTestObject
{

    @Test(dataProvider = "environments")
    public void failingMultipleNavigationsTest(DesiredCapabilities capabilities) throws InterruptedException, MalformedURLException
    {
        driver.get("http://google.com");
        driver.get("http://bing.com");
        driver.get("http://google.com");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://bing.com"));
    }
}
