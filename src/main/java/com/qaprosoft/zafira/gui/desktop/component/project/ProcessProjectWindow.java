package com.qaprosoft.zafira.gui.desktop.component.project;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

//md-dialog[contains(@class,'project-modal')]
public class ProcessProjectWindow extends AbstractUIObject {
    @FindBy(xpath = "//h2[@class='modal-header__title ng-binding ng-scope']")
    private ExtendedWebElement title;

    @FindBy(id = "close")
    private ExtendedWebElement closeButton;

    @FindBy(xpath = ".//div[contains(@class,'profile-photo_content')]")
    private ExtendedWebElement projectImg;

    @FindBy(xpath = ".//input[contains(@name,'projectName')]")
    private ExtendedWebElement projectNameField;

    @FindBy(xpath = ".//input[contains(@name,'projectKey')]")
    private ExtendedWebElement projectKeyField;

    @FindBy(xpath = ".//md-select-value[contains(@class,'md-select-value')]")
    private ExtendedWebElement selectLeadMenu;

    @FindBy(xpath = ".//md-option[contains(@ng-value,'userObject')]")
    private List<ExtendedWebElement> leadOptionList;

    @FindBy(xpath = ".//md-switch[contains(@name,'projectVisibility')]")
    private ExtendedWebElement projectVisibilitySwitcher;

    @FindBy(xpath = ".//span[@class='button__text']")
    private ExtendedWebElement projectMembersButton;

    @FindBy(xpath = ".//span[text()='delete']/ancestor::button")
    private ExtendedWebElement deleteProjectButton;

    @FindBy(xpath = ".//span[text()='cancel']/ancestor::button")
    private ExtendedWebElement cancelProcessButton;

    @FindBy(xpath = ".//span[text()='save' or text()='create']/ancestor::button")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = ".//h2[@class='modal-header__title _warning ng-scope']")
    private ExtendedWebElement deleteWarningTitle;

    @FindBy(xpath = ".//div[@class='modal-content__message _warning']")
    private ExtendedWebElement deleteRedWarningMsg;

    @FindBy(xpath = ".//div[@class='modal-content__message']")
    private ExtendedWebElement deleteWhiteWarningMsg;

    @FindBy(xpath = ".//md-icon[@aria-label='warning']")
    private ExtendedWebElement deleteIcon;

    public ProcessProjectWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isProjectPublic() {
        return Boolean.parseBoolean(projectVisibilitySwitcher.getAttribute("aria-checked").trim());
    }

    public void closeWindow() {
        waitUntil(ExpectedConditions.elementToBeClickable(closeButton.getElement()), WebConstant.TIME_TO_LOAD_PAGE);
        closeButton.click();
    }

    public String getTitle() {
        return title.getText().trim();
    }

    public boolean isProjectLogoPresent() {
        return projectImg.isVisible(WebConstant.TIME_TO_LOAD_ELEMENT);
    }

    public boolean isProjectNameFieldPresent() {
        return projectNameField.isVisible(WebConstant.TIME_TO_LOAD_ELEMENT);
    }

    public boolean isProjectVisibilitySwitcherPresent() {
        return projectVisibilitySwitcher.isVisible(WebConstant.TIME_TO_LOAD_ELEMENT);
    }

    public void changeProjectVisibility() {
        projectVisibilitySwitcher.click(WebConstant.TIME_TO_LOAD_ELEMENT);
    }

    public boolean isKeyFieldPresent() {
        return projectKeyField.isVisible(WebConstant.TIME_TO_LOAD_ELEMENT);
    }

    public boolean isSubmitButtonActive() {
        waitUntil(ExpectedConditions.visibilityOf(submitButton.getElement()), WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        return submitButton.isClickable(WebConstant.TIME_TO_LOAD_ELEMENT)
                && submitButton.isVisible(WebConstant.TIME_TO_LOAD_ELEMENT);
    }

    public boolean isCloseButtonPresent() {
        return closeButton.isVisible() && closeButton.isClickable();
    }

    public boolean isCancelButtonPresent() {
        return cancelProcessButton.isVisible() && cancelProcessButton.isClickable();
    }

    public void typeProjectName(String name) {
        waitUntil(ExpectedConditions.visibilityOf(projectNameField.getElement()), WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        projectNameField.type(name);
    }

    public void typeProjectKey(String key) {
        waitUntil(ExpectedConditions.visibilityOf(projectKeyField.getElement()), WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        projectKeyField.type(key);
    }

    public TestRunsPage clickCreateButton() {
        waitUntil(ExpectedConditions.visibilityOf(submitButton.getElement()), WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        submitButton.click();
        return new TestRunsPage(driver);
    }

    public void clickSaveButton(){
        waitUntil(ExpectedConditions.visibilityOf(submitButton.getElement()), WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        submitButton.click();
    }

    public boolean deleteProject() {
        deleteProjectButton.clickIfPresent();
        pause(WebConstant.TIME_TO_LOAD_ELEMENT);
        return deleteProjectButton.clickIfPresent();
    }

    public boolean isSelectLeadMenuPresent(){
        return selectLeadMenu.isVisible() && selectLeadMenu.isClickable();
    }

    public boolean isProjectMembersButtonPresent(){
        return projectMembersButton.isVisible() && projectMembersButton.isClickable();
    }

    public boolean isDeleteButtonPresent(){
        return deleteProjectButton.isVisible() && deleteProjectButton.isClickable();
    }

    public void clickDeleteButton(){
        deleteProjectButton.click();
    }

    public String getDeleteWarningTitleText(){
        String title = deleteWarningTitle.getText().trim().replace("warning","");
        return title;
    }

    public String getRedDeleteWarningMsg(){
        return deleteRedWarningMsg.getText().trim();
    }

    public String getWhiteDeleteWarningMsg(){
        return deleteWhiteWarningMsg.getText().trim();
    }

    public boolean isDeleteWarningIconPresent(){
        return deleteIcon.isVisible();
    }
}
