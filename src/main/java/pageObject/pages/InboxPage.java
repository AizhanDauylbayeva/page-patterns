package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InboxPage extends AbstractPage {
    public static final By USER_EMAIL_LOCATOR  = By.xpath("//*[@id='PH_user-email']");
    private static final By CREATE_MAIL_LOCATOR = By.xpath("//*[@id='b-toolbar__left']//span");

    InboxPage(WebDriver driver){
        super(driver);
    }

    public void openWriteNewMail(){
        driver.findElement(CREATE_MAIL_LOCATOR).click();
    }
}
