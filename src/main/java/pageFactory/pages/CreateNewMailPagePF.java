package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObject.pages.DraftsFolderPage;

public class CreateNewMailPagePF extends AbstractPagePF{

    @FindBy(xpath = "//textarea[@data-original-name='To']")
    private WebElement addressee;

    @FindBy(xpath = "//input[@class='b-input' ]")
    private WebElement subject;

    @FindBy(css = "#tinymce")
    private WebElement body;

    @FindBy(xpath = "//div[@data-name='saveDraft']")
    private WebElement saveDraft_Button;

    @FindBy(xpath = "//a[@class='toolbar__message_info__link']")
    WebElement saved;

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

    public DraftsFolderPagePF openDraftsFolder(){
        draftsFolder_Button.click();
        return new DraftsFolderPagePF(driver);
    }
}
