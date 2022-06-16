package controller;

import dao.UserDAO;
import dto.UserDTO;
import lombok.Getter;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import response.Response;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
public class Authentication {
    private final String pepper = "5b2OoqdtPZbn";
    User user;
    private UserDAO userDAO;

    public Authentication(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    private String generateHashedPassword(String password) {

        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("SHA-224");
            messageDigest.reset();
            messageDigest.update((password).getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String shaHex = bigInt.toString(16);

        while (shaHex.length() < 32) {
            shaHex = "0" + shaHex;
        }

        return shaHex;
    }

    public Response login(UserDTO dto) {
        User user = userDAO.getByLogin(dto.getLogin());
        if (user == null) {
            return new Response (false,"Такой логин еще не зарегистрирован");
        }

        if (comparePasswords(dto.getPassword(), user)) {
            return new Response(true, Integer.toString(user.getId()));
        } else {
            return new Response (false,"Неправильный логин или пароль");
        }
    }

    public Response register(UserDTO dto){
        User user = userDAO.getByLogin(dto.getLogin());
        if(user != null){
            return new Response (false,"Такой логин уже зарегистрирован");
        }
        String salt = RandomStringUtils.randomAscii(16);
        String hashedPassword = generateHashedPassword( pepper+ dto.getPassword() + salt);
        userDAO.createUser(new User(dto.getLogin(),hashedPassword,salt));
        return new Response(true, "зарегистрирован");
    }

    private boolean comparePasswords(String password, User user) {
        String passwordWithSalt = pepper + password + user.getSalt();
        return user.getHashedPassword().equals(generateHashedPassword(passwordWithSalt));
    }
}
