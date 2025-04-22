package ru.qascooter.courier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateNewCourier {
    private String login;
    private String password;
    private String firstName;

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
}
