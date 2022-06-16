package utility;

import console.ConsoleClient;

import java.io.Console;

public class UserMaker {
    private Console console;
    private static boolean hiddenPassword = false;

    public UserMaker(Console console) {
        this.console = console;
    }

    public String askLogin() {
        String login;
        while (true) {
            try {
                ConsoleClient.print("Введите логин: ");
                login = console.readLine().trim();
                if (login.equals("")) throw new IllegalArgumentException("Некорректный пароль");
                return login;
            } catch (IllegalArgumentException exception) {
                ConsoleClient.printError(exception.getMessage());
            }
        }
    }

    public String askLoginPassword() {
        while (true) {
            char[] password;
            StringBuilder strPassword = new StringBuilder();
            ConsoleClient.print("Введите пароль: ");
            if (hiddenPassword) {
                password = console.readPassword();
                if (password.length < 8) throw new IllegalArgumentException("Некорректный пароль");
                for (char symbol : password) {
                    strPassword.append(symbol);
                }
            } else strPassword.append(console.readLine().trim());
            ;
            return strPassword.toString();
        }
    }

    public String askRegisterPassword() {
        while (true) {
            char[] password;
            StringBuilder strPassword = new StringBuilder();
            StringBuilder strPassword2 = new StringBuilder();
            ConsoleClient.println("Пароль должен содержать не менее 8 символов");
            ConsoleClient.print("Введите пароль: ");
            password = console.readPassword();
            if (password.length < 8) throw new IllegalArgumentException("Некорректный пароль");
            for (char symbol : password) {
                strPassword.append(symbol);
            }
            ConsoleClient.print("\nВведите пароль еще раз: ");
            password = console.readPassword();
            if (password.length < 8) throw new IllegalArgumentException("Некорректный пароль");
            for (char symbol : password) {
                strPassword2.append(symbol);
            }
            if (strPassword.toString().equals(strPassword2.toString())) return strPassword.toString();
            else ConsoleClient.printError("Пароли не идентичны");
        }
    }

        public static void hidePassword () {
            hiddenPassword = true;
        }

        public static void openPassword () {
            hiddenPassword = false;
        }


    }
