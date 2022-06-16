package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;
    private final String createUser = "INSERT INTO Users(login, password, salt) VALUES (?,?,?) RETURNING login";
    private final String getUser = "SELECT * FROM Users WHERE login = ? LIMIT 1";

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public User getByLogin(String login){
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(getUser);
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String bdlogin = resultSet.getString("login");
                String bdpassword = resultSet.getString("password");
                String bdsalt = resultSet.getString("salt");
                user = new User(id, bdlogin,bdpassword,bdsalt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User createUser(User user){
        User user1 = null;
        try {
            PreparedStatement statement = connection.prepareStatement(createUser);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getHashedPassword());
            statement.setString(3, user.getSalt());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user1 = getByLogin(resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user1;
    }


}
