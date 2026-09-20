package pages;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class AddEmployeePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Employee fields
    private By firstNameField = By.name("firstName");

    private By middleNameField = By.name("middleName");

    private By lastNameField = By.name("lastName");

    private By employeeIdField =
            By.xpath("//label[text()='Employee Id']/following::input[1]");

    // Profile picture
    private By profilePicture =
            By.cssSelector("input[type='file']");

    // Save button
    private By saveButton =
            By.xpath("//button[@type='submit']");

    // Success / employee details
    private By personalDetailsHeading =
            By.xpath("//h6[contains(normalize-space(),'Personal Details')]");

    public AddEmployeePage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    public void enterFirstName(String firstName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField
                )
        ).sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {

        driver.findElement(middleNameField)
                .sendKeys(middleName);
    }

    public void enterLastName(String lastName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        lastNameField
                )
        ).sendKeys(lastName);
    }



    public void enterEmployeeId(String employeeId) {

        // Wait for OrangeHRM form loader to disappear
        By formLoader = By.cssSelector("div.oxd-form-loader");

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(formLoader)
        );

        WebElement field = wait.until(
                ExpectedConditions.elementToBeClickable(employeeIdField)
        );

        field.click();

        field.sendKeys(Keys.CONTROL, "a");
        field.sendKeys(Keys.BACK_SPACE);
        field.sendKeys(employeeId);
    }

    public void uploadProfilePicture(String filePath) {
        String absolutePath = new java.io.File(filePath)
                .getAbsolutePath();

        driver.findElement(profilePicture)
                .sendKeys(absolutePath);
    }

    public void clickSave() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        saveButton
                )
        ).click();
    }

    public boolean isEmployeeDetailsDisplayed() {
        try {
            wait.until(
                    ExpectedConditions.urlContains("/pim/viewPersonalDetails")
            );

            return driver.getCurrentUrl().contains("/pim/viewPersonalDetails");

        } catch (Exception e) {
            System.out.println("Current URL after save: " + driver.getCurrentUrl());
            return false;
        }
    }

    public void addEmployee(
            String firstName,
            String middleName,
            String lastName,
            String employeeId,
            String profilePicturePath) {

        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
        enterEmployeeId(employeeId);

        if (profilePicturePath != null &&
                !profilePicturePath.isEmpty()) {

            uploadProfilePicture(profilePicturePath);
        }

        clickSave();
    }
}