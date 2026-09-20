package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.AddEmployeePage;
import pages.EmployeeDetailsPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import pages.LogoutPage;
import pages.PIMPage;
import utils.EmployeeData;
import utils.ExtentReportManager;
import utils.JsonDataReader;
import utils.ScreenshotUtil;

public class EmployeeLifecycleTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {

        ExtentReportManager.setupReport();

        ExtentReportManager.createTest(
                "OrangeHRM Employee Lifecycle Test"
        );

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(
                "https://opensource-demo.orangehrmlive.com/"
        );

        ExtentReportManager.getTest().info(
                "OrangeHRM application launched"
        );
    }

    @Test
    public void addNewEmployee() throws Exception {

        try {

            // Login
            LoginPage loginPage =
                    new LoginPage(driver);

            loginPage.login(
                    "Admin",
                    "admin123"
            );

            Assert.assertTrue(
                    loginPage.isDashboardDisplayed(),
                    "Dashboard should be displayed after login"
            );

            ExtentReportManager.getTest().pass(
                    "Login successful and Dashboard displayed"
            );

            // Navigate to PIM
            PIMPage pimPage =
                    new PIMPage(driver);

            pimPage.navigateToAddEmployee();

            Assert.assertTrue(
                    pimPage.isAddEmployeePageDisplayed(),
                    "Add Employee page should be displayed"
            );

            ExtentReportManager.getTest().pass(
                    "Navigated to Add Employee page"
            );

            // Read employee data from JSON
            EmployeeData employeeData =
                    JsonDataReader.getEmployeeData(
                            "src/test/resources/testdata/employee.json"
                    );

            String uniqueEmployeeId =
                    "AUTO" + (System.currentTimeMillis() % 100000);

            // Add employee
            AddEmployeePage addEmployeePage =
                    new AddEmployeePage(driver);

            addEmployeePage.addEmployee(
                    employeeData.getFirstName(),
                    employeeData.getMiddleName(),
                    employeeData.getLastName(),
                    uniqueEmployeeId,
                    employeeData.getProfilePicture()
            );

            Assert.assertTrue(
                    addEmployeePage.isEmployeeDetailsDisplayed(),
                    "Employee Personal Details page should be displayed after saving"
            );

            ExtentReportManager.getTest().pass(
                    "Employee created successfully: "
                            + uniqueEmployeeId
            );

            // Search employee
            EmployeeListPage employeeListPage =
                    new EmployeeListPage(driver);

            employeeListPage.searchEmployeeById(
                    uniqueEmployeeId
            );

            Assert.assertTrue(
                    employeeListPage.isEmployeeDisplayed(),
                    "Created employee should be displayed in Employee List"
            );

            ExtentReportManager.getTest().pass(
                    "Employee found using Employee ID"
            );

            // Edit employee
            employeeListPage.clickEditEmployeeById(
                    uniqueEmployeeId
            );

            EmployeeDetailsPage employeeDetailsPage =
                    new EmployeeDetailsPage(driver);

            employeeDetailsPage.updateJobDetails(
                    "QA Engineer",
                    "Full-Time Permanent"
            );

            Assert.assertTrue(
                    employeeDetailsPage.isJobTitleDisplayed(
                            "QA Engineer"
                    ),
                    "Job Title should be updated to QA Engineer"
            );

            ExtentReportManager.getTest().pass(
                    "Employee Job Title updated successfully"
            );

            // Delete employee
            employeeListPage.searchEmployeeById(
                    uniqueEmployeeId
            );

            Assert.assertTrue(
                    employeeListPage.isEmployeeDisplayed(),
                    "Employee should exist before deletion"
            );

            employeeListPage.deleteEmployeeById(
                    uniqueEmployeeId
            );

            Assert.assertTrue(
                    employeeListPage.isEmployeeNotDisplayed(),
                    "Employee should be deleted successfully"
            );

            ExtentReportManager.getTest().pass(
                    "Employee deleted successfully: "
                            + uniqueEmployeeId
            );

            // Logout
            LogoutPage logoutPage =
                    new LogoutPage(driver);

            logoutPage.logout();

            Assert.assertTrue(
                    logoutPage.isLoggedOut(),
                    "User should be successfully logged out"
            );

            ExtentReportManager.getTest().pass(
                    "Logout successful"
            );

        } catch (Exception e) {

            String screenshotPath =
                    ScreenshotUtil.captureScreenshot(
                            driver,
                            "EmployeeLifecycleTest_Failure"
                    );

            ExtentReportManager.getTest().fail(
                    "Test failed: " + e.getMessage()
            );

            ExtentReportManager.addScreenshot(
                    screenshotPath
            );

            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

        ExtentReportManager.flushReport();
    }
}