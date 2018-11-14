package pageFactory;

import Entity.Mail;
import Entity.User;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageFactory.pages.*;

import java.util.List;

public class MailTest extends Base {
    private DraftsFolderPage draftsFolderPage;
    private Mail mail = new Mail("ayzhan7797@mail.ru", "test(module 4.2)", "Hello!");
    private Mail secondMail = new Mail("<Не указано>", "<Без темы>", "-- Name LastName");
    private User user = new User("new_account_2018", "password2018");
    private SentFolderPage sentPage;
    private CreateNewMailPage newMail;

    @BeforeClass
    private void init() {
        newMail = new CreateNewMailPage(driver);
        draftsFolderPage = new DraftsFolderPage(driver);
        sentPage = new SentFolderPage(driver);
    }

    @Test(description = "Login test")
    public void loginTest() {
        InboxPage inbox = new HomePage(driver).open().fillUsername(user.getUsername()).fillPassword(user.getPass()).chooseDomain().signIn();
        inbox.waitForUserEmail();
        Assert.assertTrue(inbox.getUserEmail().isDisplayed());
        inbox.openWriteNewMail();
    }

    @Test(dependsOnMethods = "loginTest")
    public void saveNewMailTest() {
        newMail.fillAddressee(mail.getAddressee());
        newMail.fillSubject(mail.getSubject());
        newMail.fillBody(mail.getBody());
        newMail.saveDraft();
        boolean isSaved;
        isSaved = newMail.getSaved().isDisplayed();
        Assert.assertTrue(isSaved);
    }

    @Test(dependsOnMethods = "saveNewMailTest")
    public void testContent() {
        newMail.openDraftsFolder();
        driver.navigate().to(driver.getCurrentUrl());
        List<Mail> draftMails = draftsFolderPage.getSavedMails();
        boolean content = true;
        for (int i = 0; i < draftMails.size(); i++) {
            content =  (draftMails.get(0).getAddressee().equals(mail.getAddressee()) &
                    draftMails.get(0).getSubject().equals(mail.getSubject()) &
                    draftMails.get(0).getBody().contains(mail.getBody())) ;
            }
        Assert.assertTrue(content);
    }

    @Test(dependsOnMethods = "testContent")
    public void testSecondMail() {
        List<Mail> draftMails = draftsFolderPage.getSavedMails();
        boolean content = false;
        for (Mail draftMail : draftMails) {
            if (draftMail.getAddressee().equals(secondMail.getAddressee()) &
                    draftMail.getSubject().equals(secondMail.getSubject()) &
                    draftMail.getBody().equals(secondMail.getBody())) {
                content = true;
            }
        }
        Assert.assertTrue(content);
    }

    @Test(dependsOnMethods = "testSecondMail")
    public void sendMailTest() {
        List<WebElement> draftAddrList = draftsFolderPage.getAddrList();
        draftAddrList.get(0).click();
        driver.navigate().refresh();
        newMail.sendMail();
        draftsFolderPage.openDraftsFolder();
        driver.navigate().refresh();
        List<Mail> draftMails = draftsFolderPage.getSavedMails();
        boolean content = false;
        if (draftMails.get(0).getAddressee().equals(mail.getAddressee()) &
                draftMails.get(0).getSubject().equals(mail.getSubject()) &
                draftMails.get(0).getBody().contains(mail.getBody())) {
            content = true;
        }
        Assert.assertFalse(content);
    }

    @Test(dependsOnMethods = "sendMailTest")
    public void sentFolderTest() {
        draftsFolderPage.openSentFolder();
        driver.navigate().refresh();
        List<Mail> sentList = sentPage.getSentList();
        boolean content = false;
        if (sentList.get(0).getAddressee().equals(mail.getAddressee()) &
                sentList.get(0).getSubject().equals(mail.getSubject()) &
                sentList.get(0).getBody().contains(mail.getBody())) {
            content = true;
        }
        Assert.assertTrue(content);
    }

    @AfterClass
    private void tearDown() {
        sentPage.logout();
        driver.close();
    }
}
