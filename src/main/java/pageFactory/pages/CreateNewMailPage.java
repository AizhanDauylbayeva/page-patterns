package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateNewMailPage extends AbstractPage {

    @FindBy(xpath = "//textarea[@data-original-name='To']")
    private static WebElement addresseeTextArea;

    @FindBy(xpath = "//input[@class='b-input' ]")
    private static WebElement subjectTextArea;

    @FindBy(css = "#tinymce")
    private static WebElement bodyLocator;

    @FindBy(xpath = "//div[@data-name='saveDraft']")
    private static WebElement saveDraftButton;

    @FindBy(xpath = "//a[@class='toolbar__message_info__link']")
    private static WebElement saved;

    @FindBy(xpath = "//div[@class='b-toolbar__message']/a")
    private WebElement draftsFolderButton;

    public CreateNewMailPage(WebDriver driver) {
        super(driver);
    }

    public static WebElement getSaved() {
        return saved;
    }

    public void fillAddressee(String addr){
        addresseeTextArea.sendKeys(addr);
    }

    public void fillSubject(String subj){
        subjectTextArea.sendKeys(subj);
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
