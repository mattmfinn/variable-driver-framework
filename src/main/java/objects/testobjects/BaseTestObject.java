package objects.testobjects;

import capabilities.CapabilitiesFactory;
import com.saucelabs.saucerest.SauceREST;
import enums.Saucelabs;
import environment.EnvironmentParser;
import objects.BaseObject;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.Duration;

public class BaseTestObject extends BaseObject
{

    public RemoteWebDriver driver;
    public static final SauceREST sauceREST = new SauceREST(Saucelabs.USERNAME.value, Saucelabs.ACCESSKEY.value);
    // We need to access the Options object within the EnvironmentParser class
    private static final EnvironmentParser environmentParser = new EnvironmentParser();

    @DataProvider(name = "environments")
    public static Object[] environments()
    {
        // Set the options - we read from options.json to see the boolean value of 'headless' and 'remote'
        environmentParser.setOptions();
        if(environmentParser.options.remote && environmentParser.options.headless)
        {
            CapabilitiesFactory capabilitiesFactory = new CapabilitiesFactory(environmentParser);
            return capabilitiesFactory.makeCapabilities("./src/test/resources/saucelabsEnvironments.json");
        }
        else
        {
            // TODO: We can return more meaningful values here later, allowing multiple browsers
            return new DesiredCapabilities[1];
        }
    }

    @BeforeMethod
    public void setUp(Method method, Object[] capabilities) throws MalformedURLException, UnknownHostException
    {
        // If we are local, but headless
        if (!environmentParser.options.remote)
        {
            // TODO: Pass the location of the gecko driver via gradle command
            System.setProperty("webdriver.gecko.driver", "/home/matt/gecko-driver/geckodriver");
            FirefoxBinary firefoxBinary = new FirefoxBinary();

            // If we are headless, set that option
            if(environmentParser.options.headless)
            {
                firefoxBinary.addCommandLineOptions("--headless");
            }

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            driver = new FirefoxDriver(firefoxOptions);
        }
        // We will use the method name to name the test, only if we are running remotely
        // Only runs if we are not headless and are remote
        else if(!environmentParser.options.headless && environmentParser.options.remote)
        {
            for (int i = 0; i < capabilities.length; i++)
            {
                DesiredCapabilities desiredCapabilities = (DesiredCapabilities) capabilities[i];
                desiredCapabilities.setCapability("name", method.getName());
                driver = new RemoteWebDriver(new URL(Saucelabs.URL.value), (DesiredCapabilities) capabilities[i]);
            }
        }
        else
        {
            throw new IllegalArgumentException("Both the 'headless' and 'remote' cannot be set to 'true'. Remote" +
                    "services do not support headless testing. Please adjust the options file specified for" +
                    "the EnvironmentParser object.");
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(defaultTimeOut));
    }

    @AfterMethod
    public void tearDown(ITestResult result)
    {
        String jobID = driver.getSessionId().toString();
        driver.quit();
        if(environmentParser.options.remote)
        {
            if (result.isSuccess()) sauceREST.jobPassed(jobID);
            else sauceREST.jobFailed(jobID);
        }
    }
}
