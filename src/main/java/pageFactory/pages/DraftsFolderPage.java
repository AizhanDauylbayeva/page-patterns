package pageFactory.pages;

import Entity.Mail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class DraftsFolderPage extends AbstractPage {

    @FindBy(xpath = ".//div[@class='b-datalist__item__addr']")
    private List<WebElement> addrList;

    @FindBy(xpath = ".//div[@class='b-datalist__item__subj']/text()")
    private List<WebElement> subjList;

    @FindBy(xpath = ".//*[@class='b-datalist__item__subj__snippet']")
    private List<WebElement> bodyList;

    @FindBy(xpath = ".//div[@class='b-datalist__item__panel']")
    private List<WebElement> mails;

    @FindBy(xpath = "//a[@data-mnemo='drafts']")
    private WebElement draftsFolderButton;

    @FindBy(xpath = "//a[@href='/messages/sent/']")
    private WebElement sentFolderButton;

    public DraftsFolderPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAddrList() {
        return addrList;
    }

    public void openDraftsFolder() {
        draftsFolderButton.click();
    }

    public void openSentFolder() {
        sentFolderButton.click();
    }

    public List<Mail> getSavedMailsText() {
        List<Mail> results = new ArrayList<Mail>();
        for (WebElement mail : mails) {
            String addressee = mail.findElement(By.xpath(".//div[@class='b-datalist__item__addr']")).getText();
            String div = mail.findElement(By.xpath(".//div[@class='b-datalist__item__subj']")).getText();
            String span = mail.findElement(By.xpath(".//div[@class='b-datalist__item__subj']/span")).getText();
            int index = div.indexOf(span);
            String subject = div.substring(0, index);
            String body = mail.findElement(By.xpath(".//*[@class='b-datalist__item__subj__snippet']")).getText();
            results.add(new Mail(addressee, subject, body));
        }
        return results;
    }

/*    public List<Mail> getSavedMails() {
        List<Mail> results = new ArrayList<Mail>();
        for (WebElement mail : mails) {
            WebElement addressee = mail.findElement(By.xpath(".//div[@class='b-datalist__item__addr']"));
            WebElement subject = mail.findElement(By.xpath(".//div[@class='b-datalist__item__subj']"));
        }
        return results;
    }*/

    public void openSavedMail(){
        getAddrList().get(0).click();
    }
}
