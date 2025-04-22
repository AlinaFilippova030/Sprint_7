package ru.qaScooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qaScooter.courier.CourierLogin;
import ru.qaScooter.courier.CourierSteps;
import ru.qaScooter.courier.CreateNewCourier;
import ru.qaScooter.data.Data;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class CourierLoginTest {
    private CourierSteps courierSteps;
    private int courierId;
    private CreateNewCourier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = Data.SCOOTER_URL;
        courierSteps = new CourierSteps();
        // Создание курьера
        TestCourier();
    }


    @Test
    @DisplayName("Успешная авторизация курьера")
    public void successfulLoginTest() {
        SuccessfulLogin();
    }

    @Test
    @DisplayName("Ошибка 400 при авторизации без пароля")
    public void loginWithoutPasswordTest() {
        CourierLogin noPassword = new CourierLogin(Data.LOGIN, "");
        Response response = courierSteps.loginCourier(noPassword);

        assertThat(response.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat(response.path("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка 400 при авторизации без логина")
    public void loginWithoutLoginTest() {
        CourierLogin noLogin = new CourierLogin("", Data.PASSWORD);
        Response response = courierSteps.loginCourier(noLogin);

        assertThat(response.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat(response.path("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка 404 при авторизации с неверным паролем")
    public void loginWithWrongPasswordTest() {
        CourierLogin wrongPassword = new CourierLogin(Data.LOGIN, "54321");
        Response response = courierSteps.loginCourier(wrongPassword);

        assertThat(response.statusCode(), equalTo(SC_NOT_FOUND));
        assertThat(response.path("message"), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка 404 при авторизации с неверным логином")
    public void loginWithWrongLoginTest() {
        CourierLogin wrongLogin = new CourierLogin("Masha", Data.PASSWORD);
        Response response = courierSteps.loginCourier(wrongLogin);

        assertThat(response.statusCode(), equalTo(SC_NOT_FOUND));
        assertThat(response.path("message"), equalTo("Учетная запись не найдена"));
    }


    @After
    public void tearDown() {
        deleteTestCourier();
    }

    @Step("Удалить курьера")
    private void deleteTestCourier() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
        }
    }

    @Step("Создать нового курьера")
    private void TestCourier() {
        CreateNewCourier courier = new CreateNewCourier(Data.LOGIN, Data.PASSWORD, Data.FIRST_NAME);
        courierSteps.createNewCourier(courier);

        CourierLogin loginCreds = new CourierLogin(Data.LOGIN, Data.PASSWORD);
        Response loginResponse = courierSteps.loginCourier(loginCreds);
        courierId = loginResponse.path("id");
        assertThat(courierId, notNullValue());
    }

    @Step("Успешная авторизация статус 200, id присутствует")
    private void SuccessfulLogin() {
        CourierLogin validCredentials = new CourierLogin(Data.LOGIN, Data.PASSWORD);
        Response response = courierSteps.loginCourier(validCredentials);

        assertThat(response.statusCode(), equalTo(SC_OK));
        assertThat(response.path("id"), notNullValue());
    }
}
