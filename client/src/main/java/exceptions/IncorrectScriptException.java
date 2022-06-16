package exceptions;

public class IncorrectScriptException extends Exception {

    @Override
    public String getMessage() {
        return "Некорректный входной скрипт!";
    }
}
