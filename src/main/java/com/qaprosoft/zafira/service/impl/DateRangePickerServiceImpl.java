package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.exception.DateNotFoundException;
import com.qaprosoft.zafira.gui.BasePage;
import com.qaprosoft.zafira.gui.component.modals.DateRangeModal;
import com.qaprosoft.zafira.gui.component.other.Calendar;
import com.qaprosoft.zafira.service.DateRangePickerService;
import org.openqa.selenium.WebDriver;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;

import static java.util.Locale.US;

public class DateRangePickerServiceImpl extends BaseService implements DateRangePickerService {

    public DateRangePickerServiceImpl(WebDriver driver, BasePage page) {
        super(driver, page, null);
    }

    @Override
    public void chooseDate(DateRangeModal dateRangeModal, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Calendar> calendars = dateRangeModal.getCalendars();
        Calendar leftCalendar = calendars.get(0);
        Calendar rightCalendar = calendars.get(1);
        pickDate(leftCalendar, dateFrom);
        if(dateTo != null) {
            pickDate(rightCalendar, dateTo);
        }
    }

    private void pickDate(Calendar calendar, LocalDateTime date) {
        calendar.selectYear(driver, String.valueOf(date.getYear()));
        calendar.selectMonth(driver, date.getMonth().getDisplayName(TextStyle.FULL, US));
        String day = String.valueOf(date.getDayOfMonth());
        ExtendedWebElement d = calendar.getDates().stream()
                                              .filter(dateElement -> dateElement.getText().equals(day))
                                              .findAny()
                                              .orElseThrow(() -> new DateNotFoundException("Day " + day + " does not exist"));
        d.click();
    }

}
