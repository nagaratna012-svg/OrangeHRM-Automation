package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PIMPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By pimMenu =
            By.xpath("//span[normalize-space()='PIM']");

    private By addEmployeeMenu =
            By.xpath("//*[normalize-space()='Add Employee']");

    private By addEmployeeHeading =
            By.xpath("//h6[normalize-space()='Add Employee']");

    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickPIM() {
        wait.until(
                ExpectedConditions.elementToBeClickable(pimMenu)
        ).click();

        wait.until(
                ExpectedConditions.urlContains("/pim/")
        );
    }

    public void clickAddEmployee() {
        wait.until(
                ExpectedConditions.elementToBeClickable(addEmployeeMenu)
        ).click();
    }

    public boolean isAddEmployeePageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(addEmployeeHeading)
        ).isDisplayed();
    }

    public void navigateToAddEmployee() {
        clickPIM();
        clickAddEmployee();
    }
}