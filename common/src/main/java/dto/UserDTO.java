package dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserDTO extends DTO implements Serializable {
    private final String login;
    private final String password;

    public UserDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
