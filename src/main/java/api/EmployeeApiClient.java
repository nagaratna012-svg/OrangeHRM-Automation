package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EmployeeApiClient {

    private static final String BASE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php";

    private final String apiToken;

    public EmployeeApiClient() {

        apiToken = System.getenv("ORANGEHRM_API_TOKEN");

        if (apiToken == null || apiToken.trim().isEmpty()) {

            throw new IllegalStateException(
                    "ORANGEHRM_API_TOKEN environment variable is not set."
            );
        }
    }

    // GET employee details using Employee Number
    public Response getEmployeeByNumber(int employeeNumber) {

        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + apiToken
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .when()
                .get(
                        "/api/v2/pim/employees/"
                                + employeeNumber
                )
                .then()
                .extract()
                .response();
    }

    // DELETE employee using Employee Number
    public Response deleteEmployeeByNumber(int employeeNumber) {

        String requestBody =
                "{\"ids\":[" + employeeNumber + "]}";

        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + apiToken
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .contentType("application/json")
                .body(requestBody)
                .when()
                .delete("/api/v2/pim/employees")
                .then()
                .extract()
                .response();
    }
}