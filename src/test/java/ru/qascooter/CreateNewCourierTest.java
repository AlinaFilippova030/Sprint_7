package ru.qascooter;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qascooter.courier.CourierLogin;
import ru.qascooter.courier.CourierSteps;
import ru.qascooter.courier.CreateNewCourier;
import ru.qascooter.data.Data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

import io.qameta.allure.junit4.DisplayName;

public class CreateNewCourierTest {
    private CourierSteps courierSteps;
    private CreateNewCourier courier;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = Data.SCOOTER_URL;
        courierSteps = new CourierSteps();
        courier = new CreateNewCourier(Data.LOGIN, Data.PASSWORD, Data.FIRST_NAME);
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierSuccessfully() {
        createTestCourier();
        loginAndGetCourierId();
    }

    @Test
    @DisplayName("Создание курьера без имени")
    public void createCourierWithoutName() {
        CreateNewCourier noNameCourier = new CreateNewCourier(Data.LOGIN, Data.PASSWORD, false);
        createCourier(noNameCourier);
        loginAndGetCourierId();
    }

    @Test
    @DisplayName("Ошибка при создании курьера без пароля")
    public void createCourierWithoutPassword() {
        CreateNewCourier noPassCourier = new CreateNewCourier(Data.LOGIN, false, Data.FIRST_NAME);
        Response response = courierSteps.createNewCourier(noPassCourier);
        verifyBadRequest(response, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    public void createCourierWithoutLogin() {
        CreateNewCourier noLoginCourier = new CreateNewCourier(false, Data.PASSWORD, Data.FIRST_NAME);
        Response response = courierSteps.createNewCourier(noLoginCourier);
        verifyBadRequest(response, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Ошибка при создании дубликата курьера")
    public void createDuplicateCourier() {
        createTestCourier();
        loginAndGetCourierId();
        createCourierDouble(courier);
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
        }
    }

    @Step("Создать курьера")
    private void createTestCourier() {
        Response response = courierSteps.createNewCourier(courier);
        assertThat(response.statusCode(), equalTo(SC_CREATED));
        assertThat(response.path("ok"), equalTo(true));
    }

    @Step("Залогиниться и получить ID курьера")
    private void loginAndGetCourierId() {
        CourierLogin login = new CourierLogin(Data.LOGIN, Data.PASSWORD);
        Response loginResponse = courierSteps.loginCourier(login);
        assertThat(loginResponse.statusCode(), equalTo(SC_OK));
        courierId = loginResponse.path("id");
        assertThat(courierId, notNullValue());
    }

    @Step("Создать курьера без имени")
    private void createCourier(CreateNewCourier courier) {
        Response response = courierSteps.createNewCourier(courier);
        assertThat(response.statusCode(), equalTo(SC_CREATED));
        assertThat(response.path("ok"), equalTo(true));
    }

    @Step("Создать курьера c существующим Логином и паролем")
    private void createCourierDouble(CreateNewCourier courier) {
        Response response = courierSteps.createNewCourier(courier);
        verifyConflict(response, "Этот логин уже используется. Попробуйте другой.");
    }

    @Step("Проверить ошибку 400: {expectedMessage}")
    private void verifyBadRequest(Response response, String expectedMessage) {
        assertThat(response.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat(response.path("message"), equalTo(expectedMessage));
    }

    @Step("Проверить ошибку 409: {expectedMessage}")
    private void verifyConflict(Response response, String expectedMessage) {
        assertThat(response.statusCode(), equalTo(SC_CONFLICT));
        assertThat(response.path("message"), equalTo(expectedMessage));
    }
}