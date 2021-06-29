package com.qaprosoft.zafira.gui.desktop.component.dashboard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//form[@name='form-validation styled-modal__form ng-pristine ng-valid']
public class CreateWidgetForm extends AbstractUIObject {
    @FindBy(xpath = ".//div[contains(@class,'selection-blocks_container_elements_item zf-radio-button ng-scope')]")
    private List<WidgetCard> widgetCards;

    @FindBy(xpath = "//div[contains(@class,'md-select-menu-container md-active md-clickable')]")
    private PeriodListMenu periodListMenu;

    @FindBy(xpath = "//div[@class='md-select-menu-container md-active md-clickable']")
    private ParamsSelectMenu paramsSelectMenu; // for env, platform, browser, locale and priority

    @FindBy(xpath = "//div[@class='md-select-menu-container md-active md-clickable']")
    private GroupByList groupByList; //for tests summary and pass rate (bar)

    @FindBy(id = "modalTitle")
    private ExtendedWebElement modalTitle;

    @FindBy(xpath = ".//md-icon[@aria-label='Close dialog']")
    private ExtendedWebElement closeForm;

    @FindBy(xpath = ".//*[contains(text(),'collections')]/ancestor::button")
    private ExtendedWebElement templatesButton;

    @FindBy(xpath = ".//md-icon[contains(@md-svg-src,'widgets_icon.1cebe89d.svg')]/ancestor::button")
    private ExtendedWebElement existingWidgetsButton;

    @FindBy(xpath = ".//button[contains(text(),'Next')]")
    private ExtendedWebElement nextButton;

    @FindBy(xpath = ".//input[contains(@type,'search')]")
    private ExtendedWebElement searchField;

    @FindBy(xpath = ".//button[contains(text(),'Back')]")
    private ExtendedWebElement backButton;

    @FindBy(xpath = ".//div[@class='widget-wizard_container-helper_picture_caption ng-binding ng-scope']")
    private ExtendedWebElement previewWidgetTitle;

    @FindBy(xpath = "//button[@class='refresh-button md-button md-ink-ripple']")
    private ExtendedWebElement refreshPreviewWidget;

    @FindBy(xpath = ".//md-select[contains(@aria-label,'period')]")
    private ExtendedWebElement periodListMenuButton;

    @FindBy(xpath = ".//*[contains(text(),'url')]/following-sibling::input")
    private ExtendedWebElement urlField;

    @FindBy(xpath = ".//*[contains(text(),'passed_value')]/following-sibling::input")
    private ExtendedWebElement passedValueField;

    @FindBy(xpath = ".//*[contains(text(),'percent')]/following-sibling::input")
    private ExtendedWebElement percentField;

    @FindBy(xpath = ".//*[contains(text(),'error')]/following-sibling::input")
    private ExtendedWebElement errorCountField;

    @FindBy(xpath = ".//*[contains(text(),'group_by')]/following-sibling::md-select")
    private ExtendedWebElement groupBySelect;

    @FindBy(xpath = ".//button[@class='md-primary md-button']")
    private ExtendedWebElement showMoreButton;

    @FindBy(xpath = ".//label[text()='env']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement envVisibilityToggle;

    @FindBy(xpath = ".//label[text()='env']/following-sibling::md-select")
    private ExtendedWebElement envSelect;

    @FindBy(xpath = ".//label[text()='platform']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement platformVisibilityToggle;

    @FindBy(xpath = ".//label[text()='platform']/following-sibling::md-select")
    private ExtendedWebElement platformSelect;

    @FindBy(xpath = ".//label[text()='locale']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement localeVisibilityToggle;

    @FindBy(xpath = ".//label[text()='locale']/following-sibling::md-select")
    private ExtendedWebElement localeSelect;

    @FindBy(xpath = ".//label[text()='browser']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement browserVisibilityToggle;

    @FindBy(xpath = ".//label[text()='browser']/following-sibling::md-select")
    private ExtendedWebElement browserSelect;

    @FindBy(xpath = ".//label[text()='priority']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement priorityVisibilityToggle;

    @FindBy(xpath = ".//label[text()='priority']/following-sibling::md-select")
    private ExtendedWebElement prioritySelect;

    @FindBy(xpath = ".//label[text()='users']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement usersVisibilityToggle;

    @FindBy(xpath = ".//label[text()='users']/following-sibling::input")
    private ExtendedWebElement usersField;

    @FindBy(xpath = ".//label[text()='builds']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement buildsVisibilityToggle;

    @FindBy(xpath = ".//label[text()='builds']/following-sibling::input")
    private ExtendedWebElement buildsField;

    @FindBy(xpath = ".//label[text()='runs']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement runsVisibilityToggle;

    @FindBy(xpath = ".//label[text()='runs']/following-sibling::input")
    private ExtendedWebElement runsField;

    @FindBy(xpath = ".//label[text()='milestone']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement milestoneVisibilityToggle;

    @FindBy(xpath = ".//label[text()='milestone']/following-sibling::input")
    private ExtendedWebElement milestoneField;

    @FindBy(xpath = ".//label[text()='milestone_version']/ancestor::div[contains(@class,'input-wrapper')]/button")
    private ExtendedWebElement milestoneVersionVisibilityToggle;

    @FindBy(xpath = ".//label[text()='milestone_version']/following-sibling::input")
    private ExtendedWebElement milestoneVersionField;

    @FindBy(xpath = ".//input[@name='title']")
    private ExtendedWebElement nameField;

    @FindBy(xpath = ".//textarea[@name='description']")
    private ExtendedWebElement textAreaField;

    @FindBy(xpath = ".//button[contains(text(),'Add')]")
    private ExtendedWebElement addButton;

    public CreateWidgetForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
