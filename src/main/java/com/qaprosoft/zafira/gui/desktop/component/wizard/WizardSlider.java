package com.qaprosoft.zafira.gui.desktop.component.wizard;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@class='wizard-slider__pagination swiper-pagination swiper-pagination-clickable swiper-pagination-bullets']
public class WizardSlider extends AbstractUIObject {
    @FindBy(xpath = ".//div[@aria-label='Go to slide 2']//div[@class='wizard-slider__pagination-element']")
    private ExtendedWebElement coreConceptsCircle;

    @FindBy(xpath = ".//div[@aria-label='Go to slide 2']//div[@class='wizard-slider__pagination-divider']")
    private ExtendedWebElement firstPaginationLineDivider;

    @FindBy(xpath = ".//div[@aria-label='Go to slide 3']//div[@class='wizard-slider__pagination-element']")
    private ExtendedWebElement agentConfigurationCircle;

    @FindBy(xpath = ".//div[@aria-label='Go to slide 3']//div[@class='wizard-slider__pagination-divider']")
    private ExtendedWebElement secondPaginationLineDivider;

    @FindBy(xpath = "//div[@aria-label='Go to slide 4']//div[@class='wizard-slider__pagination-element']")
    private ExtendedWebElement seleniumGridCircle;

    public WizardSlider(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
