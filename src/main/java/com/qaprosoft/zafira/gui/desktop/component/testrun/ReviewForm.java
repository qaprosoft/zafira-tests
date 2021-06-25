package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//form[@name='comments_form']
public class ReviewForm extends AbstractUIObject {
    @FindBy(id = "modalTitle")
    private ExtendedWebElement modalTitle;

    @FindBy(xpath = "//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeDialog;

    @FindBy(id = "comment")
    private ExtendedWebElement commentArea;

    @FindBy(id = "markAsReviewed")
    private ExtendedWebElement markAsReviewed;

    public ReviewForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
