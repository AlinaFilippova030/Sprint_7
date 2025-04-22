package ru.qaScooter.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.qaScooter.data.Data;

import static io.restassured.RestAssured.given;


public class CourierSteps {

    @Step("Создание нового курьера")
    public Response createNewCourier(CreateNewCourier createNewCourier) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .body(createNewCourier)
                .when()
                .post(Data.CREATE_COURIER_URL)
                .then()
                .extract().response();
    }


    @Step("Авторизация курьера")
    public Response loginCourier(CourierLogin courierLogin) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .body(courierLogin)
                .when()
                .post(Data.LOGIN_COURIER_URL)
                .then()
                .extract().response();
    }


    @Step("Удаление курьера с ID: {courierId}")
    public Response deleteCourier(int courierId) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .when()
                .delete(Data.DELETE_COURIER_URL + courierId)
                .then()
                .extract().response();
    }
}