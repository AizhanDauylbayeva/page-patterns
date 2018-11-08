package pageFactory;

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
    private WebDriver driver;
    private DraftsFolderPage drafts;
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

    }

    @AfterClass
    private void tearDown() {
        SentFolderPage sentPage = new SentFolderPage(driver);
        sentPage.logout();
        driver.close();
    }

    @Test(description = "Login test")
    public void loginTest() {
        InboxPage inbox = new HomePage(driver).open().fillUsername(user.getUsername()).fillPassword(user.getPass()).chooseDomain().signIn();
        inbox.waitForUserEmail();
        Assert.assertTrue(InboxPage.getUserEmail().isDisplayed());
        inbox.openWriteNewMail();
    }

    @Test(dependsOnMethods = "loginTest")
    public void saveNewMailTest() {
        CreateNewMailPage newMail = new CreateNewMailPage(driver);
        newMail.fillAddressee(mail.getAddressee());
        newMail.fillSubject(mail.getSubject());
        newMail.fillBody(mail.getBody());
        newMail.saveDraft();
        Assert.assertTrue(CreateNewMailPage.getSaved().isDisplayed());
        newMail.openDraftsFolder();
    }

    @Test(dependsOnMethods = "saveNewMailTest")
    public void testAddressee() {
        Assert.assertTrue(DraftsFolderPage.getFilledAddr().isDisplayed());
    }

    @Test(dependsOnMethods = "testAddressee")
    public void testSubject() {
        drafts.openMail();
        Assert.assertTrue(DraftsFolderPage.getFilledSubj().isEnabled());
    }

    @Test(dependsOnMethods = "testSubject")
    public void testContent() {
        driver.switchTo().frame(0);
        Assert.assertTrue(DraftsFolderPage.getFilledBody().isDisplayed());
        driver.switchTo().defaultContent();
    }

    @Test(dependsOnMethods = "testContent")
    public void sendMailTest() {
        driver.switchTo().defaultContent();
        drafts.sendMail();
        drafts.openDraftsFolder();
        driver.navigate().refresh();
        List<WebElement> selects = DraftsFolderPage.getDatalist();
        boolean subj = false;
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
        List<WebElement> selects = SentFolderPage.getSentList();
        boolean subj = true;
        try {
            for (WebElement select : selects) {
                subj = (select.getText().contains(mail.getSubject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(subj);
    }
}
