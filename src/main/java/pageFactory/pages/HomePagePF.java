package pageFactory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePagePF extends AbstractPagePF {

    @FindBy(id = "mailbox:login")
    private static WebElement username;

    @FindBy(id = "mailbox:password")
    private static WebElement password;

    @FindBy(xpath = "//*[@id='mailbox:submit']/input")
    private static WebElement signInButton;

    @FindBy(xpath = "//*[@id='mailbox:domain']/option[4]")
    private static WebElement domain;

    public HomePagePF(WebDriver driver) {
        super(driver);
    }

    public HomePagePF open(){
        driver.get("https://mail.ru");
        return this;
    }

    public HomePagePF fillUsername(String login){
        username.sendKeys(login);
        return this;
    }

    public HomePagePF fillPassword(String pass){
        password.sendKeys(pass);
        return this;
    }

    public HomePagePF chooseDomain(){
        domain.click();
        return this;
    }

    public InboxPagePF signIn(){
        signInButton.click();
        return new InboxPagePF(driver);
    }
}
