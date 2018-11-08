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

    @AfterClass
    private void tearDown() {
        SentFolderPage sentPage = new SentFolderPage(driver);
        sentPage.logout();
        driver.close();
    }

    @Test(description = "Login test")
    public void loginTest() {
        InboxPage inbox = new HomePage(driver).open().inputUsername("new_account_2018").inputPassword("password2018").chooseDomain().signIn();
        //TODO: wait for presence
        Assert.assertTrue(inbox.isElementPresent(InboxPage.getUserEmailLocator()));
        inbox.openWriteNewMail();
    }

    @Test(dependsOnMethods = "loginTest")
    public void saveNewMailTest() {
        newMail.fillAddressee(newMail.getAddressee());
        newMail.fillSubject(newMail.getSubject());
        newMail.fillBody(newMail.getBody());
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
        try {
            for (WebElement select : selects) {
                subj = (select.getText().contains(newMail.getSubject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(subj);
    }

    @Test(dependsOnMethods = "sendMailTest")
    public void sentFolderTest() {
        drafts.openSentFolder();
        List<WebElement> sent = driver.findElements(SentFolderPage.getSentlistLocator());
        boolean subj = true;
        try {
            for (WebElement select : sent) {
                subj = (select.getText().contains(newMail.getSubject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(subj);
    }
}
