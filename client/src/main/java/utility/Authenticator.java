package utility;

import client.Communicate;
import console.ConsoleClient;
import dto.UserDTO;
import lombok.Getter;
import request.Request;
import response.Response;

import java.io.Console;

public class Authenticator {
    @Getter
    boolean isAuthenticated = false;
    UserMaker maker;
    Communicate communicate;
    Console console;
    static int ownerId;

    public Authenticator(UserMaker maker, Communicate communicate, Console console) {
        this.maker = maker;
        this.communicate = communicate;
        this.console = console;
    }

    public void start() {
        while (true) {
            try {
                ConsoleClient.println("\nВойти/Зарегистрироваться");
                String input = console.readLine().trim();
                if (input.equalsIgnoreCase("войти")){
                    login();
                    return;
                }else if(input.equalsIgnoreCase("зарегистрироваться")){
                    register();
                    login();
                    return;
                }else throw new IllegalArgumentException("Некорректный ввод");
            } catch (IllegalArgumentException e) {
                ConsoleClient.printError(e.getMessage());
            }
        }
    }

    private void login() {
        String login = "";
        String password = "";
        while (true) {
            login = maker.askLogin();
            UserMaker.hidePassword();
            password = maker.askLoginPassword();
            communicate.send(new Request<UserDTO>(new UserDTO(login, password), "login", ""));
            Response res = communicate.get();
            ownerId = Integer.parseInt(res.getText());
            if (!res.getAnswer()) {
                ConsoleClient.printError("Ошибка авторизации");
            } else {
                ConsoleClient.println("Авторизация успешна");
                return;
            }
        }
    }

    private void register() {
        String login = "";
        String password = "";
        while (true) {
            login = maker.askLogin();
            UserMaker.openPassword();
            password = maker.askRegisterPassword();
            communicate.send(new Request<UserDTO>(new UserDTO(login, password), "register", ""));
            Response res = communicate.get();
            if (!res.getAnswer()) {
                ConsoleClient.printError("Ошибка регистрации");
            } else {
                ConsoleClient.println("Регистрация успешна");
//                setAuthenticated();
                return;
            }
        }
    }

    public void setAuthenticated() {
        isAuthenticated = !this.isAuthenticated;
    }

    public static int getOwnerId(){
        return ownerId;
    }
}
