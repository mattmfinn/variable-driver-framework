package examples;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import objects.pageobjects.external.BingSearchPage;
import objects.testobjects.BaseTestObject;

public class ValidateSearchTest extends BaseTestObject
{
    private final String searchTerm = "www.theconstantvariable.com";
    private final String title = "The Constant Variable";

    @Test(dataProvider = "environments")
    public void validateSearchTest(DesiredCapabilities capabilities)
    {
        driver.get("http://bing.com");
        BingSearchPage bingSearchPage = new BingSearchPage(driver);
        bingSearchPage.submitSearch(searchTerm);
        driver.findElementByLinkText(title).click();
        wait.until(ExpectedConditions.titleIs(title));
        Assert.assertTrue(driver.getCurrentUrl().contains(searchTerm));
    }
}
