package ru.qascooter.courier;


public class CreateNewCourier {
    private String login;
    private String password;
    private String firstName;


    public CreateNewCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CreateNewCourier(String login, String password, boolean firstName) {
        this.login = login;
        this.password = password;
    }


    public CreateNewCourier(String login, boolean password, String firstName) {
        this.login = login;
        this.firstName = firstName;
    }

    public CreateNewCourier(boolean login, String password, String firstName) {
        this.password = password;
        this.firstName = firstName;
    }


    public CreateNewCourier() {
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
