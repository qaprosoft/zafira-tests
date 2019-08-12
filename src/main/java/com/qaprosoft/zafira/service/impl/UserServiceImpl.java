package com.qaprosoft.zafira.service.impl;

import com.qaprosoft.zafira.domain.Config;
import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.UserPage;
import com.qaprosoft.zafira.gui.component.modals.ChangePasswordModal;
import com.qaprosoft.zafira.gui.component.modals.UserInfoModal;
import com.qaprosoft.zafira.gui.component.table.row.UserTableRow;
import com.qaprosoft.zafira.models.dto.user.UserType;
import com.qaprosoft.zafira.service.UserService;
import com.qaprosoft.zafira.service.builder.Builder;
import com.qaprosoft.zafira.util.CommonUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class UserServiceImpl extends BaseService<UserPage> implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    public UserServiceImpl(WebDriver driver) {
        super(driver, UserPage.class);
    }

    public UserServiceImpl(WebDriver driver, UserPage page) {
        super(driver, page, UserPage.class);
    }

    @Override
    public String getFirstName(UserTableRow row) {
        return row.getFirstLastNameLabelText().split(" ")[0];
    }

    @Override
    public String getLastName(UserTableRow row) {
        return row.getFirstLastNameLabelText().split(" ")[1];
    }

    @Override
    public Date getRegistrationDate(UserTableRow row) {
        return CommonUtils.parseDate(row.getRegistrationDateLabelText(), "MMMM dd, yyyy");
    }

    @Override
    public UserInfoModal clickNewUserButton() {
        UserPage userPage = getUIObject(driver);
        userPage.getUserSubHeader().clickNewUserButton();
        return new UserInfoModal(driver);
    }

    @Override
    public UserInfoModal clickEditUserButton(UserTableRow row) {
        row.clickMenuButton();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Edit");
        UserInfoModal userInfoModal = new UserInfoModal(driver);
        CommonUtils.waitUntilModalIsOpened(driver, userInfoModal);
        return userInfoModal;
    }

    @Override
    public ChangePasswordModal clickChangePasswordButton(UserTableRow row) {
        row.clickMenuButton();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Change password");
        return new ChangePasswordModal(driver);
    }

    @Override
    public DashboardPage clickPerformanceButton(UserTableRow row) {
        row.clickMenuButton();
        CommonUtils.clickMenuItem(row.getMenuButton(), driver, "Performance");
        return new DashboardPage(driver, Config.PERFORMANCE_DASHBOARD_ID.getLongValue());
    }

    @Override
    public List<UserType> buildUsers(int count) {
        List<UserType> userTypes = new ArrayList<>();
        IntStream.range(0, count).forEach(index -> {
            userTypes.add(buildUser());
        });
        return userTypes;
    }

    @Override
    public UserPage search(String searchText) {
        UserPage userPage = getUIObject(driver);
        userPage.getUserSubHeader().typeSearchInput(searchText);
        waitProgressLinear();
        return new UserPage(driver);
    }

    @Override
    public void deactivateUser(UserTableRow row) {
        UserInfoModal userInfoModal = clickEditUserButton(row);
        userInfoModal.clickDeactivateButton();
        CommonUtils.waitUntilAlertIsOpened(driver);
        driver.switchTo().alert().accept();
    }

    @Override
    public void activateUser(UserTableRow row) {
        UserInfoModal userInfoModal = clickEditUserButton(row);
        userInfoModal.clickActivateButton();
        CommonUtils.waitUntilAlertIsOpened(driver);
        driver.switchTo().alert().accept();
    }

    private UserType buildUser() {
        return Builder.USER.build();
    }

}
