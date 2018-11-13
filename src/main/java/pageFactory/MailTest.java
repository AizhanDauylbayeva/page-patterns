package pageFactory;
import Entity.Mail;
import Entity.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageFactory.pages.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailTest {
    private DraftsFolderPage draftsFolderPage;
    private Mail mail = new Mail("ayzhan7797@mail.ru", "test(module 4.2)", "Hello!");
    private User user = new User("new_account_2018", "password2018");
    private SentFolderPage sentPage;
    private CreateNewMailPage newMail;

    @BeforeClass(description = "Start browser")
    private void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        newMail = new CreateNewMailPage(driver);
        draftsFolderPage = new DraftsFolderPage(driver);
        draftsList = draftsFolderPage.getDatalist();
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
       // newMail.getSaved();
       // Assert.assertTrue(newMail.getSaved().isDisplayed());
        newMail.openDraftsFolder();
    }

    @Test(dependsOnMethods = "saveNewMailTest")
    public void testAddressee() {
        driver.navigate().to(driver.getCurrentUrl());
        List<WebElement> addr = draftsFolderPage.getAddrList();
        boolean addressee = true;
        addressee = addr.get(0).getText().contains(mail.getAddressee());
        Assert.assertTrue(addressee);
    }

    @Test(dependsOnMethods = "testAddressee")
    public void testSubject() {
        List<Mail> draftMails = draftsFolderPage.getMails();
        for (Mail draftMail : draftMails) {
            draftMail.getSubject().equals(mail.getSubject());
        }
        boolean subj = true;
        for (WebElement subgect: draftsList){
            System.out.println(subgect);
        }
        subj = draftsList.get(0).getText().contains(mail.getSubject());
        Assert.assertTrue(subj);
    }

    @Test(dependsOnMethods = "testSubject")
    public void testContent() {
        boolean body = true;
        body = draftsList.get(0).getText().contains(mail.getBody());
        Assert.assertTrue(body);
    }

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
