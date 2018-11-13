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
    private Mail secondMail = new Mail("<Не указано>", "<Без темы>", "");
    private User user = new User("new_account_2018", "password2018");
    private SentFolderPage sentPage;
    private List<WebElement> draftsList;
    private CreateNewMailPage newMail;

    @BeforeClass
    private void init() {
        newMail = new CreateNewMailPage(driver);
        draftsFolderPage = new DraftsFolderPage(driver);
        draftsList = draftsFolderPage.getSubjList();
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
    public void testAddressee() {
        newMail.openDraftsFolder();
        driver.navigate().to(driver.getCurrentUrl());
        List<Mail> draftMails = draftsFolderPage.getMails();
        boolean addr = true;
        for (Mail draftMail : draftMails) {
            addr = draftMail.getSubject().equals(mail.getAddressee());
        }
        Assert.assertTrue(addr);
    }

    /*@Test(dependsOnMethods = "testAddressee")
    public void testSubject() {
        List<Mail> draftMails = draftsFolderPage.getMails();
        boolean subject = true;
        for (Mail draftMail : draftMails) {
            subject = draftMail.getSubject().equals(mail.getSubject());
        }
        Assert.assertTrue(subject);
    }

    @Test(dependsOnMethods = "testSubject")
    public void testContent() {
        List<Mail> draftMails = draftsFolderPage.getMails();
        boolean body = true;
        for (Mail draftMail : draftMails) {
            body = draftMail.getSubject().equals(mail.getBody());
        }
        Assert.assertTrue(body);
    }*/

    @Test(dependsOnMethods = "testContent")
    public void sendMailTest() {
        draftsList.get(0).click();
        driver.navigate().refresh();
        newMail.sendMail();
        draftsFolderPage.openDraftsFolder();
        driver.navigate().refresh();
        boolean subj = false;
        subj = draftsList.get(0).getText().contains(mail.getSubject());
        Assert.assertFalse(subj);
    }

    @Test(dependsOnMethods = "sendMailTest")
    public void sentFolderTest() {
        draftsFolderPage.openSentFolder();
        driver.navigate().refresh();
        List<WebElement> sentList = sentPage.getSentList();
        boolean subj = true;
        subj = sentList.get(0).getText().contains(mail.getSubject());
        Assert.assertTrue(subj);
    }

    @AfterClass
    private void tearDown() {
        sentPage.logout();
        driver.close();
    }
}
