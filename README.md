# OrangeHRM Automation Framework

Selenium Java TestNG automation framework for validating the OrangeHRM employee lifecycle.

## Tech Stack

- Java 21
- Selenium WebDriver 4.35.0
- TestNG 7.11.0
- Maven
- REST Assured 5.5.6
- ExtentReports 5.1.2
- Jackson Databind
- Chrome Browser
- Page Object Model (POM)

## Application

OrangeHRM Demo:
https://opensource-demo.orangehrmlive.com/

## Framework Structure

```text
OrangeHRM-Automation/
├── pom.xml
├── README.md
├── testng.xml
├── .gitignore
├── src/
│   ├── main/java/
│   │   ├── api/
│   │   ├── pages/
│   │   └── utils/
│   └── test/
│       ├── java/tests/
│       └── resources/testdata/
├── reports/
├── screenshots/
└── videos/