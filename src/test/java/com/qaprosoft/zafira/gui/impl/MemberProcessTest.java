package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.zafira.constant.WebConstant;
import com.qaprosoft.zafira.gui.base.LogInBase;
import com.qaprosoft.zafira.gui.desktop.component.member.AddMemberWindow;
import com.qaprosoft.zafira.gui.desktop.component.member.MemberCard;
import com.qaprosoft.zafira.gui.desktop.page.tenant.MembersPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MemberProcessTest extends LogInBase {
    private String memberUsername = "testUser";

    @BeforeTest
    public void deleteIfExistsMember() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        if (membersPage.isMemberPresent(memberUsername)) {
            membersPage.deleteMember(memberUsername);
            pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        }
    }

    @AfterMethod
    public void deleteMember() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        membersPage.deleteMember(memberUsername);
        pause(WebConstant.TIME_TO_LOAD_HEAVY_ELEMENT);
        Assert.assertFalse(membersPage.isMemberPresent(memberUsername));
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
        String role = "Administrator";
        memberWindow.chooseRole(role);
        softAssert.assertTrue(memberWindow.isSaveButtonActive(),
                "Save button should be active because all fields are filled");
        memberWindow.clickSaveButton();

        pause(WebConstant.TIME_TO_LOAD_PAGE);
        softAssert.assertTrue(membersPage.isMemberPresent(memberUsername),
                "Can't find new member with username " + memberUsername);
        softAssert.assertAll();
    }

    @Test
    public void changeMemberRoleTest() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        AddMemberWindow memberWindow = membersPage.openAddMemberWindow();
        String role = "Administrator";
        memberWindow.createMember(memberUsername, role);

        List<String> changeRoleNames = List.of("Manager", "Engineer", "Guest", "Administrator");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(membersPage.isMemberPresent(memberUsername),
                "Can't find new member with username " + memberUsername);
        MemberCard memberCard = membersPage.getMemberByName(memberUsername);

        for (String changeRoleName : changeRoleNames) {
            memberCard.changeRole(changeRoleName);
            memberCard = membersPage.getMemberByName(memberUsername);
            softAssert.assertEquals(memberCard.getRole(), changeRoleName,
                    "Role " + memberCard.getRole() + " didn't changed to " + changeRoleName);
        }
        softAssert.assertAll();
    }

    @Test
    public void memberSearchFieldTest() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        AddMemberWindow memberWindow = membersPage.openAddMemberWindow();
        String role = "Administrator";
        memberWindow.createMember(memberUsername, role);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(membersPage.isMemberPresent(memberUsername),
                "Can't find new member with username " + memberUsername);

        membersPage.typeInSearchField(memberUsername);
        pause(WebConstant.TIME_TO_LOAD_PAGE);
        softAssert.assertTrue(membersPage.isMemberPresent(memberUsername), "Can't find member after search field filter");
        List<MemberCard> memberCards = membersPage.getMemberCards();
        for (MemberCard member : memberCards) {
            softAssert.assertTrue(member.getUsername().contains(memberUsername),
                    "All usernames should contain string from search field, member " + member.getName() + ", search field: " + memberUsername);
        }
        membersPage.typeInSearchField("");
        softAssert.assertAll();
    }
}
