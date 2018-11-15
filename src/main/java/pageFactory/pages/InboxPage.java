package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InboxPage extends AbstractPage {

    @FindBy(xpath = "//*[@id='PH_user-email']")
    private WebElement userEmailIdentificator;

    @FindBy(xpath = "//*[@id='b-toolbar__left']//span")
    private WebElement createNewMailButton;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public boolean isUserSignIn() {
        waitForElementVisible(userEmailIdentificator);
        return userEmailIdentificator.isDisplayed();
    }

    public void openWriteNewMail() {
        createNewMailButton.click();
    }
}
