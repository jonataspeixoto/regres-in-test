package in.reqres.step;

import static in.reqres.component.LogBuffer.requestLoggingFilter;
import static in.reqres.component.LogBuffer.responseLoggingFilter;
import static io.restassured.RestAssured.given;

import in.reqres.component.Environment;
import in.reqres.component.LogBuffer;
import in.reqres.dto.request.UserRequestBody;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserStep implements LogBuffer {
    private Environment ENV = Environment.getEnviroment();
    private String baseUrl = ENV.getProperty("BASE_URL");
    private String usersPath = ENV.getProperty("USERS_PATH");

    @Step("Send create user request")
    public Response createUser(UserRequestBody userBody){
        Response response =
            given()
                .filters(requestLoggingFilter, responseLoggingFilter)
                .contentType(ContentType.JSON)
                .body(userBody)
            .when()
                .post("%s/%s".formatted(baseUrl, usersPath))
            .then()
                .extract()
                .response();

        logAll();
        return response;
    }
}
