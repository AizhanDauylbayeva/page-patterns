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

    @FindBy(xpath = ".//div[@class='b-datalist__item__subj']")
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

    public List<WebElement> getSubjList() {
        return subjList;
    }


    public List<WebElement> getBodyList() {
        return bodyList;
    }

    public void openDraftsFolder() {
        draftsFolderButton.click();
    }

    public void openSentFolder() {
        sentFolderButton.click();
    }

    public List<Mail> getMails() {
        List<Mail> results = new ArrayList<Mail>();
        for (WebElement mail : mails) {
            String addressee = mail.findElement(By.xpath(".//div[@class='b-datalist__item__addr']")).getText();
            String subject = mail.findElement(By.xpath(".//div[@class='b-datalist__item__subj']")).getText();
            String body = mail.findElement(By.xpath(".//*[@class='b-datalist__item__subj__snippet']")).getText();
            results.add(new Mail(addressee, subject, body));
        }
        return results;
    }
}
