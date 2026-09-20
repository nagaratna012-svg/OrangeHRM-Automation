package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EmployeeApiClient {

    private static final String BASE_URL =
            "https://reqres.in";

    public Response getEmployeeById(int employeeId) {

        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .when()
                .get("/api/users/" + employeeId)
                .then()
                .extract()
                .response();
    }
}