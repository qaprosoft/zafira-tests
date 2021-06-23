package com.qaprosoft.zafira.gui.desktop.component.methodresult;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[contains(@class,'swiper-slide')]
public class SwiperSlide extends AbstractUIObject {
    @FindBy(xpath = ".//md-icon[contains(@class,'swiper-slide__icon material-icons')]")
    private ExtendedWebElement swiperIcon;

    @FindBy(xpath = ".//time[contains(@class,'swiper-slide__top-section')]")
    private ExtendedWebElement timeAgo;

    @FindBy(xpath = "//div[contains(@class,'swiper-slide__time-diff ng-scope')]")
    private ExtendedWebElement timeDiff;

    public SwiperSlide(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
