package objects.pageobjects.external;

import objects.pageobjects.BasePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BingSearchPage extends BasePageObject
{

    private final String searchResultPagePartial = "search?";

    @FindBy(css = "input[id='sb_form_q']")
    public WebElement searchField;

    @FindBy(css = "label[class='search icon tooltip']")
    public WebElement submitButton;

    public BingSearchPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(defaultTimeOut));
    }

    public void submitSearch(String s)
    {
        searchField.sendKeys(s);
        submitButton.click();
        wait.until(ExpectedConditions.urlContains(searchResultPagePartial));
    }
}
