package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SentFolderPagePF extends AbstractPagePF{

    @FindBy(xpath = "//div[@class='b-datalist b-datalist_letters b-datalist_letters_to']//div[@class='b-datalist__item__subj']")
    public static List<WebElement> sent_list;

    @FindBy(id = "PH_logoutLink")
    private static WebElement logout;

    public SentFolderPagePF(WebDriver driver) {
        super(driver);
    }

    public void logout(){
        logout.click();
    }
}
