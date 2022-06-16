package utility;

import console.ConsoleClient;
import dto.*;
import exceptions.IncorrectInputException;
import exceptions.IncorrectScriptException;
import utility.exceptions.CannotBeNullException;
import utility.exceptions.GoingBeyondLimitsException;
import utility.exceptions.IncorrectNameEnumException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class Asker { // Кажется закончил, останется только дописать геттеры и сеттеры по необходимости, проверить все exceptions
    ConsoleClient consoleClient;
    private static boolean fileMode;
    private final int MAX_COORD_X = 811;
    private final int MIN_STUDENTS_COUNT = 0;
    private final int MIN_AVERAGE_MARK = 0;
    private final int MIN_WEIGHT_ADMIN = 0;
    private boolean personExist;

    public Asker(ConsoleClient consoleClient) {
        this.consoleClient = consoleClient;
        fileMode = false;
        personExist = true;
    }


    public String askName() throws IncorrectScriptException {
        String name;
        while (true) {
            try {
                ConsoleClient.println("\nВведите название группы");
                name = readLine();
                if (fileMode) ConsoleClient.println(name);
                if (name.equals("")) throw new CannotBeNullException();
                break;
            } catch (CannotBeNullException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }

        }
        return name;
    }

    public Integer askCoordinateX() throws IncorrectScriptException {
        String strX;
        int x;
        while (true) {
            try {
                ConsoleClient.println("\nВведите координату X");
                ConsoleClient.println("Максимальное значение координаты X = " + MAX_COORD_X);
                ConsoleClient.println("Число должно быть целочисленным");
                strX = readLine();
                if (fileMode) ConsoleClient.println(strX);
                if (strX.equals("")) throw new CannotBeNullException();
                x = Integer.parseInt(strX);
                if (x > MAX_COORD_X) throw new GoingBeyondLimitsException();
                break;
            } catch (CannotBeNullException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (GoingBeyondLimitsException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                ConsoleClient.printError("Значением поля должно являться число!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    public int askCoordinateY() throws IncorrectScriptException {
        String strY;
        int y;
        while (true) {
            try {
                ConsoleClient.println("\nВведите координату Y");
                ConsoleClient.println("Число должно быть целочисленным");
                strY = readLine();
                if (fileMode) ConsoleClient.println(strY);
                y = Integer.parseInt(strY);
                break;
            } catch (NumberFormatException exception) {
                ConsoleClient.printError("Значением поля должно являться число!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    public CoordinatesDTO askCoordinates() throws IncorrectScriptException {
        Integer x = askCoordinateX();
        int y = askCoordinateY();
        return new CoordinatesDTO(x, y);
    }

    public long askStudentsCount() throws IncorrectScriptException {
        String strStud;
        long stud;
        while (true) {
            try {
                ConsoleClient.println("\nВведите количество студентов в группе.");
                ConsoleClient.println("Значение должно быть больше " + MIN_STUDENTS_COUNT);
                ConsoleClient.println("Число должно быть целочисленным");
                strStud = readLine();
                if (fileMode) ConsoleClient.println(strStud);
                stud = Long.parseLong(strStud);
                if (stud <= 0) throw new GoingBeyondLimitsException();
                break;
            } catch (GoingBeyondLimitsException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                ConsoleClient.printError("Значением поля должно являться число!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return stud;
    }

    public double askAverageMark() throws IncorrectScriptException {
        String strMark;
        double mark;
        while (true) {
            try {
                ConsoleClient.println("\nВведите среднюю оценку в группе.");
                ConsoleClient.println("Значение должно быть больше " + MIN_AVERAGE_MARK);
                ConsoleClient.println("Число может быть дробным");
                strMark = readLine();
                if (fileMode) ConsoleClient.println(strMark);
                mark = Double.parseDouble(strMark);
                if (mark <= 0) throw new GoingBeyondLimitsException();
                break;
            } catch (GoingBeyondLimitsException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NumberFormatException exception) {
                ConsoleClient.printError("Значением поля должно являться число!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return mark;
    }

    public FormOfEducationDTO askFromOfEducation() throws IncorrectScriptException {
        String strEduc;
        FormOfEducationDTO educ;
        while (true) {
            try {
                ConsoleClient.println("\nВведите форму обучения");
                ConsoleClient.println("Доступные формы обучения: Дистанционно, Очно, Вечер");
                strEduc = readLine();
                if (strEduc.equals("")) throw new CannotBeNullException();
                if (fileMode) ConsoleClient.println(strEduc);
                if (strEduc.equalsIgnoreCase("дистанционно")) {
                    educ = FormOfEducationDTO.DISTANCE_EDUCATION;
                } else if (strEduc.equalsIgnoreCase("очно")) {
                    educ = FormOfEducationDTO.DISTANCE_EDUCATION;
                } else if (strEduc.equalsIgnoreCase("вечер")) {
                    educ = FormOfEducationDTO.DISTANCE_EDUCATION;
                } else throw new IncorrectNameEnumException();
                break;
            } catch (IncorrectNameEnumException exception) {
                ConsoleClient.printError("Введено неправильное имя формы обучения!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (CannotBeNullException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return educ;
    }

    public SemesterDTO askSemester() throws IncorrectScriptException {
        String strSem;
        SemesterDTO sem;
        while (true) {
            try {
                ConsoleClient.println("\nВведите семестр обучения");
                ConsoleClient.println("Доступные семестры обучения: Три, Пять, Семь");
                strSem = readLine();
                if (strSem.equals("")) throw new CannotBeNullException();
                if (fileMode) ConsoleClient.println(strSem);
                if (strSem.equalsIgnoreCase("три")) {
                    sem = SemesterDTO.THIRD;
                } else if (strSem.equalsIgnoreCase("пять")) {
                    sem = SemesterDTO.FIFTH;
                } else if (strSem.equalsIgnoreCase("семь")) {
                    sem = SemesterDTO.SEVENTH;
                } else throw new IncorrectNameEnumException();
                break;
            } catch (IncorrectNameEnumException exception) {
                ConsoleClient.printError("Введено неправильное имя семестра обучения!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (CannotBeNullException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return sem;
    }

    public String askAdminName() throws IncorrectScriptException {
        String admin;
        while (true) {
            try {
                ConsoleClient.println("\nВведите имя админа");
                admin = readLine();
                if (admin.equals("")) throw new CannotBeNullException();
                if (fileMode) ConsoleClient.println(admin);
                break;
            } catch (CannotBeNullException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return admin;
    }

    public LocalDateTime askAdminBirthday() throws IncorrectScriptException {
        String strTime;
        LocalDateTime time;
        while (true) {
            try {
                ConsoleClient.println("\nВведите день рождения админа.");
                ConsoleClient.println("Шаблон ввода: день недели, месяц день, год час:минута");
                ConsoleClient.println("Пример ввода: dd-MM-yyyy HH:mm:ss");
                strTime = readLine();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy HH:mm");
                if (strTime.equals("")) time = null;
                else time = LocalDateTime.parse(strTime, dtf);
                if (fileMode) ConsoleClient.println(strTime);
                break;
            } catch (DateTimeParseException exception) { //Обработать исключения
                ConsoleClient.printError("Некорректный ввод даты рождения!");
                ConsoleClient.println("Обратите внимание на ввод дня недели и месяца с заглавной буквы и " +
                        "сокращение названия месяца 3 буквами!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return time;
    }

    public float askAdminWeight() throws IncorrectScriptException {
        String strWeight;
        float weight;
        while (true) {
            try {
                ConsoleClient.println("\nВведите вес админа");
                ConsoleClient.println("Значение поля должно быть больше " + MIN_WEIGHT_ADMIN);
                ConsoleClient.println("Число может быть дробным");
                strWeight = readLine();
                if (fileMode) ConsoleClient.println(strWeight);
                weight = Float.parseFloat(strWeight);
                if (weight <= 0f) throw new GoingBeyondLimitsException();
                break;
            } catch (NumberFormatException exception) {
                ConsoleClient.printError("Значением поля должно являться число!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (GoingBeyondLimitsException exception) {
                ConsoleClient.printError(exception.getMessage());
                if (fileMode) throw new IncorrectScriptException();
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return weight;
    }

    public PersonDTO askPerson() throws IncorrectScriptException {
        PersonDTO person = null;
        while (true) {
            try {
                ConsoleClient.println("\nВы хотите указать админа группы? Да/Нет");
                String answer = readLine();
                if (answer.equalsIgnoreCase("Да")) {
                    setPersonExists();
                    String adminName = askAdminName();
                    LocalDateTime adminBirthday = askAdminBirthday();
                    float adminWeight = askAdminWeight();
                    person = new PersonDTO(adminName, adminBirthday, adminWeight);
                } else if (answer.equalsIgnoreCase("Нет")) {
                    person = new PersonDTO(null, null, 0);
                } else throw new IncorrectInputException();
                break;
            } catch (IncorrectInputException exception) {
                ConsoleClient.printError("Некорректный ввод ответа!");
            } catch (NoSuchElementException exception) {
                ConsoleClient.printError("Значение поля не распознано!");
                if (fileMode) throw new IncorrectScriptException();
            } catch (IllegalStateException exception) {
                ConsoleClient.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return person;
    }

    public boolean changeParameters(String[] parameters, StudyGroupDTO studyGroup) throws IncorrectScriptException {
        try {
            for (String parameter : parameters) {
                switch (parameter.toLowerCase().trim()) {
                    case "имя":
                        studyGroup.setName(askName());
                        break;
                    case "координаты":
                        studyGroup.setCoordinates(askCoordinates());
                        break;
                    case "количество студентов":
                        studyGroup.setStudentsCount(askStudentsCount());
                        break;
                    case "средняя оценка":
                        studyGroup.setAverageMark(askAverageMark());
                        break;
                    case "форма обучения":
                        studyGroup.setFormOfEducation(askFromOfEducation());
                        break;
                    case "семестр":
                        studyGroup.setSemesterEnum(askSemester());
                        break;
                    case "админ группы":
                        studyGroup.setGroupAdmin(askPerson());
                        break;
                    default:
                        throw new IncorrectInputException();
                }
            }
            return true;
        } catch (IncorrectInputException exception) {
            ConsoleClient.printError("Параметры группы введены неверно!\n");
            if (fileMode) throw new IncorrectScriptException();
        }
        return false;
    }

    private String readLine() {
        return consoleClient.readLine();
    }

    public static void setFileMode() {
        fileMode = true;
    }


    public static void setUserMode() {
        fileMode = false;
    }


    public boolean getPersonExist() {
        return personExist;
    }

    public void setPersonExists() {
        personExist = true;
    }

    public void setPersonDoesntExist() {
        personExist = false;
    }

    public static boolean getFileMode() {
        return fileMode;
    }
}
