package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.base.LogInBase;
import com.qaprosoft.zafira.gui.desktop.component.member.AddMemberWindow;
import com.qaprosoft.zafira.gui.desktop.page.tenant.MembersPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MemberProcessTest extends LogInBase {
    private String memberUsername = "testUser";

    @AfterMethod
    public void deleteMember() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        membersPage.deleteMember(memberUsername);
        pause(5);
    }

    @Test
    public void addMemberTest() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        AddMemberWindow memberWindow = membersPage.openAddMemberWindow();
        SoftAssert softAssert = new SoftAssert();

        memberWindow.typeUsername(memberUsername);
        softAssert.assertTrue(memberWindow.isUsernamePresent(memberUsername),
                "Can't find " + memberUsername + " in username field");
        softAssert.assertFalse(memberWindow.isSaveButtonActive(),
                "Save button should not be active because role field is empty");
        String role = "Manager";
        memberWindow.chooseRole(role);
        softAssert.assertTrue(memberWindow.isSaveButtonActive(),
                "Save button should be active because all fields are filled");
        memberWindow.clickSaveButton();

        pause(WebConstant.TIME_TO_LOAD_PAGE);
        softAssert.assertTrue(membersPage.isMemberPresent(memberUsername),
                "Can't find new member with username " + memberUsername);
        softAssert.assertAll();
    }
}
