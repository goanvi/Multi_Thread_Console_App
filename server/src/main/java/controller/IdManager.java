package controller;

import java.util.LinkedHashSet;
import java.util.Set;

public class IdManager {
    private static final Set<Integer> idStudyGroupBuffer = new LinkedHashSet<>();
    private static final Set<String> idPersonBuffer = new LinkedHashSet<>();

    public static int setStudyGroupID(Integer id) {
        int id0 = id;
        while (true) {
            if (idStudyGroupBuffer.contains(id0)) {
                id0 = changeId(id0);
            } else {
                break;
            }
        }
        return id0;
    }

    public static String setPersonID(Integer id) {
        Integer passportID0 = id;
        while (true) {
            if (idPersonBuffer.contains(passportID0.toString())) {
                passportID0 = changeId(passportID0);
            } else {
                break;
            }
        }
        return Integer.toString(passportID0);
    }

    public static void removePersonID(Integer id) {
        idPersonBuffer.remove(id.toString());
    }

    public static boolean containsStudyGroupID(Integer id) {
        return idStudyGroupBuffer.contains(id);
    }

    public static boolean containsPersonID(String id) {
        return idPersonBuffer.contains(id);
    }

    public static void savePersonID(String id) {
        idPersonBuffer.add(id);
    }

    public static void saveStudyGroupID(Integer id) {
        idStudyGroupBuffer.add(id);
    }

    public static void removeStudyGroupID(Integer id) {
        idStudyGroupBuffer.remove(id);
    }

    public static int changeId(int id) {
        return id + (int) ((Math.random() * 1000) + 1);
    }

    public static Set<Integer> getIdStudyGroupBuffer() {
        return idStudyGroupBuffer;
    }
}