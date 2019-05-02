package com.qaprosoft.zafira.gui.component.modals;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.Modal;
import com.qaprosoft.zafira.gui.component.other.Calendar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DateRangeModal extends Modal {

    @FindBy(className = "md-date-range-picker__calendar-wrapper")
    private List<Calendar> calendars;

    @FindBy(css = "button.md-primary")
    private ExtendedWebElement submitButton;

    @FindBy(css = "button.md-warn")
    private ExtendedWebElement clearButton;

    public DateRangeModal(WebDriver driver) {
        super(driver);
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public ExtendedWebElement getSubmitButton() {
        return submitButton;
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public ExtendedWebElement getClearButton() {
        return clearButton;
    }

    public void clickClearButton() {
        clearButton.click();
    }

}
