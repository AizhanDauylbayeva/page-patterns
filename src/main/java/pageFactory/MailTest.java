package pageFactory;

import Entity.Mail;
import Entity.User;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageFactory.pages.*;

import java.util.List;

public class MailTest extends Base {
    private InboxPage inbox;
    private Mail mail = new Mail("ayzhan7797@mail.ru", "test(module 4.2)", "Hello!");
    private Mail secondMail = new Mail("<Не указано>", "<Без темы>", "-- Name LastName");
    private User user = new User("new_account_2018", "password2018");
    private DraftsFolderPage draftsFolderPage;
    private CreateNewMailPage newMailPage;
    private SentFolderPage sentPage;

    @BeforeClass
    private void init() {
        inbox = new InboxPage(driver);
        newMailPage = new CreateNewMailPage(driver);
        draftsFolderPage = new DraftsFolderPage(driver);
        sentPage = new SentFolderPage(driver);
    }

    @Test(description = "Login test")
    public void loginTest() {
        inbox = new HomePage(driver).open().fillUsername(user.getUsername()).fillPassword(user.getPass()).chooseDomain().signIn();
        inbox.waitForUserEmail();
        Assert.assertTrue(inbox.getUserEmail().isDisplayed());
    }

    @Test(dependsOnMethods = "loginTest")
    public void saveNewMailTest() {
        inbox.openWriteNewMail();
        newMailPage.fillAddressee(mail.getAddressee());
        newMailPage.fillSubject(mail.getSubject());
        newMailPage.fillBody(mail.getBody());
        newMailPage.saveDraft();
        Assert.assertTrue(newMailPage.getSavedIdentificator());
    }

    @Test(dependsOnMethods = "saveNewMailTest")
    public void testContent()  {
        newMailPage.openDraftsFolder();
        driver.navigate().refresh();
        List<Mail> draftMails = draftsFolderPage.getSavedMailsText();
        boolean content = true;
            content =  (draftMails.get(0).getAddressee().equals(mail.getAddressee()) &
                    draftMails.get(0).getSubject().equals(mail.getSubject()) &
                    draftMails.get(0).getBody().contains(mail.getBody())) ;
        Assert.assertTrue(content);
    }

    @Test(dependsOnMethods = "testContent")
    public void testSecondMail() {
        List<Mail> draftMails = draftsFolderPage.getSavedMailsText();
        boolean content = false;
        for (Mail draftMail : draftMails) {
            if (draftMail.getAddressee().equals(secondMail.getAddressee()) &
                    draftMail.getSubject().equals(secondMail.getSubject()) &
                    draftMail.getBody().equals(secondMail.getBody())) {
                content = true;
                break;
            }
        }
        Assert.assertTrue(content);
    }

    @Test(dependsOnMethods = "testSecondMail")
    public void sendMailTest() {
        draftsFolderPage.openSavedMail();
        driver.navigate().refresh();
        newMailPage.sendMail();
        draftsFolderPage.openDraftsFolder();
        driver.navigate().refresh();
        List<Mail> draftMails = draftsFolderPage.getSavedMailsText();
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
