package com.qaprosoft.zafira.gui.desktop.component.testrun;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.util.WaitUtil;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

//className = "md-dialog-content"
public class TestRunsLauncher extends AbstractUIObject {
    @FindBy(xpath = ".//div[contains(@class,'folder-container_folder_name hide-phone')]")
    private List<ExtendedWebElement> repositoryList;

    @FindBy(xpath = ".//li[contains(@class,'hide-phone')]//div[@class='folder-container_item_list_item-wrapper']")
    private List<ExtendedWebElement> suitesList;

    @FindBy(xpath = ".//span[@class='ng-binding']")
    private List<ExtendedWebElement> defaultSuitesList;

    @FindBy(xpath = ".//button[contains(text(),'Launch')]")
    private ExtendedWebElement launchSuiteButton;

    public TestRunsLauncher(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
    }

    public void launchSuiteTests(String repoName, String suiteName) {
        long defaultTimeout = 5000;
        long defaultPolling = 500;
        WaitUtil.waitListToLoad(repositoryList, defaultTimeout, defaultPolling);
        for (ExtendedWebElement repository : repositoryList) {
            if (repository.getText().trim().contains(repoName)) {
                repository.click();
                WaitUtil.waitListToLoad(defaultSuitesList, defaultTimeout, defaultPolling);
                for (ExtendedWebElement suite : defaultSuitesList) {
                    if (suite.getText().trim().contains(suiteName)) {
                        suite.click();
                        launchSuiteButton.click();
                        break;
                    }
                }
                break;
            }
        }
    }
}
