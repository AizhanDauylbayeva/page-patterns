package pageFactory.pages;

import Entity.Mail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class SentFolderPage extends AbstractPage {

    @FindBy(xpath = ".//div[@class='b-datalist__item__panel']")
    private List<WebElement> mails;

    @FindBy(id = "PH_logoutLink")
    private WebElement logout;

    public SentFolderPage(WebDriver driver) {
        super(driver);
    }

    private List<Mail> getSentList() {
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

    public boolean isSentMailExist(Mail mail) {
        List<Mail> draftMails = getSentList();
        boolean content = false;
        for (Mail draftMail : draftMails) {
            if (draftMail.getAddressee().equals(mail.getAddressee()) &&
                    draftMail.getSubject().equals(mail.getSubject()) &&
                    draftMail.getBody().contains(mail.getBody())) {
                content = true;
                break;
            }
        }
        return content;
    }

    public void logout() {
        logout.click();
    }
}
