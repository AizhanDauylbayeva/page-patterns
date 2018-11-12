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
    private WebDriver driver;
    private DraftsFolderPage drafts;
   // private List<Mail> mail = new ArrayList<Mail>();
    Mail mail = new Mail("ayzhan7797@mail.ru", "test(module 4.2)", "Hello!");
    private User user = new User("new_account_2018", "password2018");
    private SentFolderPage sentPage;

    @BeforeClass(description = "Start browser")
    private void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        drafts = new DraftsFolderPage(driver);
        sentPage = new SentFolderPage(driver);
       // mail.add(letter1);
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
        CreateNewMailPage newMail = new CreateNewMailPage(driver);
        newMail.fillAddressee(mail.getAddressee());
        newMail.fillSubject(mail.getSubject());
        newMail.fillBody(mail.getBody());
        newMail.saveDraft();
        newMail.getSaved();
        Assert.assertTrue(newMail.getSaved().isDisplayed());
        newMail.openDraftsFolder();
    }

    @Test(dependsOnMethods = "saveNewMailTest")
    public void testAddressee() {
        List<WebElement> addr = drafts.getAddrList();
        boolean addressee = true;
        for (WebElement select : addr) {
            addressee = (select.getText().contains(mail.getAddressee()));
        }
        Assert.assertTrue(addressee);
    }

    @Test(dependsOnMethods = "testAddressee")
    public void testSubject() {
//      Assert.assertTrue(drafts.getFilledSubj().isEnabled());
        Assert.assertTrue(drafts.getFilledSubj().getText().contains(mail.getSubject()));
    }

    @Test(dependsOnMethods = "testSubject")
    public void testContent() {
        driver.switchTo().frame(0);
        Assert.assertTrue(drafts.getFilledBody().isDisplayed());
        driver.switchTo().defaultContent();
    }

    @Test(dependsOnMethods = "testContent")
    public void sendMailTest() {
        driver.switchTo().defaultContent();
        drafts.sendMail();
        drafts.openDraftsFolder();
        driver.navigate().refresh();
        List<WebElement> selects = drafts.getDatalist();
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
        driver.navigate().refresh();
        List<WebElement> selects = sentPage.getSentList();
        boolean subj = true;
            for (WebElement select : selects) {
                subj = (select.getText().contains(mail.getSubject()));
            }
        Assert.assertTrue(subj);
    }

    @AfterClass
    private void tearDown() {
        sentPage.logout();
        driver.close();
    }
}
