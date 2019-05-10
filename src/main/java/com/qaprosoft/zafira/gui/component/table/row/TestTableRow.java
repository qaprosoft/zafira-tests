package com.qaprosoft.zafira.gui.component.table.row;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TestTableRow extends AbstractUIObject {

    @FindBy(className = "test-info__name")
    private ExtendedWebElement nameLabel;

    @FindBy(name = "testOwner")
    private ExtendedWebElement ownerLabel;

    @FindBy(name = "taskTicket")
    private ExtendedWebElement taskTicketLink;

    @FindBy(name = "issueTicket")
    private ExtendedWebElement issueTicketLink;

    @FindBy(xpath = ".//button[.//*[text() = 'remove_red_eye']]")
    private ExtendedWebElement detailsButton;

    @FindBy(xpath = ".//button[.//*[text() = 'more_vert']]")
    private ExtendedWebElement menuButton;

    public TestTableRow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getNameLabel() {
        return nameLabel;
    }

    public String getNameLabelText() {
        return nameLabel.getText();
    }

    public ExtendedWebElement getOwnerLabel() {
        return ownerLabel;
    }

    public String getOwnerLabelText() {
        return ownerLabel.getText();
    }

    public ExtendedWebElement getTaskTicketLink() {
        return taskTicketLink;
    }

    public String getTaskTicketLinkText() {
        return taskTicketLink.getText();
    }

    public ExtendedWebElement getIssueTicketLink() {
        return issueTicketLink;
    }

    public String getIssueTicketLinkText() {
        return issueTicketLink.getText();
    }

    public ExtendedWebElement getDetailsButton() {
        return detailsButton;
    }

    public void clickDetailsButton() {
        detailsButton.click();
    }

    public ExtendedWebElement getMenuButton() {
        return menuButton;
    }

    public void clickMenuButton() {
        menuButton.click();
    }

}
