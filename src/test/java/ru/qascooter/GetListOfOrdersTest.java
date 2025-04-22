package ru.qaScooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Step;
import ru.qaScooter.data.Data;
import ru.qaScooter.order.OrderSteps;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = Data.SCOOTER_URL;
    }

    @Test
    //получение списка заказов
    @DisplayName("получение списка заказов")
    public void getOrderListTest() {
        OrderSteps orderSteps = new OrderSteps();
        Response getListOfOrders = orderSteps.getOrderList();
        assertThat(getListOfOrders.statusCode(), equalTo(SC_OK));
        verifyOrdersArrayExists(getListOfOrders);
    }


    @Step("Проверить, что массив 'orders' существует и не пуст")
    public void verifyOrdersArrayExists(Response getListOfOrders) {
        assertThat("orders", notNullValue());
        assertThat("Массив 'orders' не пуст",
                ((List<?>) getListOfOrders.path("orders")).size(), greaterThan(0));
    }
}

