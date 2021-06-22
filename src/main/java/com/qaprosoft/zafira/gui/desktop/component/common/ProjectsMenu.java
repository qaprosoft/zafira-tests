package com.qaprosoft.zafira.gui.desktop.component.common;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//div[contains(@class,'md-select-menu-container project-settings__select md-active md-clickable')]
public class ProjectsMenu extends AbstractUIObject {
    @FindBy(xpath = ".//div[contains(@class,'md-text')]//span[@class='project-settings__item-name ng-binding']")
    private List<ExtendedWebElement> projectTitles;

    @FindBy(xpath = ".//div[contains(@class,'md-text')]//span[@class='project-settings__item-key ng-binding']")
    private List<ExtendedWebElement> projectKeys;

    @FindBy(xpath = ".//div[contains(@class,'md-text')]//span[contains(@class,'profile-photo')]")
    private List<ExtendedWebElement> projectPhotos;

    @FindBy(xpath = "//div[@class='project-settings__button']")
    private ExtendedWebElement viewAllProjectsButton;

    @FindBy(xpath = "//span[text()='Create a Project']")
    private ExtendedWebElement createNewProjectButton;

    public ProjectsMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
