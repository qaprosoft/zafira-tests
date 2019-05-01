package com.qaprosoft.zafira.gui.component.modals;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.Modal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class UploadUserPhotoModal extends Modal {

    @FindBy(id = "fileInput")
    private ExtendedWebElement uploadInput;

    @FindBy(id = "upload")
    private ExtendedWebElement uploadButton;

    public UploadUserPhotoModal(WebDriver driver) {
        super(driver);
    }

    public ExtendedWebElement getUploadInput() {
        return uploadInput;
    }

    public void setFileIntoUploadInput(String filePath) {
        uploadInput.getElement().sendKeys(filePath);
    }

    public ExtendedWebElement getUploadButton() {
        return uploadButton;
    }

    public void clickUploadButton() {
        uploadButton.click();
    }

}
