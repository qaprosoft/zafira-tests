//package com.qaprosoft.zafira.service.impl;
//
//import com.qaprosoft.zafira.domain.Config;
//import com.qaprosoft.zafira.gui.BasePage;
//import com.qaprosoft.zafira.gui.DashboardPage;
//import com.qaprosoft.zafira.gui.component.Header;
//import com.qaprosoft.zafira.service.HeaderService;
//import org.openqa.selenium.WebDriver;
//
//public class HeaderServiceImpl extends BaseService implements HeaderService {
//
//    private static final Long DEFAULT_DASHBOARD_ID = Config.DEFAULT_DASHBOARD_ID.getLongValue();
//
//    public HeaderServiceImpl(WebDriver driver, BasePage basePage) {
//        super(driver, basePage, null);
//    }
//
//    @Override
//    public DashboardPage clickBrandImage() {
//        Header header = page.getHeader();
//        header.clickBrandImage();
//        return new DashboardPage(driver, DEFAULT_DASHBOARD_ID);
//    }
//
//}
