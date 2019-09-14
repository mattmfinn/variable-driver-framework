package testobjects;

import capabilities.CapabilitiesFactory;
import com.saucelabs.saucerest.SauceREST;
import enums.Saucelabs;
import environment.EnvironmentParser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseWebTestObject
{

    public RemoteWebDriver driver;
    public static final String URL = "http://" + Saucelabs.USERNAME.value() + ":"
            + Saucelabs.ACCESS_KEY.value() + "@ondemand.saucelabs.com:80/wd/hub";
    public static final SauceREST sauceREST = new SauceREST(Saucelabs.USERNAME.value(), Saucelabs.ACCESS_KEY.value());

    @DataProvider(name = "environments")
    public static Object[] environments()
    {
        EnvironmentParser environmentParser = new EnvironmentParser();
        CapabilitiesFactory capabilitiesFactory = new CapabilitiesFactory(environmentParser);
        return capabilitiesFactory.makeCapabilities("./src/test/resources/webEnvironments.json");
    }

    @BeforeMethod
    public void setUp(Method method, Object[] capabilities) throws MalformedURLException
    {
        // Name the test in Saucelabs and create the driver
        for(int i = 0; i < capabilities.length; i++)
        {
            DesiredCapabilities desiredCapabilities = (DesiredCapabilities) capabilities[i];
            desiredCapabilities.setCapability("name", method.getName());
            driver = new RemoteWebDriver(new URL(URL), (DesiredCapabilities) capabilities[i]);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result)
    {
        String jobID = getSessionId();
        driver.quit();
        if(result.isSuccess()) sauceREST.jobPassed(jobID);
        else sauceREST.jobFailed(jobID);
    }

    public String getSessionId()
    {
        return driver.getSessionId().toString();
    }
}
