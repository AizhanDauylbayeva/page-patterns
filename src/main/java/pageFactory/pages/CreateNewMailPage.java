package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateNewMailPage extends AbstractPage {

    @FindBy(xpath = "//textarea[@data-original-name='To']")
    private static WebElement addresseeLocator;

    @FindBy(xpath = "//input[@class='b-input' ]")
    private static WebElement subjectLocator;

    @FindBy(css = "#tinymce")
    private static WebElement bodyLocator;

    @FindBy(xpath = "//div[@data-name='saveDraft']")
    private static WebElement saveDraftButton;

    @FindBy(xpath = "//a[@class='toolbar__message_info__link']")
    private static WebElement saved;

    @FindBy(xpath = "//div[@class='b-toolbar__message']/a")
    private WebElement draftsFolderButton;

    private String addressee = "ayzhan7797@mail.ru";
    private String subject = "test(module 4.2)";
    private String body = "Hello!";

    public CreateNewMailPage(WebDriver driver) {
        super(driver);
    }

    public String getAddressee() {
        return addressee;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public static WebElement getSaved() {
        return saved;
    }

    public void fillAddressee(String addr){
        addresseeLocator.sendKeys(addr);
    }

    public void fillSubject(String subj){
        subjectLocator.sendKeys(subj);
    }

    public void fillBody(String content){
        driver.switchTo().frame(0);
        bodyLocator.sendKeys(content);
        driver.switchTo().defaultContent();
    }

    public void saveDraft (){
        saveDraftButton.click();
    }

    public void openDraftsFolder(){
        draftsFolderButton.click();
    }
}
