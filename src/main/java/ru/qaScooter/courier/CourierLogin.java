package ru.qaScooter.courier;

public class CourierLogin {
    private String login;
    private String password;

    public CourierLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}