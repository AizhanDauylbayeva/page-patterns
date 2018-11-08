package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SentFolderPage extends AbstractPage {

    @FindBy(xpath = "//*[@class='b-datalist b-datalist_letters b-datalist_letters_to']//div[@class='b-datalist__item__subj']")
    private static List<WebElement> sentList;

    @FindBy(id = "PH_logoutLink")
    private static WebElement logout;

    public SentFolderPage(WebDriver driver) {
        super(driver);
    }

    public static List<WebElement> getSentList() {
        return sentList;
    }

    public void logout() {
        logout.click();
    }
}
