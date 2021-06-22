package com.qaprosoft.zafira.gui.desktop.page.tenant;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.qaprosoft.zafira.gui.desktop.component.methodresult.ActionStepCard;
import com.qaprosoft.zafira.gui.desktop.component.methodresult.SwiperSlide;
import com.qaprosoft.zafira.gui.desktop.component.testresult.TestSession;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MethodResultPage extends AbstractPage {
    @FindBy(xpath = "//v-accordion[@class='test-sessions__accordion ng-scope ng-isolate-scope']")
    private TestSession session;

    @FindBy(xpath = "//div[@class='testrun-info__tab-table-row ng-scope']")
    private List<ActionStepCard> steps;

    @FindBy(xpath = "//div[contains(@class,'swiper-slide')]")
    private List<SwiperSlide> swiperSlides;

    @FindBy(xpath = "//button[@aria-label='Go back']")
    private ExtendedWebElement backButton;

    @FindBy(xpath = "//span[@class='testrun-info__title-name ng-binding ng-scope']")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "//div[@class='testrun-info__tab-table-col _visuals' and text()='Visuals']")
    private ExtendedWebElement visualsColumnTitle;

    @FindBy(xpath = "//div[@class='testrun-info__tab-table-col _start' and text()='Start']")
    private ExtendedWebElement startColumnTitle;

    @FindBy(xpath = "//span[text()='Status']/ancestor::div[@class='testrun-info__tab-table-col _status']")
    private ExtendedWebElement statusColumnTitle;

    @FindBy(xpath = "//div[@class='testrun-info__tab-table-col _action' and text()='Action']")
    private ExtendedWebElement actionColumnTitle;

    @FindBy(xpath = "//span[text()='Artifacts']/ancestor::button")
    private ExtendedWebElement downloadArtifactsButton;

    public MethodResultPage(WebDriver driver) {
        super(driver);
    }
}
