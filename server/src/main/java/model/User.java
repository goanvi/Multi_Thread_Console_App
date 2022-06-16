package model;

public class User {
    private int id;
    private final String login;
    private final String hashedPassword;
    private final String salt;

    public User(int id, String login, String passwordHash, String salt) {
        this.id = id;
        this.login = login;
        this.hashedPassword = passwordHash;
        this.salt = salt;
    }
    public User(String login, String passwordHash, String salt) {
        this.login = login;
        this.hashedPassword = passwordHash;
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }
}
