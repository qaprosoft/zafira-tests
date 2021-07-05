package com.qaprosoft.zafira.gui.impl;

import com.qaprosoft.zafira.gui.base.LogInBase;
import com.qaprosoft.zafira.gui.desktop.component.common.Pagination;
import com.qaprosoft.zafira.gui.desktop.component.member.AddMemberWindow;
import com.qaprosoft.zafira.gui.desktop.component.member.MemberCard;
import com.qaprosoft.zafira.gui.desktop.page.tenant.MembersPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MembersPageTest extends LogInBase {

    @Test
    public void membersPageElementsPresenceTest() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        membersPage.assertPageOpened();

        String expectedTitle = "Members";
        String expectedUsernameTitle = "USERNAME";
        String expectedRoleTitle = "ROLE";
        String expectedDataTitle = "ADDED";

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(membersPage.getTitle(), expectedTitle);
        softAssert.assertTrue(membersPage.isSearchFieldPresent(), "Can't find search field");
        softAssert.assertTrue(membersPage.isAddMemberButtonActive(), "Can't find add member button");
        softAssert.assertEquals(membersPage.getUsernameTableTitle(), expectedUsernameTitle);
        softAssert.assertEquals(membersPage.getRoleTableTitle(), expectedRoleTitle);
        softAssert.assertEquals(membersPage.getAddingDate(), expectedDataTitle);
        List<MemberCard> memberCards = membersPage.getMemberCards();
        Pagination pagination = membersPage.getPagination();
        softAssert.assertEquals(String.valueOf(memberCards.size()), pagination.getNumberOfItemsOnThePage(),
                "Number of items differs to pagination");
        softAssert.assertTrue(membersPage.getHeader().isUIObjectPresent());
        softAssert.assertTrue(membersPage.getNavigationMenu().isUIObjectPresent());
        softAssert.assertAll();
    }

    @Test
    public void addMemberWindowElementsPresenceTest() {
        MembersPage membersPage = navigationMenu.toMembersPage();
        membersPage.assertPageOpened();
        AddMemberWindow addMemberWindow = membersPage.openAddMemberWindow();

        String expectedWindowTitle = "Add members";
        List<String> expectedRoles = List.of("Administrator", "Manager", "Engineer", "Guest");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addMemberWindow.getTitle(), expectedWindowTitle);
        softAssert.assertTrue(addMemberWindow.isCloseButtonPresent(), "Can't fin close button");
        softAssert.assertFalse(addMemberWindow.isSaveButtonActive(),
                "Save button should be inactive because of empty fields");
        softAssert.assertTrue(addMemberWindow.isUsernameFieldPresent(), "Can't find username field");
        softAssert.assertTrue(addMemberWindow.isRoleFieldPresent(), "Can't find role field");
        softAssert.assertEquals(addMemberWindow.getRoles(), expectedRoles, "Some roles differ expected");
        softAssert.assertAll();
    }
}
