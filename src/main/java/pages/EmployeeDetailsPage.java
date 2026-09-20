package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeeDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By jobTab =
            By.xpath("//a[normalize-space()='Job']");

    private By formLoader =
            By.cssSelector("div.oxd-form-loader");

    private By jobTitleDropdown =
            By.xpath(
                    "//label[normalize-space()='Job Title']" +
                            "/ancestor::div[contains(@class,'oxd-input-group')]" +
                            "//div[contains(@class,'oxd-select-text')]"
            );

    private By employmentStatusDropdown =
            By.xpath(
                    "//label[normalize-space()='Employment Status']" +
                            "/ancestor::div[contains(@class,'oxd-input-group')]" +
                            "//div[contains(@class,'oxd-select-text')]"
            );

    private By saveButton =
            By.xpath("//button[@type='submit']");


    public EmployeeDetailsPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }


    public void waitForLoaderToDisappear() {

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        formLoader
                )
        );
    }


    public void clickJobTab() {

        waitForLoaderToDisappear();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        jobTab
                )
        ).click();

        waitForLoaderToDisappear();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        jobTitleDropdown
                )
        );
    }


    public void selectJobTitle(String jobTitle) {

        waitForLoaderToDisappear();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        jobTitleDropdown
                )
        ).click();

        By option = By.xpath(
                "//div[@role='option']//span[normalize-space()='" +
                        jobTitle +
                        "']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        option
                )
        ).click();

        waitForLoaderToDisappear();
    }


    public void selectEmploymentStatus(String status) {

        waitForLoaderToDisappear();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        employmentStatusDropdown
                )
        ).click();

        By option = By.xpath(
                "//div[@role='option']//span[normalize-space()='" +
                        status +
                        "']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        option
                )
        ).click();

        waitForLoaderToDisappear();
    }


    public void clickSave() {

        waitForLoaderToDisappear();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        saveButton
                )
        ).click();

        waitForLoaderToDisappear();
    }


    public boolean isJobTitleDisplayed(String jobTitle) {

        By selectedJobTitle = By.xpath(
                "//label[normalize-space()='Job Title']" +
                        "/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//div[contains(@class,'oxd-select-text')]" +
                        "//div[contains(@class,'oxd-select-text-input')]" +
                        "[normalize-space()='" + jobTitle + "']"
        );

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            selectedJobTitle
                    )
            ).isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "Job Title verification failed. Current URL: "
                            + driver.getCurrentUrl()
            );

            return false;
        }
    }


    public boolean isEmploymentStatusDisplayed(String status) {

        By selectedEmploymentStatus = By.xpath(
                "//label[normalize-space()='Employment Status']" +
                        "/ancestor::div[contains(@class,'oxd-input-group')]" +
                        "//div[contains(@class,'oxd-select-text')]" +
                        "//div[contains(@class,'oxd-select-text-input')]" +
                        "[normalize-space()='" + status + "']"
        );

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            selectedEmploymentStatus
                    )
            ).isDisplayed();

        } catch (Exception e) {

            System.out.println(
                    "Employment Status verification failed. Current URL: "
                            + driver.getCurrentUrl()
            );

            return false;
        }
    }


    public void updateJobDetails(
            String jobTitle,
            String employmentStatus
    ) {

        clickJobTab();

        selectJobTitle(jobTitle);

        selectEmploymentStatus(employmentStatus);

        clickSave();
    }
}