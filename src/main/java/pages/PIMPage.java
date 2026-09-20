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
            By.xpath("//a[contains(@href,'/pim/addEmployee')]");

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

        String addEmployeeUrl =
                "https://opensource-demo.orangehrmlive.com/web/index.php/pim/addEmployee";

        driver.get(addEmployeeUrl);

        wait.until(
                ExpectedConditions.urlContains(
                        "/pim/addEmployee"
                )
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        addEmployeeHeading
                )
        );
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