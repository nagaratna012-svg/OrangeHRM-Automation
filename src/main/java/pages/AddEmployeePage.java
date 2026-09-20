package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEmployeePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By firstNameField =
            By.name("firstName");

    private By middleNameField =
            By.name("middleName");

    private By lastNameField =
            By.name("lastName");

    private By employeeIdField =
            By.xpath(
                    "//label[text()='Employee Id']" +
                            "/following::input[1]"
            );

    private By profilePicture =
            By.cssSelector("input[type='file']");

    private By saveButton =
            By.xpath("//button[@type='submit']");

    private By personalDetailsHeading =
            By.xpath(
                    "//h6[contains(normalize-space(),'Personal Details')]"
            );


    public AddEmployeePage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }


    public void enterFirstName(String firstName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstNameField
                )
        ).clear();

        driver.findElement(firstNameField)
                .sendKeys(firstName);
    }


    public void enterMiddleName(String middleName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        middleNameField
                )
        ).clear();

        driver.findElement(middleNameField)
                .sendKeys(middleName);
    }


    public void enterLastName(String lastName) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        lastNameField
                )
        ).clear();

        driver.findElement(lastNameField)
                .sendKeys(lastName);
    }


    public void enterEmployeeId(String employeeId) {

        By formLoader =
                By.cssSelector("div.oxd-form-loader");

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        formLoader
                )
        );

        WebElement field =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                employeeIdField
                        )
                );

        field.click();

        field.sendKeys(
                Keys.CONTROL,
                "a"
        );

        field.sendKeys(
                Keys.BACK_SPACE
        );

        field.sendKeys(employeeId);
    }


    public void uploadProfilePicture(String filePath) {

        String absolutePath =
                new File(filePath)
                        .getAbsolutePath();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        profilePicture
                )
        ).sendKeys(absolutePath);
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
                    ExpectedConditions.urlContains(
                            "/pim/viewPersonalDetails"
                    )
            );

            return driver.getCurrentUrl()
                    .contains("/pim/viewPersonalDetails");

        } catch (Exception e) {

            System.out.println(
                    "Current URL after save: "
                            + driver.getCurrentUrl()
            );

            return false;
        }
    }


    /*
     * Extract OrangeHRM internal employee number
     * from the employee details URL.
     *
     * Example URL:
     * /pim/viewPersonalDetails/empNumber/123
     *
     * Returns:
     * 123
     */
    public int getEmployeeNumber() {

        String currentUrl =
                driver.getCurrentUrl();

        Pattern pattern =
                Pattern.compile(
                        "/empNumber/(\\d+)"
                );

        Matcher matcher =
                pattern.matcher(currentUrl);

        if (matcher.find()) {

            int employeeNumber =
                    Integer.parseInt(
                            matcher.group(1)
                    );

            System.out.println(
                    "OrangeHRM Employee Number: "
                            + employeeNumber
            );

            return employeeNumber;
        }

        throw new RuntimeException(
                "Unable to extract employee number from URL: "
                        + currentUrl
        );
    }


    public void addEmployee(
            String firstName,
            String middleName,
            String lastName,
            String employeeId,
            String profilePicturePath
    ) {

        enterFirstName(firstName);

        enterMiddleName(middleName);

        enterLastName(lastName);

        enterEmployeeId(employeeId);

        if (profilePicturePath != null
                && !profilePicturePath.trim().isEmpty()) {

            uploadProfilePicture(
                    profilePicturePath
            );
        }

        clickSave();
    }
}