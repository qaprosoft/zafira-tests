package com.qaprosoft.zafira.gui.message;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InputMessage extends AbstractUIObject {

    @FindBy(xpath = "./following-sibling::*[@ng-messages]/*[@ng-message]/*")
    private List<ExtendedWebElement> messages;

    public InputMessage(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public List<ExtendedWebElement> getMessages() {
        return messages;
    }

}
