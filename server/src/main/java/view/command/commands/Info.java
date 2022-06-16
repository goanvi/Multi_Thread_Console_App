package view.command.commands;

import controller.DAOManager;
import request.Request;
import response.Response;
import view.command.AbstractCommand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Info extends AbstractCommand {

    public Info() {
        super("info", "выводит в стандартный поток вывода информацию о коллекции" +
                " (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public Response execute(Request request, DAOManager manager) {
        int allElements = 0;
        int ownerElements = 0;
        try {
            Statement statement = manager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT (id) FROM StudyGroups");
            while (resultSet.next()) {
                allElements = resultSet.getInt(1);
            }
            ResultSet resultSet1 = statement.executeQuery("SELECT COUNT (id) FROM StudyGroups WHERE ownerid =" + request.getOwnerId());
            while (resultSet1.next()) {
                ownerElements = resultSet1.getInt(1);
            }
        } catch (SQLException e) {
            return new Response(false, "Непредвиденная ошибка");
        }
        String stringBuilder =
                "Всего элементов: "+ allElements + "\n" +
                "Ваших элементов: " + ownerElements + "\n"+
                        "Информация о коллекции успешно выведена!";
        return new Response(true, stringBuilder);
    }

    public String getMessage() {
        return "info - Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
