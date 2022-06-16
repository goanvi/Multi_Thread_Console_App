package controller;

import model.StudyGroup;

import java.util.Comparator;

public class StudyGroupComparator implements Comparator<StudyGroup> {
    @Override
    public int compare(StudyGroup group1, StudyGroup group2) {
        int result;
        result = Long.compare(group1.getStudentsCount(), group2.getStudentsCount());
        if (result == 0) {
            result = Double.compare(group1.getAverageMark(), group2.getAverageMark());
        }
        return result;
    }
}
