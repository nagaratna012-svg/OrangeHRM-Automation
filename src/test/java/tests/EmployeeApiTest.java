package tests;

import api.EmployeeApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class EmployeeApiTest {

    @Test
    public void validateEmployeeApi() {

        String apiToken =
                System.getenv("ORANGEHRM_API_TOKEN");

        if (apiToken == null
                || apiToken.trim().isEmpty()) {

            throw new SkipException(
                    "ORANGEHRM_API_TOKEN is not configured. "
                            + "API test will be enabled after token configuration."
            );
        }

        EmployeeApiClient apiClient =
                new EmployeeApiClient();

        /*
         * This is a sample employee number used only
         * to verify that the OrangeHRM API endpoint
         * is accessible.
         *
         * The Employee Lifecycle test will later use
         * the employee number of the employee created
         * through the UI.
         */
        int employeeNumber = 1;

        Response response =
                apiClient.getEmployeeByNumber(
                        employeeNumber
                );

        System.out.println(
                "API Status Code: "
                        + response.getStatusCode()
        );

        System.out.println(
                "API Response: "
                        + response.asPrettyString()
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "OrangeHRM API should return HTTP 200"
        );
    }
}