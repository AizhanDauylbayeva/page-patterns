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

    @FindBy(xpath = "//a[@data-mnemo='drafts']")
    private WebElement draftsFolderButton;

    @FindBy(xpath = ".//div[@class='b-datalist__item__subj']")
    private List<WebElement> datalist;


    @FindBy(xpath = ".//div[@class='b-datalist__item__panel']")
    private List<WebElement> mails;

    @FindBy(xpath = "//a[@href='/messages/sent/']")
    private WebElement sentFolderButton;

    public DraftsFolderPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAddrList() {
        return addrList;
    }

    public List<WebElement> getDatalist() {
        return datalist;
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
            String address = mail.findElement(By.xpath(".//div[@class='b-datalist__item__addr']")).getText();
            String subject = mail.findElement(By.xpath(".//div[@class='b-datalist__item__subj']")).getText();

            results.add(new Mail(address, subject, null));
        }
        return results;
    }
}
