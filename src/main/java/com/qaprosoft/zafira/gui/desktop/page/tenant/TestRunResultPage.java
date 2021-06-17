package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.RunResultDetailsBar;
import com.qaprosoft.zafira.gui.desktop.component.TenantHeader;
import com.qaprosoft.zafira.gui.desktop.component.TestRunCard;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunResultPage extends AbstractPage {

    @FindBy(id = "nav-container")
    private NavigationMenu navigationMenu;

    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[contains(@class,'test-run-card ng-isolate-scope _single')]")
    private TestRunCard testCard;

    @FindBy(xpath = "//a[contains(@class,'back_button')]//md-icon")
    private ExtendedWebElement backIcon;

    @FindBy(xpath = "//div[@id='pageTitle']//span[text()='Test results']")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//div[@class='test-run-group-row test-details__header-actions _default']")
    private RunResultDetailsBar resultBar;

    @FindBy(xpath = "//*[text()='Expand all labels']/parent::div")
    private ExtendedWebElement expandAllLabel;

    @FindBy(xpath = "//*[text()='Collapse all labels']/parent::div")
    private ExtendedWebElement collapseAllLabel;

    public TestRunResultPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(pageTitle);
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public boolean isBackButtonActive() {
        return backIcon.isClickable() && backIcon.isVisible();
    }

    public TestRunCard getPageCard(){
        return testCard;
    }

    public RunResultDetailsBar getResultBar(){
        return resultBar;
    }

    public boolean isCollapseAllLabelVisible(){
        return collapseAllLabel.isVisible();
    }

    public boolean isExpandAllLabelVisible(){
        return expandAllLabel.isVisible();
    }
}
