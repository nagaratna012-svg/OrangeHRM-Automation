# OrangeHRM Automation Framework

## Quality Engineer (Automation) – Technical Assessment

Selenium + Java + TestNG automation framework for validating the OrangeHRM Employee Lifecycle using UI automation, REST API validation, Page Object Model, data-driven test data, and Extent Reports.

---

## 1. Project Overview

This project automates the end-to-end employee lifecycle in the OrangeHRM demo application.

### Application Under Test

OrangeHRM Demo:

https://opensource-demo.orangehrmlive.com/

### Automated Employee Lifecycle

The automation covers:

1. Login using valid credentials
2. Verify Dashboard
3. Navigate to PIM
4. Add a new employee using JSON test data
5. Upload employee profile picture
6. Generate a unique Employee ID
7. Verify the created employee through OrangeHRM REST API
8. Search employee using Employee ID
9. Update Job Title
10. Update Employment Status
11. Verify updated employee details
12. Delete employee through the UI
13. Verify deletion through the API
14. Logout
15. Generate an Extent HTML report

---

## 2. Technology Stack

| Technology         | Version / Usage                 |
| ------------------ | ------------------------------- |
| Java               | JDK 21.0.11                     |
| Selenium WebDriver | 4.35.0                          |
| TestNG             | 7.11.0                          |
| REST Assured       | 5.5.6                           |
| Extent Reports     | 5.1.2                           |
| Jackson            | JSON test-data parsing          |
| Maven              | Build and dependency management |
| IntelliJ IDEA      | Development IDE                 |
| Chrome             | Web browser                     |

---

## 3. Framework Design

The framework follows the **Page Object Model (POM)** to improve maintainability and reduce duplication.

### Main Components

* **Page Objects** – contain locators and UI actions
* **Test Classes** – contain test scenarios and assertions
* **API Client** – handles REST API operations
* **Utility Classes** – provide reusable functionality
* **JSON Test Data** – supports data-driven employee creation
* **Extent Reports** – provides execution reporting
* **Screenshot Utility** – captures screenshots when a test fails

---

## 4. Project Structure

```text
OrangeHRM-Automation/
│
├── pom.xml
├── README.md
├── testng.xml
├── .gitignore
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── api/
│   │       │   └── EmployeeApiClient.java
│   │       │
│   │       ├── pages/
│   │       │   ├── LoginPage.java
│   │       │   ├── PIMPage.java
│   │       │   ├── AddEmployeePage.java
│   │       │   ├── EmployeeListPage.java
│   │       │   ├── EmployeeDetailsPage.java
│   │       │   └── LogoutPage.java
│   │       │
│   │       └── utils/
│   │           ├── EmployeeData.java
│   │           ├── JsonDataReader.java
│   │           ├── ExtentReportManager.java
│   │           └── ScreenshotUtil.java
│   │
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   │
│       │   └── tests/
│       │       ├── OrangeHRMLaunchTest.java
│       │       ├── EmployeeLifecycleTest.java
│       │       └── EmployeeApiTest.java
│       │
│       └── resources/
│           └── testdata/
│               ├── employee.json
│               └── profile.png
│
├── reports/
├── screenshots/
└── videos/
```

---

## 5. Page Object Model

Each major application page has its own Page Object.

### LoginPage

Responsible for:

* Entering username
* Entering password
* Clicking Login
* Verifying Dashboard

### PIMPage

Responsible for:

* Navigating to PIM
* Navigating to Add Employee

### AddEmployeePage

Responsible for:

* Entering employee information
* Entering Employee ID
* Uploading profile picture
* Saving employee
* Extracting OrangeHRM Employee Number

### EmployeeListPage

Responsible for:

* Searching employees
* Verifying employee presence
* Opening employee details
* Deleting employees

### EmployeeDetailsPage

Responsible for:

* Opening Job section
* Updating Job Title
* Updating Employment Status
* Verifying updated values

### LogoutPage

Responsible for:

* Opening user menu
* Logging out
* Verifying the login page

---

## 6. Data-Driven Testing

Employee test data is maintained separately from the automation code.

Location:

```text
src/test/resources/testdata/employee.json
```

Example:

```json
{
  "firstName": "Nagaratna",
  "middleName": "QA",
  "lastName": "Automation",
  "employeeId": "AUTO",
  "profilePicture": "src/test/resources/testdata/profile.png"
}
```

The framework uses Jackson `ObjectMapper` to read the JSON data.

A unique Employee ID is generated during execution to prevent duplicate Employee IDs between test runs.

---

## 7. API Testing

REST Assured is used for API validation.

### API Authentication

The OrangeHRM API token is **not hardcoded** in the source code.

The framework reads it from the environment variable:

```text
ORANGEHRM_API_TOKEN
```

### Configure the API Token in IntelliJ

Open:

```text
Run
→ Edit Configurations
→ Environment variables
```

Add:

```text
Name:
ORANGEHRM_API_TOKEN

Value:
<your OrangeHRM API access token>
```

The value should contain only the token.

Do not commit the token to GitHub.

### API Validation Flow

After creating an employee through the UI, the framework extracts the generated Employee Number from the URL.

The API is then called to retrieve that employee.

The following values are cross-checked between UI test data and API response:

* First Name
* Last Name
* Employee ID

### API Deletion Verification

After the employee is deleted through the UI, the framework calls the employee GET API again.

The demo API returns an invalid-parameter response for the deleted Employee Number, and the test validates that the deleted employee is no longer retrievable.

---

## 8. Test Execution

### Run from IntelliJ

Individual tests can be executed directly from IntelliJ IDEA.

For example:

```text
EmployeeLifecycleTest
```

### Run using Maven

From the project root:

```bash
mvn clean test
```

### Run using TestNG Suite

```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 9. Expected Test Result

A successful Employee Lifecycle execution validates:

```text
Login: PASSED
Dashboard: PASSED
Employee Creation: PASSED
Profile Picture Upload: PASSED
API Validation: PASSED
Employee Search: PASSED
Employee Update: PASSED
UI Deletion: PASSED
API Deletion Verification: PASSED
Logout: PASSED
```

Example TestNG result:

```text
===============================================
Default Suite
Total tests run: 1
Passes: 1
Failures: 0
Skips: 0
===============================================
```

---

## 10. Reporting

The framework uses **Extent Reports** for HTML execution reporting.

Reports are generated under:

```text
reports/
```

The report contains:

* Test name
* Execution steps
* Passed steps
* Failed steps
* API validation details
* Screenshots for failures

---

## 11. Screenshots

Screenshots are captured automatically when the Employee Lifecycle test encounters an exception.

Screenshots are stored under:

```text
screenshots/
```

---

## 12. Test Video

Execution video is maintained under:

```text
videos/
```

The video demonstrates the automated Employee Lifecycle execution against the OrangeHRM application.

---

## 13. Assertions

The framework uses TestNG assertions for meaningful validation.

Examples include:

* Dashboard is displayed after login
* Add Employee page is displayed
* Employee creation is successful
* API returns the expected employee
* UI and API employee data match
* Employee is displayed after search
* Job Title is updated
* Employment Status is updated
* Employee is removed from the UI
* Deleted employee is no longer retrievable through API
* Logout returns the user to the login page

---

## 14. Synchronization

The framework uses Selenium explicit waits such as:

```java
WebDriverWait
ExpectedConditions
```

The automation avoids unnecessary fixed delays such as:

```java
Thread.sleep()
```

This improves test stability and execution reliability.

---

## 15. Maintainability

The framework follows these practices:

* Page Object Model
* Reusable page methods
* Centralized API client
* Externalized test data
* Explicit waits
* Descriptive assertions
* Meaningful method and variable names
* Environment-based API authentication
* Screenshot capture on failures
* HTML reporting
* Maven dependency management

---

## 16. Prerequisites

Install the following:

1. Java JDK 21
2. Maven
3. IntelliJ IDEA or another Java IDE
4. Google Chrome
5. Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

---

## 17. Clone and Setup

Clone the repository:

```bash
git clone <repository-url>
```

Navigate to the project:

```bash
cd OrangeHRM-Automation
```

Install dependencies and compile:

```bash
mvn clean install
```

Configure the OrangeHRM API token as an environment variable before executing API-dependent tests.

---

## 18. Test Credentials

For the OrangeHRM demo application:

```text
Username: Admin
Password: admin123
```

The application URL is:

```text
https://opensource-demo.orangehrmlive.com/
```

---

## 19. Notes

* The OrangeHRM demo environment is a public test environment and its data may change over time.
* The API token is intentionally excluded from source control.
* Employee IDs are generated dynamically during execution.
* Browser-specific CDP warnings may appear when the installed Chrome version is newer than the CDP version bundled with the Selenium dependency. The test execution can still proceed when WebDriver communication is functioning normally.

---

## 20. Assessment Coverage

| Requirement               | Implementation     |
| ------------------------- | ------------------ |
| UI Automation             | Selenium WebDriver |
| Programming Language      | Java               |
| Test Framework            | TestNG             |
| Page Object Model         | Implemented        |
| Data Driven Testing       | JSON               |
| Employee Creation         | Implemented        |
| Profile Picture Upload    | Implemented        |
| Employee Search           | Implemented        |
| Employee Update           | Implemented        |
| API Validation            | REST Assured       |
| UI/API Cross Validation   | Implemented        |
| UI Employee Deletion      | Implemented        |
| API Deletion Verification | Implemented        |
| Logout                    | Implemented        |
| Assertions                | TestNG             |
| HTML Report               | Extent Reports     |
| Failure Screenshot        | Implemented        |
| Test Video                | Included           |
| Dependency Management     | Maven              |
| Documentation             | README             |

---

## 21. Author

**Nagaratna**

QA Automation Engineer

Skills demonstrated in this project:

* Selenium WebDriver
* Java
* TestNG
* REST Assured
* API Testing
* Page Object Model
* Data-Driven Testing
* Maven
* HTML Reporting
* Functional UI Automation
