package controller;

import controller.exceptions.EmptyCollectionException;
import model.Semester;
import model.StudyGroup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectionManager { //Надо будет дописать loadCollection
    private final StudyGroupComparator comparator = new StudyGroupComparator();
    LocalDateTime lastSaveTime;
    LocalDateTime lastLoadTime;
    private TreeSet<StudyGroup> studyGroupCollection = new TreeSet<>(comparator);

    public CollectionManager(String readFile) {
        loadCollection(readFile);
        studyGroupCollection.forEach((group) -> IdManager.saveStudyGroupID(group.getID()));
    }

    public void clearCollection() {
        studyGroupCollection.clear();
    }

    public void addToCollection(StudyGroup studyGroup) {
        studyGroupCollection.add(studyGroup);
    }

    public void addCollection(TreeSet<StudyGroup> collection) {
        this.studyGroupCollection = collection;
    }

    public void removeGreater(StudyGroup studyGroup) throws EmptyCollectionException {
        if (studyGroupCollection.size() == 0) throw new EmptyCollectionException();
        Set<StudyGroup> removeGroups = studyGroupCollection.stream().filter((StudyGroup group) -> group.compareTo(studyGroup) > 0).collect(Collectors.toSet());
        removeGroups.forEach((group) -> studyGroupCollection.remove(group));
    }

    public void removeLower(StudyGroup studyGroup) throws EmptyCollectionException {
        if (studyGroupCollection.size() == 0) throw new EmptyCollectionException();
        Set<StudyGroup> removeGroups = studyGroupCollection.stream().filter((StudyGroup group) -> group.compareTo(studyGroup) < 0).collect(Collectors.toSet());
        removeGroups.forEach((group) -> studyGroupCollection.remove(group));
    }

    public void loadCollection(String file) {
        studyGroupCollection = ParserCSV.readFile(file);
        lastLoadTime = LocalDateTime.now();
    }

    public StudyGroup getAnyBySemesterEnum(Semester semester) throws EmptyCollectionException {
        if (studyGroupCollection.isEmpty()) throw new EmptyCollectionException();
        Optional<StudyGroup> output = studyGroupCollection.stream().filter((group) -> group.getSemesterEnum().equals(semester)).findAny();
        return output.orElse(null);
    }

    public StudyGroup getByValue(StudyGroup studyGroup) throws EmptyCollectionException {
        if (studyGroupCollection.size() == 0) throw new EmptyCollectionException();
        for (StudyGroup group : studyGroupCollection) {
            if (group.equals(studyGroup)) return group;
        }
        return null;
    }

    public StudyGroup getByID(Integer id) throws EmptyCollectionException {
        if (studyGroupCollection.size() == 0) throw new EmptyCollectionException();
        Optional<StudyGroup> output = studyGroupCollection.stream().filter((group) -> group.getID().equals(id)).findAny();
        return output.orElse(null);
    }

    public long getSumOfStudentsCount() throws EmptyCollectionException {
        long sum;
        if (studyGroupCollection.size() == 0) throw new EmptyCollectionException();
        sum = studyGroupCollection.stream().mapToLong(StudyGroup::getStudentsCount).sum();
        return sum;
    }

    public List<StudyGroup> getLessThanStudentsCount(long studCount) throws EmptyCollectionException {
        if (studyGroupCollection.size() == 0) throw new EmptyCollectionException();
        return studyGroupCollection.stream().filter(group -> group.getStudentsCount() < studCount).collect(Collectors.toList());
    }

    public void remove(StudyGroup studyGroup) {
        studyGroupCollection.remove(studyGroup);
    }

    public TreeSet<StudyGroup> getCollection() {
        return studyGroupCollection;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public void setLastSaveTime() {
        lastSaveTime = LocalDateTime.now();
    }

    public String getCollectionType() {
        return studyGroupCollection.getClass().getName();
    }

    public int getCollectionSize() {
        return studyGroupCollection.size();
    }

    public LocalDateTime getLastLoadTime() {
        return lastLoadTime;
    }
}
