package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * This object should contain all the common logic that is shared between both test and page object classes
 */
public class BaseObject
{
    protected WebDriverWait wait;
    protected final int defaultTimeOut = 10;

}
