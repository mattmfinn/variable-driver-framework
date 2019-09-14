package examples;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import testobjects.BaseWebTestObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MultipleNavigationsTest extends BaseWebTestObject
{

    @Test(dataProvider = "environments")
    public void multipleNavigationsTest(DesiredCapabilities capabilities) throws InterruptedException, MalformedURLException
    {
        driver.get("http://google.com");
        Thread.sleep(5000);
        driver.get("http://bing.com");
        Thread.sleep(5000);
        driver.get("http://google.com");
    }
}
