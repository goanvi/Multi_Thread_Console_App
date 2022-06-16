package console;

import exceptions.IncorrectScriptException;
import utility.Asker;
import utility.CommandManager;

import java.io.Console;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class ConsoleClient {
    CommandManager commandManager;
    Console console;
    Scanner scriptScanner;
    Deque<String> files = new ArrayDeque<>();
    Deque<Scanner> scanners = new ArrayDeque<>();
    Scanner testScanner;

    public ConsoleClient(CommandManager commandManager, Console console) {
        this.commandManager = commandManager;
        this.console = console;
    }

    public void interactiveMode() {
        try {
            while (true) {
                Asker.setUserMode();
                println("");
                println("Введите команду");
                String[] input = (readLine() + " ").split(" ", 2);
                commandManager.callCommand(input[0], input[1]);
            }
        } catch (IncorrectScriptException exception) {
            printError("Эта ошибка не должна была тут выпасть!");
            System.exit(0);
        }
    }

    public void fileMode(Scanner scanner) {
        String inputLine;
        try {
            scanners.add(scanner);
            Asker.setFileMode();
            println("Выполнение скрипта из файла " + files.getLast());
            while (true) {
                scriptScanner = scanners.getLast();
                if (scanners.getLast().hasNextLine()) {
                    inputLine = scanners.getLast().nextLine().trim();
                    String[] input = (inputLine + " ").split(" ", 2);
                    commandManager.callCommand(input[0], input[1]);
                } else {
                    break;
                }

            }
            scanners.removeLast();
        } catch (IncorrectScriptException exception) {
            printError(exception.getMessage());
            files.clear();
            scanners.clear();
            interactiveMode();
        }

    }

    public Console getConsole() {
        return console;
    }

    public String readLine() {
        try {
            if (Asker.getFileMode()) {
                return scriptScanner.nextLine().trim();
            }
            return getConsole().readLine().trim();
        } catch (NullPointerException exception) {
            readLine();
        }
        return null;
    }

    public Scanner getScriptScanner() {
        return scriptScanner;
    }

    public Deque<String> getFiles() {
        return files;
    }

    public static void println(Object out) {
        System.out.println(out);
    }

    public static void print(Object out) {
        System.out.print(out);
    }

    public static void printError(Object out) {
        System.out.println("ERROR: " + out);
    }

}
