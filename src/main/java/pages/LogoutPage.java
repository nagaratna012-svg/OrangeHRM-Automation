package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By userDropdown =
            By.cssSelector("span.oxd-userdropdown-tab");

    private By logoutLink =
            By.xpath("//a[normalize-space()='Logout']");

    private By usernameField =
            By.name("username");

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void logout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(userDropdown)
        ).click();

        wait.until(
                ExpectedConditions.elementToBeClickable(logoutLink)
        ).click();
    }

    public boolean isLoggedOut() {

        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            usernameField
                    )
            ).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }
}