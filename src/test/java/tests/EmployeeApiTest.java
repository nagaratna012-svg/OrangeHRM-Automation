package tests;

import api.EmployeeApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeApiTest {

    @Test
    public void validateEmployeeApi() {

        EmployeeApiClient apiClient =
                new EmployeeApiClient();

        Response response =
                apiClient.getEmployeeById(2);

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
                "API should return HTTP 200"
        );

        String firstName =
                response.jsonPath()
                        .getString("data.first_name");

        String lastName =
                response.jsonPath()
                        .getString("data.last_name");

        Assert.assertNotNull(
                firstName,
                "First name should be present"
        );

        Assert.assertNotNull(
                lastName,
                "Last name should be present"
        );

        System.out.println(
                "API Employee: "
                        + firstName
                        + " "
                        + lastName
        );
    }
}