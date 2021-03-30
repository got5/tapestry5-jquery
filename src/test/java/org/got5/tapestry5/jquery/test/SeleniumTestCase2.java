package org.got5.tapestry5.jquery.test;

import org.apache.tapestry5.test.SeleniumTestCase;
import org.apache.tapestry5.test.TapestryTestConfiguration;
import org.got5.tapestry5.jquery.JQueryTestConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

@TapestryTestConfiguration(webAppFolder = "src/test/webapp")
public class SeleniumTestCase2 extends SeleniumTestCase
{
    protected WebElement findFirstVisible(WebDriver webDriver, By by)
    {
        return webDriver.findElements(by)
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Unable to find visible element by " + by));
    }

    protected void click(WebElement element)
    {
        this.scrollIntoView(element);
        JavascriptExecutor executor = (JavascriptExecutor)this.webDriver;
        executor.executeScript("arguments[0].click();", element);
    }

    protected void click(By locator)
    {
        click(webDriver.findElement(locator));
    }

    protected void clickAndWait(WebElement element)
    {
        click(element);
        waitForPageToLoad();
    }

    protected void assertNotPresent(By locator, String message)
    {
        assertNotPresent(locator, __ -> true, message);
    }

    protected void assertNotPresent(By locator, Predicate<WebElement> filter, String message)
    {
        List<WebElement> elements = webDriver.findElements(locator).stream().filter(filter).collect(toList());
        assertEquals(elements, emptyList(), message);
    }

    protected void waitForCondition(ExpectedCondition<?> condition, String message)
    {
        try
        {
            super.waitForCondition(condition, JQueryTestConstants.TIMEOUT / 1000);
        }
        catch (TimeoutException e)
        {
            throw new TimeoutException(message, e);
        }
    }

    @Override
    public void open(String url)
    {
        boolean absoluteUrl = startsWithIgnoreCase(url, "http://") || startsWithIgnoreCase(url, "https://");

        if (!absoluteUrl)
        {
            url = getBaseURL() + (url.startsWith("/") ? url.substring(1) : url);
        }

        webDriver.navigate().to(url);

        waitForPageInitialized(Duration.ofMillis(JQueryTestConstants.TIMEOUT));
    }

    protected void waitForPageInitialized(Duration timeout)
    {
        long startTime = System.currentTimeMillis();

        waitForDocumentReady(timeout);

        // In a limited number of cases, a "page" is an container error page or raw HTML content
        // that does not include the body element and data-page-initialized element. In those cases,
        // there will never be page initialization in the Tapestry sense and we return immediately.
        try
        {
            WebElement body = webDriver.findElement(By.cssSelector("body"));

            //  Not a tapestry page?
            if (body.getAttribute("data-page-initialized") == null)
            {
                return;
            }

            long timePassed = System.currentTimeMillis() - startTime;

            Duration timeLeft = Duration.ofMillis(timeout.toMillis() - timePassed);

            waitForVisible(By.cssSelector("body[data-page-initialized='true']"), timeLeft);
        }
        catch (NoSuchElementException e)
        {
            // no body element found, there's nothing to wait for
        }
    }

    protected void waitForVisible(By locator, Duration timeout)
    {
        waitForCondition(ExpectedConditions.visibilityOfElementLocated(locator), timeout.getSeconds());
    }

    private void waitForDocumentReady(Duration timeout)
    {
        new WebDriverWait(webDriver, timeout.getSeconds())
                .until(webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }

    protected boolean isEnabled(By locator)
    {
        return webDriver.findElement(locator).isEnabled();
    }

    protected void focus(By locator)
    {
        new Actions(webDriver).moveToElement(webDriver.findElement(locator));
    }

    @Override
    public void focus(String locator)
    {
        focus(convertLocator(locator));
    }

    @Override
    public void keyDown(String locator, String keySequence)
    {
        WebElement element = webDriver.findElement(convertLocator(locator));
        Actions actions = new Actions(webDriver);

        keySequence.chars().forEach(ch ->
                actions
                        .keyDown(element, Keys.getKeyFromUnicode((char) ch))
                        .perform());
    }


    @Override
    public void keyUp(String locator, String keySequence)
    {
        WebElement element = webDriver.findElement(convertLocator(locator));
        Actions actions = new Actions(webDriver);

        keySequence.chars().forEach(ch ->
                actions
                        .keyUp(element, Keys.getKeyFromUnicode((char) ch))
                        .perform());
    }
}