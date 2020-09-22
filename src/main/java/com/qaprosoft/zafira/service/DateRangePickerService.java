package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.gui.component.modals.DateRangeModal;

import java.time.LocalDateTime;

public interface DateRangePickerService {

    void chooseDate(DateRangeModal dateRangeModal, LocalDateTime dateFrom, LocalDateTime dateTo);

}
