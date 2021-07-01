package com.qaprosoft.zafira.gui.desktop.component.project;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//md-dialog[contains(@class,'project-modal')]
public class ProjectProcessWindow extends AbstractUIObject {
    @FindBy(xpath = "//h2[@class='modal-header__title ng-binding ng-scope']")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//button[@id='close']")
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
    private ExtendedWebElement projectVisibility;

    @FindBy(xpath = ".//span[@class='button__text']")
    private ExtendedWebElement projectMembersButton;

    @FindBy(xpath = ".//span[text()='delete']/ancestor::button")
    private ExtendedWebElement deleteProjectButton;

    @FindBy(xpath = "//span[text()='cancel']/ancestor::button")
    private ExtendedWebElement cancelProcessButton;

    @FindBy(xpath = "//span[text()='save' or text()='create']/ancestor::button")
    private ExtendedWebElement saveButton;

    public ProjectProcessWindow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isProjectPublic(){
        return Boolean.getBoolean(projectVisibility.getAttribute("aria-checked").trim());
    }

    public void closeWindow(){
        closeButton.click();
    }
}
