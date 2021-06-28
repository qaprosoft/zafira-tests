package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//form[@name='build_now_form']
public class BuildNowForm extends AbstractUIObject {
    @FindBy(id = "modalTitle")
    private ExtendedWebElement modalTitle;

    @FindBy(xpath = ".//label[contains(text(),'branch')]")
    private ExtendedWebElement branchLabel;

    @FindBy(xpath = ".//label[contains(text(),'branch')]/following-sibling::input")
    private ExtendedWebElement branchField;

    @FindBy(xpath = ".//label[contains(text(),'suite')]")
    private ExtendedWebElement suiteLabel;

    @FindBy(xpath = ".//label[contains(text(),'suite')]/following-sibling::input")
    private ExtendedWebElement suiteField;

    @FindBy(id = "build")
    private ExtendedWebElement build;

    public BuildNowForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
