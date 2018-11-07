package pageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageFactory.pages.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailTestPF {
    private WebDriver driver;
    private DraftsFolderPagePF drafts;

    @BeforeClass(description = "Start browser")
    private void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        drafts = new DraftsFolderPagePF(driver);
    }

    @Test(description = "Login test")
    public void loginTest() {
        InboxPagePF inbox = new HomePagePF(driver).open().fillUsername("new_account_2018").fillPassword("password2018").chooseDomain().signIn();
        inbox.waitForUserEmail();
        Assert.assertTrue(InboxPagePF.user_email.isDisplayed());
        inbox.openWriteNewMail();
    }

    @Test(dependsOnMethods = "loginTest")
    public void saveNewMailTest() {
        CreateNewMailPagePF newMail = new CreateNewMailPagePF(driver);
        newMail.fillAddressee("ayzhan7797@mail.ru");
        newMail.fillSubject("test(module 5)");
        newMail.fillBody("Hello!");
        newMail.saveDraft();
        Assert.assertTrue(CreateNewMailPagePF.saved.isDisplayed());
        newMail.openDraftsFolder();
    }

    @Test(dependsOnMethods = "saveNewMailTest")
    public void testAddressee() {
        Assert.assertTrue(DraftsFolderPagePF.filled_addr.isDisplayed());
    }

    @Test(dependsOnMethods = "testAddressee")
    public void testSubject() {
        drafts.openMail();
        Assert.assertTrue(DraftsFolderPagePF.filled_subj.isEnabled());
    }

    @Test(dependsOnMethods = "testSubject")
    public void testContent() {
        driver.switchTo().frame(0);
        Assert.assertTrue(DraftsFolderPagePF.filled_body.isDisplayed());
        driver.switchTo().defaultContent();
    }

    @Test(dependsOnMethods = "testContent")
    public void sendMailTest() {
        drafts.sendMail();
        drafts.openDraftsFolder();
        WebElement select = DraftsFolderPagePF.datalist;
        List<WebElement> subjects = select.findElements(By.tagName("Subject"));
        try {
            for (WebElement subject : subjects) {
                boolean subj = ("test(module 5)".equals(subject.getText()));
                Assert.assertFalse(subj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test(dependsOnMethods = "sendMailTest")
    public void sentFolderTest() {
        SentFolderPagePF sentPage = new SentFolderPagePF(driver);
        drafts.openSentFolder();
        WebElement select = SentFolderPagePF.sent_list;
        List<WebElement> subjects = select.findElements(By.tagName("Subject"));
        try {
            for (WebElement subject : subjects) {
                boolean subj = ("test(module 5)".equals(subject.getText()));
                Assert.assertTrue(subj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sentPage.logout();
        driver.close();
    }
}
