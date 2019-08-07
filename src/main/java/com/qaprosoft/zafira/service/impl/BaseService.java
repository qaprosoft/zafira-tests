package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.gui.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class BaseService<T extends BasePage> {

    protected final WebDriver driver;
    protected final Class<T> pageClass;
    protected T page;

    public BaseService(WebDriver driver, Class<T> pageClass) {
        this(driver, null, pageClass);
    }

    public BaseService(WebDriver driver, T page, Class<T> pageClass) {
        this.driver = driver;
        this.page = page;
        this.pageClass = pageClass;
    }

    public void setPage(T page) {
        this.page = page;
    }

    @SuppressWarnings("unchecked")
    public T getUIObject(WebDriver driver, Object... constructorArgs) {
        T result = null;
        if(page == null) {
            Constructor<?> ctor;
            try {
                if(constructorArgs.length == 0) {
                    ctor = pageClass.getConstructor(WebDriver.class);
                    result = (T) ctor.newInstance(driver);
                } else {
                    Class[] constructorArgTypes = Arrays.stream(constructorArgs).map(Object::getClass).toArray(Class[]::new);
                    ctor = pageClass.getConstructor(constructorArgTypes);
                    result = (T) ctor.newInstance(constructorArgs);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return page == null ? result : page;
    }

    public void waitProgressLinear() {
        T page = getUIObject(driver);
        if (page.getProgressLinear().isElementPresent(1)) {
            Wait<WebDriver> wait = new WebDriverWait(driver, 15, 100);
            wait.until(ExpectedConditions.invisibilityOf(page.getProgressLinear().getElement()));
        }
    }

}
