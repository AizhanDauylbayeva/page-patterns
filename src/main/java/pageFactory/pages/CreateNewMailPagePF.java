package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateNewMailPagePF extends AbstractPagePF{

    @FindBy(xpath = "//textarea[@data-original-name='To']")
    private static WebElement addressee;

    @FindBy(xpath = "//input[@class='b-input' ]")
    private static WebElement subject;

    @FindBy(css = "#tinymce")
    private static WebElement body;

    @FindBy(xpath = "//div[@data-name='saveDraft']")
    private static WebElement saveDraft_Button;

    @FindBy(xpath = "//a[@class='toolbar__message_info__link']")
    public static WebElement saved;

    @FindBy(xpath = "//div[@class='b-toolbar__message']/a")
    private WebElement draftsFolder_Button;

    public CreateNewMailPagePF (WebDriver driver) {
        super(driver);
    }

    public void fillAddressee(String addr){
        addressee.sendKeys(addr);
    }

    public void fillSubject(String subj){
        subject.sendKeys(subj);
    }

    public void fillBody(String content){
        driver.switchTo().frame(0);
        body.sendKeys(content);
        driver.switchTo().defaultContent();
    }

    public void saveDraft (){
        saveDraft_Button.click();
    }

    public void openDraftsFolder(){
        draftsFolder_Button.click();
    }
}
