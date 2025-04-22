package ru.qascooter;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qascooter.data.Data;
import ru.qascooter.order.CreateNewOrder;
import ru.qascooter.order.OrderSteps;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(Parameterized.class)
public class CreateColorOrdersTest {
    private static final String firstName = "Сальвадор";
    private static final String lastName = "не Дали";
    private static final String address = "Укупника 41/5";
    private static final int metroStation = 5;
    private static final String phone = "+79213489009";
    private static final int rentTime = 3;
    private static final String deliveryDate = "2025-04-23";
    private static final String comment = "Добавьте комментарий";
    private String[] color;

    public CreateColorOrdersTest(String[] color) {
        this.color = color;
    }

    private int track;
    private OrderSteps orderSteps;
    private CreateNewOrder order;


    @Parameterized.Parameters(name = "Выбран цвет: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Data.SCOOTER_URL;
        orderSteps = new OrderSteps();
        order = new CreateNewOrder(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
    }

    @Test
    // Создание нового заказа
    @DisplayName("Создание заказа")
    public void createNewOrderTest() {
        String colorString = String.join(", ", color);
        Allure.parameter("Цвет", colorString);
        Response createdOrderResponse = createOrder(order, colorString);
        track = createdOrderResponse.path("track");
        assertThat(createdOrderResponse.statusCode(), equalTo(SC_CREATED));
        assertThat(track, notNullValue());
        System.out.println("заказ: " + track + " создан");
    }

    @Step("Создание заказа с цветом: {1}")
    public Response createOrder(CreateNewOrder order, String Цвет) {
        return orderSteps.createNewOrder(order);
    }


    @After
    //отмена заказа
    public void tearDown() {
        if (track != 0) {
            orderSteps.cancelOrder(track);
            System.out.println("заказ: " + track + " отменен");
        }
    }
}


