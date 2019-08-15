package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.gui.component.table.BaseTable;
import com.qaprosoft.zafira.service.PaginationService;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

public class PaginationServiceImpl extends BaseService implements PaginationService {

    private final BaseTable table;

    public PaginationServiceImpl(WebDriver driver, BaseTable table) {
        super(driver, null, null);
        this.table = table;
    }

    @Override
    public int getFromItemValue() {
        String countLabelText = table.getPagination().getCountLabelText();
        String fromValue = countLabelText.split("-")[0].trim();
        return StringUtils.isBlank(fromValue) ? 0 : Integer.valueOf(fromValue);
    }

    @Override
    public int getToItemValue() {
        String countLabelText = table.getPagination().getCountLabelText();
        String toValue = countLabelText.split("-")[1].split("of")[0].trim();
        return StringUtils.isBlank(countLabelText) ? 0 : Integer.valueOf(toValue);
    }

    @Override
    public int getTotalItemsValue() {
        String countLabelText = table.getPagination().getCountLabelText();
        String totalValue = countLabelText.split("of")[1].trim();
        return StringUtils.isBlank(countLabelText) ? 0 : Integer.valueOf(totalValue);
    }

}
