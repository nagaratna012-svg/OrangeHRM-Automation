package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

    public static void setupReport() {

        String reportPath =
                System.getProperty("user.dir")
                        + "/reports/OrangeHRM-Test-Report.html";

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportPath);

        sparkReporter.config()
                .setDocumentTitle("OrangeHRM Automation Report");

        sparkReporter.config()
                .setReportName("OrangeHRM Employee Lifecycle Tests");

        extentReports = new ExtentReports();

        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo(
                "Project",
                "OrangeHRM Automation"
        );

        extentReports.setSystemInfo(
                "Automation",
                "Selenium + Java + TestNG"
        );

        extentReports.setSystemInfo(
                "Browser",
                "Chrome"
        );
    }

    public static void createTest(String testName) {

        extentTest =
                extentReports.createTest(testName);
    }

    public static ExtentTest getTest() {

        return extentTest;
    }

    public static void addScreenshot(
            String screenshotPath
    ) {

        try {

            if (screenshotPath != null) {

                extentTest.addScreenCaptureFromPath(
                        screenshotPath
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to attach screenshot: "
                            + e.getMessage()
            );
        }
    }

    public static void flushReport() {

        if (extentReports != null) {
            extentReports.flush();
        }
    }
}