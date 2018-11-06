package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateNewMailPage extends AbstractPage {
    private static final By ADDRESSEE_LOCATOR = By.xpath("//textarea[@data-original-name='To']");
    private static final By SUBJECT_LOCATOR = By.xpath("//input[@class='b-input' ]");
    private static final By BODY_LOCATOR = By.cssSelector("#tinymce");
    private static final By SAVE_DRAFT_BUTTON_LOCATOR = By.xpath("//div[@data-name='saveDraft']");
    public static final By SAVED_LOCATOR = By.xpath("//a[@class='toolbar__message_info__link']");
    private static final By DRAFT_FOLDER_LOCATOR = By.xpath("//div[@class='b-toolbar__message']/a");

    public CreateNewMailPage(WebDriver driver) {
        super(driver);
    }

    public void fillAddressee(String addressee){
        waitForElementPresent(ADDRESSEE_LOCATOR);
        driver.findElement(ADDRESSEE_LOCATOR).sendKeys(addressee);
    }

    public void fillSubject(String subject){
        driver.findElement(SUBJECT_LOCATOR).sendKeys(subject);
    }

    public void fillBody(String body){
        driver.switchTo().frame(0);
        driver.findElement(BODY_LOCATOR).sendKeys(body);
        driver.switchTo().defaultContent();
    }

    public CreateNewMailPage saveDraft (){
        driver.findElement(SAVE_DRAFT_BUTTON_LOCATOR).click();
        return this;
    }

    public DraftsFolderPage openDraftsFolder(){
        driver.findElement(DRAFT_FOLDER_LOCATOR).click();
        return new DraftsFolderPage(driver);
    }
}
