package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");

    // More flexible Dashboard locator
    private By dashboardText =
            By.xpath("//h6[contains(normalize-space(),'Dashboard')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterUsername(String username) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameField)
        ).clear();

        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        ).clear();

        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    public boolean isDashboardDisplayed() {
        try {
            wait.until(
                    ExpectedConditions.urlContains("/dashboard")
            );

            return driver.getCurrentUrl().contains("/dashboard");

        } catch (Exception e) {
            System.out.println("Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}