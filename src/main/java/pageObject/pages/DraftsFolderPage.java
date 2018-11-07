package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DraftsFolderPage extends AbstractPage{

    public static final By FILLED_ADDRESSEE_LOCATOR = By.xpath("//div[@class='b-datalist__item__addr' and contains(string(), 'ayzhan7797@mail.ru')]");
    public static final By FILLED_SUBJECT_LOCATOR = By.xpath("//a[@data-subject = 'test(module 5)']");
    public static final By FILLED_BODY_LOCATOR = By.xpath("//*[@class = 'js-helper js-readmsg-msg' and contains(string(), 'Hello!')]");
    private static final By SEND_BUTTON_LOCATOR = By.xpath("//div[@data-name='send']/span");
    private static final By DRAFTS_FOLDER_LOCATOR = By.xpath("//a[@data-mnemo='drafts']");
    public static final By DATALIST_LOCATOR = By.xpath(".//*[@class='b-datalist b-datalist_letters b-datalist_letters_to']//div[@class='b-datalist__item__subj']");
    private static final By SENT_FOLDER_LOCATOR = By.xpath("//a[@href='/messages/sent/']");

    public DraftsFolderPage(WebDriver driver) {
        super(driver);
    }

    public void openMail(){
        driver.findElement(FILLED_ADDRESSEE_LOCATOR).click();
    }

    public void sendMail(){
        driver.findElement(SEND_BUTTON_LOCATOR).click();
    }

    public void openDraftsFolder(){
        driver.findElement(DRAFTS_FOLDER_LOCATOR).click();
    }

    public void openSentFolder(){
        driver.findElement(SENT_FOLDER_LOCATOR).click();
    }
}
