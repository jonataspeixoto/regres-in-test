package in.reqres.functional.tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import in.reqres.data.factories.UserRequestFactory;
import in.reqres.dto.request.UserRequestBody;
import in.reqres.dto.response.UserResponseBody;
import in.reqres.step.CreateUserStep;
import io.qameta.allure.Description;
import io.restassured.response.Response;

public class CreateTest {

    @Test
    @Description("Teste de criacao de usuario com sucesso")
    public void createUserWithSuccessTest(){
        UserRequestBody userRequestBody = UserRequestFactory.generateFullUser();

        Response response = new CreateUserStep().createUser(userRequestBody);

        UserResponseBody userResponseBody = response.getBody().as(UserResponseBody.class);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.SC_CREATED, response.statusCode()),
            () -> Assertions.assertEquals(userRequestBody.getName(), userResponseBody.getName()),
            () -> Assertions.assertEquals(userRequestBody.getJob(), userResponseBody.getJob()),
            () -> Assertions.assertNotNull(userResponseBody.getId()),
            () -> Assertions.assertNotNull(userResponseBody.getCreatedAt())
        );
    }

    //Não há especificação informando campos obrigatórios mas coloquei aqui de qualquer forma como um exemplo
    @Description("Teste de campo nulo")
    @ParameterizedTest(name = "{0}")
    @DisplayName("Teste de campo nulo")
    @MethodSource("in.reqres.data.providers.arguments.UserArguments#provideEmptyUserRequestBody")
    public void mandatoryFieldsEmptyTest(UserRequestBody body){
        Response response = new CreateUserStep().createUser(body);

        UserResponseBody userResponseBody = response.getBody().as(UserResponseBody.class);

        Assertions.assertAll(
            () -> Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode()),
            () -> Assertions.assertNull(userResponseBody.getId()),
            () -> Assertions.assertNull(userResponseBody.getName()),
            () -> Assertions.assertNull(userResponseBody.getJob()),
            () -> Assertions.assertNull(userResponseBody.getCreatedAt())
        );
    }
}
