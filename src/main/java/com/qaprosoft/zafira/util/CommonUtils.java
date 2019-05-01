package com.qaprosoft.zafira.util;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CommonUtils {

    public static String getInputText(ExtendedWebElement input) {
        return input.getAttribute("value");
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

    public static void waitUntilDocumentIdReady(WebDriver driver) {
        Wait<WebDriver> wait = new WebDriverWait(driver, 2);
        wait.until(dr -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

}
