package com.qaprosoft.zafira.gui.desktop.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ElementLoadingStrategy;
import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Launcher extends AbstractUIObject {
    private final Clock clock = Clock.systemDefaultZone();

    @FindBy(xpath = "//div[contains(@class,'folder-container_folder_name hide-phone')]")
    private List<ExtendedWebElement> repositoryList;

    @FindBy(xpath = "//li[contains(@class,'hide-phone')]//div[@class='folder-container_item_list_item-wrapper']")
    private List<ExtendedWebElement> suitesList;

    @FindBy(xpath = "//span[@class='ng-binding']")
    private List<ExtendedWebElement> defaultSuitesList;

    @FindBy(xpath = "//button[contains(text(),'Launch')]")
    private ExtendedWebElement launchSuiteButton;

    public Launcher(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
        setLoadingStrategy(ElementLoadingStrategy.BY_VISIBILITY);
    }

    public void launchSuiteTests(String repoName, String suiteName) {
        long defaultTimeout = 5000;
        long defaultPolling = 500;
        waitListToLoad(repositoryList, defaultTimeout, defaultPolling);
        for (ExtendedWebElement repository : repositoryList) {
            if (repository.getText().contains(repoName)) {
                repository.click();
                waitListToLoad(defaultSuitesList, defaultTimeout, defaultPolling);
                for (ExtendedWebElement suite : defaultSuitesList) {
                    if (suite.getText().contains(suiteName)) {
                        suite.click();
                        launchSuiteButton.click();
                        break;
                    }
                }
                break;
            }
        }
    }

    private void waitListToLoad(List<ExtendedWebElement> list, long timeout, long pollingEvery) {
        Instant end = clock.instant().plusMillis(timeout);
        Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
        Duration interval = Duration.ofMillis(pollingEvery);
        while (list.isEmpty() && end.isAfter(clock.instant())) {
            try {
                sleeper.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
