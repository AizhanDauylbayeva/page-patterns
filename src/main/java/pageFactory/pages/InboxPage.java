package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InboxPage extends AbstractPage {

    @FindBy(xpath = "//*[@id='PH_user-email']")
    private static WebElement user_email;

    @FindBy(xpath = "//*[@id='b-toolbar__left']//span")
    private static WebElement createNewMailButton;

    InboxPage(WebDriver driver){
        super(driver);
    }

    public static WebElement getUserEmail() {
        return user_email;
    }

    public void waitForUserEmail(){
        waitForElementVisible(user_email);
    }

    public void openWriteNewMail(){
        createNewMailButton.click();
    }
}
