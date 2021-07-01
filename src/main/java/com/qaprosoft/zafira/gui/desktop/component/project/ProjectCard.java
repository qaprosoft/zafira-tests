package com.qaprosoft.zafira.gui.desktop.component.project;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import com.qaprosoft.zafira.gui.desktop.page.tenant.TestRunsPage;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

//div[@class='projects-table__row ng-scope']
public class ProjectCard extends AbstractUIObject {
    @FindBy(xpath = ".//span[@class='projects-photo__icon-wrapper _squared ng-scope']")
    private ExtendedWebElement img;

    @FindBy(xpath = ".//a[@class='projects-table__content _link ng-binding']")
    private ExtendedWebElement projectName;

    @FindBy(xpath = ".//div[@class='projects-table__col _cat']/span")
    private ExtendedWebElement category;

    @FindBy(xpath = ".//div[@class='projects-table__col _key']/span")
    private ExtendedWebElement key;

    @FindBy(xpath = ".//div[@class='projects-table__col _lead']/span")
    private ExtendedWebElement lead;

    @FindBy(xpath = ".//div[@class='projects-table__col _created']/span")
    private ExtendedWebElement created;

    @FindBy(xpath = ".//div[@class='projects-table__col _members']/a")
    private ExtendedWebElement membersRef;

    @FindBy(xpath = ".//div[@class='projects-table__col _edit']/md-icon")
    private ExtendedWebElement edit;

    public ProjectCard(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getKey() {
        return key.getText().trim();
    }

    public String getName(){
        return projectName.getText().trim();
    }

    public TestRunsPage toTestRunsPage() {
        projectName.click();
        return new TestRunsPage(driver);
    }
}
