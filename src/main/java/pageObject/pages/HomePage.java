package pageObject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage{
    private static final By USERNAME_LOCATOR = By.id("mailbox:login");
    private static final By PASSWORD_LOCATOR = By.id("mailbox:password");
    private static final By LOGIN_BUTTON_LOCATOR = By.xpath("//*[@id='mailbox:submit']/input");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage inputUsername(String name) {
        driver.findElement(USERNAME_LOCATOR).sendKeys(name);
        return this;
    }

    public HomePage inputPassword(String pass) {
        driver.findElement(PASSWORD_LOCATOR).sendKeys(pass);
        return this;
    }

    public HomePage chooseDomain() {
        driver.findElement(By.xpath("//*[@id='mailbox:domain']/option[4]")).click();
        return this;
    }

    public InboxPage startSearch() {
        driver.findElement(LOGIN_BUTTON_LOCATOR).click();
        return new InboxPage(driver);
    }

    public HomePage open() {
        driver.get("https://mail.ru/");
        return this;
    }
}
