package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InboxPagePF extends AbstractPagePF {

    @FindBy(xpath = "//*[@id='PH_user-email']")
    public static WebElement user_email;

    @FindBy(xpath = "//*[@id='b-toolbar__left']//span")
    WebElement createNewMailButton;

    public InboxPagePF(WebDriver driver){
        super(driver);
    }

    public CreateNewMailPagePF openWriteNewMail(){
        createNewMailButton.click();
        return new CreateNewMailPagePF(driver);
    }
}
