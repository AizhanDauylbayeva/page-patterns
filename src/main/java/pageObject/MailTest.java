package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObject.pages.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailTest {
    private WebDriver driver;
    private DraftsFolderPage drafts;
    private CreateNewMailPage newMail;
    private Mail mail = new Mail("ayzhan7797@mail.ru", "test(module 4.2)", "Hello!");
    private User user = new User("new_account_2018", "password2018");

    @BeforeClass(description = "Start browser")
    private void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        drafts = new DraftsFolderPage(driver);
        newMail = new CreateNewMailPage(driver);
    }

    @Test(description = "Login test")
    public void loginTest() {
        InboxPage inbox = new HomePage(driver).open().inputUsername(user.getUsername()).inputPassword(user.getPass()).chooseDomain().signIn();
        //TODO: wait for presence
        Assert.assertTrue(inbox.isElementPresent(InboxPage.getUserEmailLocator()));
        inbox.openWriteNewMail();
    }

    @Test(dependsOnMethods = "loginTest")
    public void saveNewMailTest() {
        newMail.fillAddressee(mail.getAddressee());
        newMail.fillSubject(mail.getSubject());
        newMail.fillBody(mail.getBody());
        newMail.saveDraft();
        Assert.assertTrue(newMail.isElementPresent(CreateNewMailPage.getSavedLocator()));
        newMail.openDraftsFolder();
    }

    @Test(dependsOnMethods = "saveNewMailTest")
    public void testAddressee() {
        Assert.assertTrue(drafts.isElementPresent(DraftsFolderPage.getFilledAddresseeLocator()));
    }

    @Test(dependsOnMethods = "testAddressee")
    public void testSubject() {
        drafts.openMail();
        Assert.assertTrue(drafts.isElementPresent(DraftsFolderPage.getFilledSubjectLocator()));
    }

    @Test(dependsOnMethods = "testSubject")
    public void testContent() {
        driver.switchTo().frame(0);
        Assert.assertTrue(drafts.isElementPresent(DraftsFolderPage.getFilledBodyLocator()));
    }

    @Test(dependsOnMethods = "testContent")
    public void sendMailTest() {
        driver.switchTo().defaultContent();
        drafts.sendMail();
        drafts.openDraftsFolder();
        driver.navigate().refresh();
        List<WebElement> selects = driver.findElements(DraftsFolderPage.getDatalistLocator());
        boolean subj = false;
        for (WebElement select : selects) {
            System.out.println(select);
        }
        try {
            for (WebElement select : selects) {
                subj = (select.getText().contains(mail.getSubject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(subj);
    }

    @Test(dependsOnMethods = "sendMailTest")
    public void sentFolderTest() {
        drafts.openSentFolder();
        driver.navigate().refresh();
        List<WebElement> sent = driver.findElements(SentFolderPage.getSentlistLocator());
        boolean subj = true;
        for (WebElement select : sent) {
            System.out.println();
            System.out.println(select);
        }
        try {
            for (WebElement select : sent) {
                subj = (select.getText().contains(mail.getSubject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(subj);
    }

    @AfterClass
    private void tearDown() {
        SentFolderPage sentPage = new SentFolderPage(driver);
        sentPage.logout();
        driver.close();
    }
}
