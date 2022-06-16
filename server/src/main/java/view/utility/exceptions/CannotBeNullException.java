package view.utility.exceptions;

public class CannotBeNullException extends Exception {
    @Override
    public String getMessage() {
        return "Значение поля не может бить пустым!";
    }
}
