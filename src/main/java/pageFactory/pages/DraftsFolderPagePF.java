package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DraftsFolderPagePF extends AbstractPagePF {

    @FindBy(xpath = "//div[@class='b-datalist__item__addr' and contains(string(), 'ayzhan7797@mail.ru')]")
    public static WebElement filled_addr;

    @FindBy(xpath = "//a[@data-subject = 'test(module 5)']")
    public static WebElement filled_subj;

    @FindBy(xpath = "//*[@class = 'js-helper js-readmsg-msg' and contains(string(), 'Hello!')]")
    public static WebElement filled_body;

    @FindBy(xpath = "//div[@data-name='send']/span")
    private static WebElement send_button;

    @FindBy(xpath = "//a[@data-mnemo='drafts']")
    private static WebElement drafts_folder_button;

    @FindBy(xpath = "//*[@class='b-datalist b-datalist_letters b-datalist_letters_to']//div[@class='b-datalist__item__subj']")
    public static WebElement datalist;

    @FindBy(xpath = "//a[@href='/messages/sent/']")
    private static WebElement sent_folder_button;

    public DraftsFolderPagePF(WebDriver driver) {
        super(driver);
    }

    public void openMail(){
        filled_addr.click();
    }

    public void sendMail(){
        send_button.click();
    }

    public void openDraftsFolder(){
        drafts_folder_button.click();
    }

    public void openSentFolder(){
        sent_folder_button.click();
    }

}