package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DraftsFolderPage extends AbstractPage {

  /*  @FindBy(xpath = "//div[@class='b-datalist__item__addr']")
    private WebElement draftsList;*/

    @FindBy(xpath = "//div[@class='label-input']//span[@data-text]")
            //"//div[@class='label-input']//span[@data-text]")
            //"//div[@class='b-datalist__item__addr']") and contains(string(), 'ayzhan7797@mail.ru')]")
    private WebElement filledAddr;

    @FindBy(xpath = "//a[@data-subject")
    private WebElement filledSubj;
////input[@name='Subject']
    @FindBy(xpath = "//*[@class = 'js-helper js-readmsg-msg' and contains(string(), 'Hello!')]")
    private WebElement filledBody;

    @FindBy(xpath = "//div[@data-name='send']/span")
    private WebElement sendButton;

    @FindBy(xpath = "//a[@data-mnemo='drafts']")
    private WebElement draftsFolderButton;

    @FindBy(xpath = "//*[@class='b-datalist b-datalist_letters b-datalist_letters_to']//div[@class='b-datalist__item__subj']")
    private List<WebElement> datalist;

    @FindBy(xpath = "//a[@href='/messages/sent/']")
    private WebElement sentFolderButton;

    public DraftsFolderPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getFilledAddr() {
        return filledAddr;
    }

    public WebElement getFilledSubj() {
        return filledSubj;
    }

    public WebElement getFilledBody() {
        return filledBody;
    }

    public List<WebElement> getDatalist() {
        return datalist;
    }

    public void openMail() {
        filledAddr.click();
    }

    public void sendMail() {
        sendButton.click();
    }

    public void openDraftsFolder() {
        draftsFolderButton.click();
    }

    public void openSentFolder() {
        sentFolderButton.click();
    }

}
