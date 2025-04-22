package ru.qascooter.courier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierLogin {
    private String login;
    private String password;

    // Конструктор без пароля
    public CourierLogin(String login) {
        this.login = login;
        this.password = "";
    }

    // Конструктор без логина
    public CourierLogin(String password, boolean dummy) {
        this.password = password;
        this.login = "";
    }
}