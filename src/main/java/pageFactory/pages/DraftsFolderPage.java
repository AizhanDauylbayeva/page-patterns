package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DraftsFolderPage extends AbstractPage {

    @FindBy(xpath = "//div[@class='b-datalist__item__addr' and contains(string(), 'ayzhan7797@mail.ru')]")
    private static WebElement filledAddr;

    @FindBy(xpath = "//a[@data-subject = 'test(module 4.2)']")
    private static WebElement filledSubj;

    @FindBy(xpath = "//*[@class = 'js-helper js-readmsg-msg' and contains(string(), 'Hello!')]")
    private static WebElement filledBody;

    @FindBy(xpath = "//div[@data-name='send']/span")
    private static WebElement sendButton;

    @FindBy(xpath = "//a[@data-mnemo='drafts']")
    private static WebElement draftsFolderButton;

    @FindBy(xpath = "//*[@class='b-datalist b-datalist_letters b-datalist_letters_to']//div[@class='b-datalist__item__subj']")
    private static List<WebElement> datalist;

    @FindBy(xpath = "//a[@href='/messages/sent/']")
    private static WebElement sentFolderButton;

    public DraftsFolderPage(WebDriver driver) {
        super(driver);
    }

    public static WebElement getFilledAddr() {
        return filledAddr;
    }

    public static WebElement getFilledSubj() {
        return filledSubj;
    }

    public static WebElement getFilledBody() {
        return filledBody;
    }

    public static List<WebElement> getDatalist() {
        return datalist;
    }

    public void openMail(){
        filledAddr.click();
    }

    public void sendMail(){
        sendButton.click();
    }

    public void openDraftsFolder(){
        draftsFolderButton.click();
    }

    public void openSentFolder(){
        sentFolderButton.click();
    }

}
