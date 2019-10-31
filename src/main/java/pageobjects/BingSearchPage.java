package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BingSearchPage
{

    private final String searchResultPagePartial = "search?";
    private WebDriverWait wait;
    private final int timeOut = 10;


    @FindBy(css = "input[id='sb_form_q']")
    public WebElement searchField;

    @FindBy(css = "input[id='sb_form_go']")
    public WebElement submitButton;

    public BingSearchPage(WebDriver driver)
    {
        this.wait = new WebDriverWait(driver, timeOut);
        PageFactory.initElements(driver, this);
    }

    public void submitSearch(String s)
    {
        searchField.sendKeys(s);
        submitButton.click();
        wait.until(ExpectedConditions.urlContains(searchResultPagePartial));
    }
}
