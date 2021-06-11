package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.NavigationMenu;
import com.qaprosoft.zafira.gui.desktop.component.TenantHeader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestRunResultPage extends AbstractPage {

    @FindBy(id = "nav-container")
    private NavigationMenu navigationMenu;

    @FindBy(id = "header")
    private TenantHeader header;

    @FindBy(xpath = "//div[@id='pageTitle']//span[text()='Test results']")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//a[contains(@class,'back_button')]//md-icon")
    private ExtendedWebElement backIcon;

    @FindBy(xpath = "//span[@class='test-run-card__title-text ng-binding']")
    private ExtendedWebElement testRunName;

    @FindBy(xpath = "//div[@class='test-run-card__job-name ng-scope']")
    private ExtendedWebElement zebrunnerJobName;

    @FindBy(xpath = "//div[@class='test-run-card__title']//button[@title='Copy to clipboard']")
    private ExtendedWebElement copyTestRunNameButton;

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

    public String getTestRunName() {
        return testRunName.getText();
    }

    public String getTestRunJob() {
        return zebrunnerJobName.getText();
    }

    public boolean isCopyTestNameButtonActive() {
        testRunName.hover();
        return copyTestRunNameButton.isClickable() && copyTestRunNameButton.isVisible();
    }
}
