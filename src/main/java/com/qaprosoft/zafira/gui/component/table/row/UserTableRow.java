package com.qaprosoft.zafira.gui.component.table.row;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UserTableRow extends AbstractUIObject {

    @FindBy(css = ".profile-photo .material-icons")
    private ExtendedWebElement emptyPhotoIcon;

    @FindBy(css = ".profile-photo img")
    private ExtendedWebElement photoImage;

    @FindBy(xpath = ".//*[@name = 'userUsername']")
    private ExtendedWebElement usernameLabel;

    @FindBy(xpath = ".//*[@name = 'userEmail']")
    private ExtendedWebElement emailLabel;

    @FindBy(xpath = ".//*[@name = 'userFirstLastName']")
    private ExtendedWebElement firstLastNameLabel;

    @FindBy(xpath = ".//*[@data-title= 'Status']//*[@name = 'status']")
    private ExtendedWebElement statusLabel;

    @FindBy(xpath = ".//*[@data-title= 'Source']//*[@name = 'status']")
    private ExtendedWebElement sourceLabel;

    @FindBy(xpath = ".//*[@name = 'userCreated']")
    private ExtendedWebElement registrationDateLabel;

    @FindBy(className = "time")
    private ExtendedWebElement lastLoginLabel;

    @FindBy(xpath = ".//*[@name = 'userMenu']")
    private ExtendedWebElement menuButton;

    public UserTableRow(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getEmptyPhotoIcon() {
        return emptyPhotoIcon;
    }

    public ExtendedWebElement getPhotoImage() {
        return photoImage;
    }

    public String getPhotoImageLink() {
        return photoImage.getAttribute("src");
    }

    public ExtendedWebElement getUsernameLabel() {
        return usernameLabel;
    }

    public String getUsernameLabelText() {
        return usernameLabel.getText();
    }

    public ExtendedWebElement getEmailLabel() {
        return emailLabel;
    }

    public String getEmailLabelText() {
        return emailLabel.getText();
    }

    public ExtendedWebElement getFirstLastNameLabel() {
        return firstLastNameLabel;
    }

    public String getFirstLastNameLabelText() {
        return firstLastNameLabel.getText();
    }

    public ExtendedWebElement getStatusLabel() {
        return statusLabel;
    }

    public String getStatusLabelText() {
        return statusLabel.getText();
    }

    public ExtendedWebElement getSourceLabel() {
        return sourceLabel;
    }

    public String getSourceLabelText() {
        return sourceLabel.getText();
    }

    public ExtendedWebElement getRegistrationDateLabel() {
        return registrationDateLabel;
    }

    public String getRegistrationDateLabelText() {
        return registrationDateLabel.getText();
    }

    public ExtendedWebElement getLastLoginLabel() {
        return lastLoginLabel;
    }

    public String getLastLoginLabelText() {
        return lastLoginLabel.getText();
    }

    public ExtendedWebElement getMenuButton() {
        return menuButton;
    }

    public void clickMenuButton() {
        menuButton.click();
    }

}
