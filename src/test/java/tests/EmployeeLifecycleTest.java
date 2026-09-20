package tests;

import api.EmployeeApiClient;
import io.restassured.response.Response;
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

        int employeeNumber = 0;
        String uniqueEmployeeId = null;

        try {

            // =====================================================
            // 1. LOGIN
            // =====================================================

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


            // =====================================================
            // 2. NAVIGATE TO ADD EMPLOYEE
            // =====================================================

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


            // =====================================================
            // 3. READ EMPLOYEE DATA FROM JSON
            // =====================================================

            EmployeeData employeeData =
                    JsonDataReader.getEmployeeData(
                            "src/test/resources/testdata/employee.json"
                    );

            uniqueEmployeeId =
                    "AUTO" + (System.currentTimeMillis() % 100000);


            // =====================================================
            // 4. ADD EMPLOYEE
            // =====================================================

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

            employeeNumber =
                    addEmployeePage.getEmployeeNumber();

            System.out.println(
                    "Created Employee Number: "
                            + employeeNumber
            );

            ExtentReportManager.getTest().pass(
                    "Employee created successfully. Employee Number: "
                            + employeeNumber
            );


            // =====================================================
            // 5. API VALIDATION OF CREATED EMPLOYEE
            // =====================================================

            EmployeeApiClient employeeApiClient =
                    new EmployeeApiClient();

            Response apiResponse =
                    employeeApiClient.getEmployeeByNumber(
                            employeeNumber
                    );

            System.out.println(
                    "API Status Code: "
                            + apiResponse.getStatusCode()
            );

            System.out.println(
                    "API Response: "
                            + apiResponse.asPrettyString()
            );

            Assert.assertEquals(
                    apiResponse.getStatusCode(),
                    200,
                    "API should return HTTP 200 for the created employee"
            );

            String apiFirstName =
                    apiResponse.jsonPath()
                            .getString("data.firstName");

            String apiLastName =
                    apiResponse.jsonPath()
                            .getString("data.lastName");

            String apiEmployeeId =
                    apiResponse.jsonPath()
                            .getString("data.employeeId");

            Assert.assertEquals(
                    apiFirstName,
                    employeeData.getFirstName(),
                    "API First Name should match UI test data"
            );

            Assert.assertEquals(
                    apiLastName,
                    employeeData.getLastName(),
                    "API Last Name should match UI test data"
            );

            Assert.assertEquals(
                    apiEmployeeId,
                    uniqueEmployeeId,
                    "API Employee ID should match UI-created Employee ID"
            );

            ExtentReportManager.getTest().pass(
                    "API validation successful. UI and API employee data match."
            );


            // =====================================================
            // 6. SEARCH EMPLOYEE BY EMPLOYEE ID
            // =====================================================

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
                    "Employee found using Employee ID: "
                            + uniqueEmployeeId
            );


            // =====================================================
            // 7. UPDATE JOB TITLE AND EMPLOYMENT STATUS
            // =====================================================

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

            Assert.assertTrue(
                    employeeDetailsPage.isEmploymentStatusDisplayed(
                            "Full-Time Permanent"
                    ),
                    "Employment Status should be updated to Full-Time Permanent"
            );

            ExtentReportManager.getTest().pass(
                    "Employee Job Title and Employment Status updated successfully"
            );


            // =====================================================
            // 8. SEARCH EMPLOYEE BEFORE DELETION
            // =====================================================

            employeeListPage.searchEmployeeById(
                    uniqueEmployeeId
            );

            Assert.assertTrue(
                    employeeListPage.isEmployeeDisplayed(),
                    "Employee should exist before deletion"
            );

            ExtentReportManager.getTest().pass(
                    "Employee verified before deletion"
            );


            // =====================================================
            // 9. DELETE EMPLOYEE THROUGH UI
            // =====================================================

            employeeListPage.deleteEmployeeById(
                    uniqueEmployeeId
            );

            Assert.assertTrue(
                    employeeListPage.isEmployeeNotDisplayed(),
                    "Employee should be deleted successfully from UI"
            );

            ExtentReportManager.getTest().pass(
                    "Employee deleted successfully through UI: "
                            + uniqueEmployeeId
            );


            // =====================================================
            // 10. VERIFY DELETION THROUGH API
            // =====================================================

            Response deleteVerificationResponse =
                    employeeApiClient.getEmployeeByNumber(
                            employeeNumber
                    );

            System.out.println(
                    "API Status Code After UI Deletion: "
                            + deleteVerificationResponse.getStatusCode()
            );

            System.out.println(
                    "API Response After UI Deletion: "
                            + deleteVerificationResponse.asPrettyString()
            );

            int deletionVerificationStatus =
                    deleteVerificationResponse.getStatusCode();

            Assert.assertTrue(
                    deletionVerificationStatus == 404
                            || deletionVerificationStatus == 422,
                    "API should indicate that the deleted employee no longer exists. "
                            + "Actual status: "
                            + deletionVerificationStatus
            );

            ExtentReportManager.getTest().pass(
                    "API deletion verification successful. "
                            + "Employee Number "
                            + employeeNumber
                            + " no longer exists. "
                            + "API returned status: "
                            + deletionVerificationStatus
            );

            ExtentReportManager.getTest().pass(
                    "API deletion verification successful. "
                            + "Employee Number "
                            + employeeNumber
                            + " no longer exists."
            );


            // =====================================================
            // 11. LOGOUT
            // =====================================================

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


            // =====================================================
            // FINAL SUCCESS MESSAGE
            // =====================================================

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "EMPLOYEE LIFECYCLE TEST PASSED"
            );

            System.out.println(
                    "Employee Number: "
                            + employeeNumber
            );

            System.out.println(
                    "Employee ID: "
                            + uniqueEmployeeId
            );

            System.out.println(
                    "UI Creation: PASSED"
            );

            System.out.println(
                    "API Validation: PASSED"
            );

            System.out.println(
                    "UI Search: PASSED"
            );

            System.out.println(
                    "UI Update: PASSED"
            );

            System.out.println(
                    "UI Deletion: PASSED"
            );

            System.out.println(
                    "API Deletion Verification: PASSED"
            );

            System.out.println(
                    "Logout: PASSED"
            );

            System.out.println(
                    "=========================================="
            );


        } catch (Exception e) {

            String screenshotPath =
                    ScreenshotUtil.captureScreenshot(
                            driver,
                            "EmployeeLifecycleTest_Failure"
                    );

            ExtentReportManager.getTest().fail(
                    "Test failed: "
                            + e.getMessage()
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