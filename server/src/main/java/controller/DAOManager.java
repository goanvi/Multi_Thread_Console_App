package controller;

import dao.CoordinatesDAO;
import dao.PersonDAO;
import dao.StudyGroupDAO;
import dao.UserDAO;
import lombok.Getter;

import java.sql.Connection;

@Getter
public class DAOManager {
    Connection connection;
    StudyGroupDAO studyGroupDAO;
    PersonDAO personDAO;
    CoordinatesDAO coordinatesDAO;
    UserDAO userDAO;

    public DAOManager(Connection connection) {
        this.connection = connection;
        this.coordinatesDAO = new CoordinatesDAO(connection);
        this.personDAO = new PersonDAO(connection);
        this.studyGroupDAO = new StudyGroupDAO(connection, coordinatesDAO, personDAO);
        this.userDAO = new UserDAO(connection);
    }


}
