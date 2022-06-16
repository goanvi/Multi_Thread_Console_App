package controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import model.StudyGroup;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ParserCSV {
    public static String toCSV(Collection<StudyGroup> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        Object[] collArray = collection.toArray();
        for (Object obj : collArray) {
            stringBuilder.append(Arrays.toString(makeData(obj)).replace("[", "").replace("]", ""));
            stringBuilder.append("\n");
        }
        return String.valueOf(stringBuilder);
    }

    public static TreeSet<StudyGroup> readFile(String file) {
        Comparator<StudyGroup> sgc = new StudyGroupComparator();
        TreeSet<StudyGroup> collection = new TreeSet<>(sgc);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8));
             CSVReader reader = new CSVReader(bufferedReader)) {
            List<String[]> input = reader.readAll();
            input.forEach((object) -> {
                collection.add(new StudyGroup(object));
            });
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return collection;
    }

    private static String[] makeStudyGroupHeader() {
        return new String[]{"id", "name", "coordinatesX", "coordinatesY", "creationDate",
                "studentsCount", "averageMark", "formOfEducation", "semesterEnum", "groupAdmin", "birthday", "weight", "passportID"};
    }

    private static String[] makeHeader(Object object) {
        String[] recordHd = object.toString().split(",");
        String[] header = new String[recordHd.length];
        for (int i = 0; i < recordHd.length; i++) {
            String[] sword = recordHd[i].split("=");
            header[i] = sword[0];
        }
        return header;
    }

    private static String[] makeData(Object object) {
        String[] recordDt = object.toString().split(",");
        String[] data = new String[13];
        for (int i = 0; i < recordDt.length; i++) {
            String[] sword = recordDt[i].split("=");
            data[i] = sword[sword.length - 1];
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = "\"" + data[i] + "\"";
        }
        return data;
    }

    public TreeSet<StudyGroup> csvFromData(String string) {
        Comparator<StudyGroup> sgc = new StudyGroupComparator(); //Будут использовать в main, добовление коллекции через addAll
        TreeSet<StudyGroup> collection = new TreeSet<>(sgc);
        String[] bigData = string.split("\n");
        //String[] header = bigData[0].split(",");
        for (int i = 1; i < bigData.length; i++) {
            String[] data = bigData[i].replace(" ", "").split(",");
            collection.add(new StudyGroup(data));
        }
        return collection;
    }

}
