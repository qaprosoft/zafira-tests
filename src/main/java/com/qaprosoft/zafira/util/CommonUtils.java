package com.qaprosoft.zafira.util;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CommonUtils {

    public static String getInputText(ExtendedWebElement input) {
        return input.getAttribute("value");
    }

    public static String getSelectValue(ExtendedWebElement select) {
        ExtendedWebElement valueLabel = select.findExtendedWebElement(By.xpath(".//md-select-value/span"));
        return valueLabel.getText();
    }

    public static void pastTo(ExtendedWebElement element, WebDriver driver) {
        String osName = System.getProperty("os.name");
        Keys ctrlKey = osName.toLowerCase().contains("mac") ? Keys.COMMAND : Keys.CONTROL;
        Actions action = new Actions(driver);
        action.sendKeys(Keys.chord(ctrlKey, "v")).build().perform();
    }

    public static String getStringFromBuffer() {
        String result = null;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        try {
            result = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void hoverAndClickOn(ExtendedWebElement hoverElement, ExtendedWebElement clickElement, WebDriver driver) {
        Actions action = new Actions(driver);
        action.moveToElement(hoverElement.getElement())
              .moveToElement(clickElement.getElement())
              .click()
              .build()
              .perform();
        action.release().build().perform();
    }

    public static void hoverOn(ExtendedWebElement element, WebDriver driver) {
        Actions action = new Actions(driver);
        action.moveToElement(element.getElement())
              .build()
              .perform();
    }

    public static Actions clickAndHold(ExtendedWebElement element, WebDriver driver) {
        Actions action = new Actions(driver);
        action.clickAndHold(element.getElement()).build().perform();
        return action;
    }

    public static void releaseAction(Actions action, WebDriver driver) {
        action.release().build().perform();
    }

    public static void clickOutside(WebDriver driver) {
        WebElement body = driver.findElement(By.tagName("body"));
        clickByCoordinates(body, driver, 0, 0);
    }

    public static void clickByCoordinates(WebElement element, WebDriver driver, int x, int y) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element, x, y).click().build().perform();
    }

    public static void select(ExtendedWebElement element, WebDriver driver, String value) {
        element.click();
        WebElement valuesContainer = getMenuValuesContainer(element, driver);
        WebElement valueElement = valuesContainer.findElement(By.xpath(".//md-option[contains(translate(normalize-space(@value), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + value.toLowerCase() + "')]"));
        valueElement.click();
    }

    public static void clickMenuItem(ExtendedWebElement element, WebDriver driver, String value) {
        WebElement valuesContainer = getMenuValuesContainer(element, driver);
        WebElement valueElement = valuesContainer.findElement(By.xpath(".//*[contains(translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + value.toLowerCase() + "')]"));
        element.pause(1);
        valueElement.click();
    }

    public static <T> T waitForCompletableFuture(CompletableFuture<T> completableFuture) {
        T result = null;
        try {
            result = completableFuture.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean check(ExtendedWebElement element) {
        boolean isChecked = isChecked(element);
        boolean result = !isChecked;
        if (result) {
            element.click();
        }
        return result;
    }

    public static boolean uncheck(ExtendedWebElement element) {
        boolean result = isChecked(element);
        if (result) {
            element.click();
        }
        element.scrollTo();
        return result;
    }

    public static void scrollTop(WebDriver driver) {
        WebElement element = driver.findElement(By.className("page"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void scrollBottom(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static boolean isChecked(ExtendedWebElement element) {
        return Boolean.parseBoolean(element.getAttribute("aria-checked"));
    }

    private static WebElement getMenuValuesContainer(ExtendedWebElement element, WebDriver driver) {
        element.pause(2);
        String containerId = element.getAttribute("aria-owns");
        return driver.findElement(By.id(containerId));
    }

    public static void setAuthorizationCookie(WebDriver driver, String authToken) {
        Cookie cookie = new Cookie("Authorization", authToken, "127.0.0.1", "/", null);
        driver.manage().addCookie(cookie);
    }

    public static String getAuthorizationCookie(WebDriver driver) {
        Cookie cookie = driver.manage().getCookieNamed("Authorization");
        return cookie == null ? null : cookie.getValue();
    }

}
