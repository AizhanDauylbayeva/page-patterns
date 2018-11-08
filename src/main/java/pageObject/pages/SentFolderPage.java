package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SentFolderPage extends AbstractPage{

    private static final By SENTLIST_LOCATOR = By.xpath("//div[@class='b-datalist b-datalist_letters b-datalist_letters_to']//*[@class='b-datalist__item__subj']");
    private static final By LOGOUT_LOCATOR = By.id("PH_logoutLink");

    public SentFolderPage(WebDriver driver) {
        super(driver);
    }

    public static By getSentlistLocator() {
        return SENTLIST_LOCATOR;
    }

    public void logout(){
        driver.findElement(LOGOUT_LOCATOR).click();
    }
}
