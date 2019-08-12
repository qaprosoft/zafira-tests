package com.qaprosoft.zafira.service;

import com.qaprosoft.zafira.gui.DashboardPage;
import com.qaprosoft.zafira.gui.UserPage;
import com.qaprosoft.zafira.gui.component.modals.ChangePasswordModal;
import com.qaprosoft.zafira.gui.component.modals.UserInfoModal;
import com.qaprosoft.zafira.gui.component.table.row.UserTableRow;
import com.qaprosoft.zafira.models.dto.user.UserType;

import java.util.Date;
import java.util.List;

public interface UserService {

    String getFirstName(UserTableRow row);

    String getLastName(UserTableRow row);

    Date getRegistrationDate(UserTableRow row);

    UserInfoModal clickNewUserButton();

    UserInfoModal clickEditUserButton(UserTableRow row);

    ChangePasswordModal clickChangePasswordButton(UserTableRow row);

    DashboardPage clickPerformanceButton(UserTableRow row);

    List<UserType> buildUsers(int count);

    UserPage search(String searchText);

    void deactivateUser(UserTableRow row);

    void activateUser(UserTableRow row);

}
