package model;

import fileBackedTaskManager.FileBackedTaskManager;
import managers.HistoryManager;

import java.util.List;

public interface TaskManager {

    void printAllTask();

    void printAllEpic();

    void printAllSubtask();

    void printById(int id);

    void printSubtaskByIdEpic(int idEpic);

    void clearTask();

    void clearEpic();

    void clearSubtask();

    void deleteTaskById(int id) throws FileBackedTaskManager.ManagerSaveException;

    void deleteEpicById(int id) throws FileBackedTaskManager.ManagerSaveException;

    void deleteSubtaskById(int id) throws FileBackedTaskManager.ManagerSaveException;

    void deleteById(int id) throws FileBackedTaskManager.ManagerSaveException;

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    int addTask(Task newTask) throws FileBackedTaskManager.ManagerSaveException;

    int addEpic(Epic newEpic) throws FileBackedTaskManager.ManagerSaveException;

    int addSubtask(Subtask newSubtask) throws FileBackedTaskManager.ManagerSaveException;

    void updateEpicStatus(int id) throws FileBackedTaskManager.ManagerSaveException;

    void updateTask(Task task) throws FileBackedTaskManager.ManagerSaveException;

    void updateEpic(Epic epic) throws FileBackedTaskManager.ManagerSaveException;

    void updateSubtask(Subtask subtask) throws FileBackedTaskManager.ManagerSaveException;

    List<Task> getTasks();

    List<Task> getEpics();

    List<Task> getSubtasks();

    boolean checkIdInEpic(int id);

    boolean checkIdInTask(int id);

    boolean checkIdSubtask(int id);

    List<Task> getHistory();

    HistoryManager getHistoryManager();
}
