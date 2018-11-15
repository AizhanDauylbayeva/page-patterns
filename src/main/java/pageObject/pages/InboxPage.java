package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageObject.pages.AbstractPage;

public class InboxPage extends AbstractPage {
    private static final By USER_EMAIL_LOCATOR  = By.xpath("//*[@id='PH_user-email']");
    private static final By CREATE_MAIL_LOCATOR = By.xpath("//*[@id='b-toolbar__left']//span");

    InboxPage(WebDriver driver){
        super(driver);
    }

    public static By getUserEmailLocator() {
        return USER_EMAIL_LOCATOR;
    }

    public void openWriteNewMail(){
        driver.findElement(CREATE_MAIL_LOCATOR).click();
    }
}
