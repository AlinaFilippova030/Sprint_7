package ru.qaScooter.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.qaScooter.data.Data;
import static io.restassured.RestAssured.given;


public class OrderSteps {
    @Step("Создание нового заказ")
    public Response createNewOrder(CreateNewOrder createNewOrder) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .body(createNewOrder)
                .when()
                .post(Data.CREATE_ORDER_URL)
                .then()
                .extract().response();
    }


    @Step("Отменен заказ: №{track}")
    public Response cancelOrder(int track) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .queryParam("track", track)
                .when()
                .put(Data.CANCEL_ORDER_URL)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов")
    public Response getOrderList() {
        return given()
                .when()
                .get(Data.GET_ORDER_LIST_URL)
                .then()
                .extract().response();
    }
}
