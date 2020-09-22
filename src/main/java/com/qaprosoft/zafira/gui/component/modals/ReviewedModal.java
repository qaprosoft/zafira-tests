package com.qaprosoft.zafira.gui.component.modals;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.zafira.gui.component.Modal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ReviewedModal extends Modal {

    @FindBy(id = "comment")
    private ExtendedWebElement commentTextarea;

    @FindBy(id = "markAsReviewed")
    private ExtendedWebElement markAsReviewedButton;

    public ReviewedModal(WebDriver driver) {
        super(driver);
    }

    public ExtendedWebElement getCommentTextarea() {
        return commentTextarea;
    }

    public void typeComment(String comment) {
        commentTextarea.type(comment);
    }

    public ExtendedWebElement getMarkAsReviewedButton() {
        return markAsReviewedButton;
    }

    public void clickMarkAsReviewedButton() {
        markAsReviewedButton.click();
    }

}
