package com.qaprosoft.zafira.gui.component.search;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.util.CommonUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunSearchBlock extends AbstractUIObject {

    @FindBy(name = "testRunSearchCheckbox")
    private ExtendedWebElement mainCheckbox;

    @FindBy(className = "tests-runs-search__input-field")
    private ExtendedWebElement querySearchInput;

    @FindBy(className = "reviewed")
    private ExtendedWebElement reviewedButton;

    @FindBy(id = "searchStatus")
    private ExtendedWebElement statusSelect;

    @FindBy(id = "searchEnv")
    private ExtendedWebElement environmentSelect;

    @FindBy(id = "searchPlatform")
    private ExtendedWebElement platformSelect;

    @FindBy(id = "searchCalendar")
    private ExtendedWebElement datePickerButton;

    @FindBy(className = "tests-runs-search__reset-btn")
    private ExtendedWebElement resetButton;

    public TestRunSearchBlock(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getMainCheckbox() {
        return mainCheckbox;
    }

    public void clickMainCheckbox() {
        mainCheckbox.click();
    }

    public ExtendedWebElement getQuerySearchInput() {
        return querySearchInput;
    }

    public void typeSearchQuery(String query) {
        querySearchInput.type(query);
    }

    public ExtendedWebElement getReviewedButton() {
        return reviewedButton;
    }

    public void clickReviewedButton() {
        reviewedButton.click();
    }

    public ExtendedWebElement getStatusSelect() {
        return statusSelect;
    }

    public void selectStatus(WebDriver driver, String value) {
        CommonUtils.select(statusSelect, driver, value);
    }

    public ExtendedWebElement getEnvironmentSelect() {
        return environmentSelect;
    }

    public void selectEnvironment(WebDriver driver, String value) {
        CommonUtils.select(environmentSelect, driver, value);
    }

    public ExtendedWebElement getPlatformSelect() {
        return platformSelect;
    }

    public void selectPlatform(WebDriver driver, String value) {
        CommonUtils.select(platformSelect, driver, value);
    }

    public ExtendedWebElement getDatePickerButton() {
        return datePickerButton;
    }

    public void clickDatePickerButton() {
        datePickerButton.click();
    }

    public ExtendedWebElement getResetButton() {
        return resetButton;
    }

    public void clickResetButton() {
        resetButton.click();
    }

}
