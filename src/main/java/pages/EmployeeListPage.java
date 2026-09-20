package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeeListPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private String currentEmployeeId;

    private By employeeListMenu =
            By.xpath("//*[normalize-space()='Employee List']");

    private By employeeIdField =
            By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");

    private By searchButton =
            By.xpath("//button[@type='submit']");

    public EmployeeListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickEmployeeList() {

        wait.until(
                ExpectedConditions.elementToBeClickable(employeeListMenu)
        ).click();

        wait.until(
                ExpectedConditions.urlContains("/pim/viewEmployeeList")
        );
    }

    public void enterEmployeeId(String employeeId) {

        currentEmployeeId = employeeId;

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(employeeIdField)
        ).clear();

        driver.findElement(employeeIdField)
                .sendKeys(employeeId);
    }

    public void clickSearch() {

        wait.until(
                ExpectedConditions.elementToBeClickable(searchButton)
        ).click();
    }

    public boolean isEmployeeDisplayed() {

        By employeeIdText = By.xpath(
                "//div[contains(@class,'oxd-table-body')]" +
                        "//div[contains(normalize-space(),'" +
                        currentEmployeeId +
                        "')]"
        );

        try {

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            employeeIdText
                    )
            );

            System.out.println(
                    "Employee found with Employee ID: "
                            + currentEmployeeId
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Employee not found with Employee ID: "
                            + currentEmployeeId
            );

            return false;
        }
    }

    public void searchEmployeeById(String employeeId) {

        clickEmployeeList();

        enterEmployeeId(employeeId);

        clickSearch();
    }

    public void clickEditEmployeeById(String employeeId) {

        By editButton = By.xpath(
                "//div[contains(@class,'oxd-table-body')]" +
                        "//div[@role='row']" +
                        "[.//div[normalize-space()='" + employeeId + "']]" +
                        "//button[1]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(editButton)
        ).click();
    }
    public void deleteEmployeeById(String employeeId) {

        By deleteButton = By.xpath(
                "//div[contains(@class,'oxd-table-body')]" +
                        "//div[@role='row']" +
                        "[.//div[normalize-space()='" + employeeId + "']]" +
                        "//button[contains(@class,'oxd-table-cell-action-space')][2]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(deleteButton)
        ).click();

        // Confirm deletion
        By confirmDeleteButton = By.xpath(
                "//button[normalize-space()='Yes, Delete']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(confirmDeleteButton)
        ).click();
    }

    public boolean isEmployeeNotDisplayed() {

        By employeeRowById = By.xpath(
                "//div[contains(@class,'oxd-table-body')]" +
                        "//div[@role='row']" +
                        "[.//div[normalize-space()='" + currentEmployeeId + "']]"
        );

        try {
            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            employeeRowById
                    )
            );

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}