package examples;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import pageobjects.BingSearchPage;
import testobjects.BaseWebTestObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ValidateSearchTest extends BaseWebTestObject
{
    private final String searchTerm = "www.theconstantvariable.com";
    private final String title = "The Constant Variable";
    private WebDriverWait wait;
    private final int timeOut = 10;

    @Test(dataProvider = "environments")
    public void validateSearchTest(DesiredCapabilities capabilities)
    {
        wait = new WebDriverWait(driver, timeOut);
        driver.get("http://bing.com");
        BingSearchPage bingSearchPage = new BingSearchPage(driver);
        bingSearchPage.submitSearch(searchTerm);
        driver.findElementByLinkText(title).click();
        wait.until(ExpectedConditions.titleIs(title));
        Assert.assertTrue(driver.getCurrentUrl().contains(searchTerm));
    }
}
