package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SentFolderPagePF extends AbstractPagePF{

    @FindBy(xpath = "//div[@class='b-datalist b-datalist_letters b-datalist_letters_to']//*[@class='b-datalist__item__subj']")
    WebElement sent_list;

    @FindBy(id = "PH_logoutLink")
    private WebElement logout;

    public SentFolderPagePF(WebDriver driver) {
        super(driver);
    }

    public void logout(){
        logout.click();
    }
}
