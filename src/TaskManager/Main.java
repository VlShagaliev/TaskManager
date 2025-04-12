package TaskManager;

import fileBackedTaskManager.FileBackedTaskManager;
import managers.HistoryManager;
import managers.Managers;
import model.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Managers managers = new Managers();
    static TaskManager taskManager = managers.getDefault();

    public static void main(String[] args) throws FileBackedTaskManager.ManagerSaveException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("Backup.csv");
        taskManager = FileBackedTaskManager.loadFromFile(file);
        HistoryManager historyManager = taskManager.getHistoryManager();
        int command;
        while (true) {
            printMenu();
            String name;
            String description;
            int id;
            int numberOfProgress;
            Progress progress;
            command = Integer.parseInt(scanner.nextLine());
            switch (command) {
                case 1:
                    /*System.out.print("Введите имя Эпика: ");
                    name = scanner.nextLine();
                    System.out.print("Введите описание Эпика: ");
                    description = scanner.nextLine();
                    model.Epic newEpic = new model.Epic(name, description);
                    if (taskManager.addEpic(newEpic) > 0) {
                        System.out.println("Добавлена задача под номером: " + newEpic.getId());
                    }*/
                    additionEpic(taskManager);
                    break;
                case 2:
                    /*System.out.print("Введите имя задачи: ");
                    name = scanner.nextLine();
                    System.out.print("Введите описание задачи: ");
                    description = scanner.nextLine();
                    printMenuStatus();
                    numberOfProgress = Integer.parseInt(scanner.nextLine());
                    progress = setProgress(numberOfProgress);
                    if (progress != null) {
                        model.Task newTask = new model.Task(name, description, progress);
                        if (taskManager.addTask(newTask) > 0) {
                            System.out.println("Добавлена задача под номером: " + newTask.getId());
                        } else {
                            System.out.println("Такая задача уже существует!");
                        }
                    }*/
                    additionTask(taskManager);
                    break;
                case 3:
                    /*System.out.print("Введите ID Эпика которому хотите добавить подзадачу: ");
                    int idEpic = Integer.parseInt(scanner.nextLine());
                    if (taskManager.checkIdInEpic(idEpic)) {
                        System.out.print("Введите имя подзадачи: ");
                        name = scanner.nextLine();
                        System.out.print("Введите описание подзадачи: ");
                        description = scanner.nextLine();
                        printMenuStatus();
                        numberOfProgress = Integer.parseInt(scanner.nextLine());
                        progress = setProgress(numberOfProgress);
                        if (progress != null) {
                            model.Subtask newSubtask = new model.Subtask(name, description, idEpic, progress);
                            if (taskManager.addSubtask(newSubtask) > 0) {
                                System.out.println("Добавлена задача под номером: " + newSubtask.getId());
                            } else {
                                System.out.println("Такая подзадача уже существует!");
                            }
                        }

                    } else {
                        System.out.println("Такого Эпика нет!");
                    }*/
                    additionSubtask(taskManager);
                    break;
                case 4:
                    taskManager.printAllTask();
                    break;
                case 5:
                    taskManager.printAllEpic();
                    break;
                case 6:
                    taskManager.printAllSubtask();
                    break;
                case 7:
                    System.out.print("Введите ID задачи/Эпика/подзадачи которую хотите вывести: ");
                    id = Integer.parseInt(scanner.nextLine());
                    if (taskManager.checkIdSubtask(id) || taskManager.checkIdInEpic(id) || taskManager.checkIdInTask(id)) {
                        taskManager.printById(id);
                    } else {
                        System.out.println("Такой задачи/Эпика/подзадачи нет!");
                    }
                    break;
                case 8:
                    System.out.print("Введите ID Эпика подзадачи которого хотите вывести: ");
                    id = Integer.parseInt(scanner.nextLine());
                    if (taskManager.checkIdSubtask(id)) {
                        taskManager.printSubtaskByIdEpic(id);
                    } else {
                        System.out.println("Такого Эпика нет!");
                    }
                    break;
                case 9:
                    taskManager.clearTask();
                    break;
                case 10:
                    taskManager.clearEpic();
                    break;
                case 11:
                    taskManager.clearSubtask();
                    break;
                case 12:
                    System.out.print("Введите ID: ");
                    id = Integer.parseInt(scanner.nextLine());
                    taskManager.deleteById(id);
                    break;
                case 13:
                    System.out.print("Введите номер задачи статус которой хотите обновить: ");
                    id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Вы хотите обновить имя и описание задачи?: ");
                    System.out.println("1.Да \n2.Нет");
                    int replace = Integer.parseInt(scanner.nextLine());
                    if (replace == 1) {
                        if (taskManager.checkIdInTask(id)) {
                            System.out.println("Введите новое имя задачи");
                            name = scanner.nextLine();
                            System.out.println("Введите новое описание задачи");
                            description = scanner.nextLine();
                            printMenuStatus();
                            numberOfProgress = Integer.parseInt(scanner.nextLine());
                            progress = setProgress(numberOfProgress);
                            if (progress != null) {
                                Task task = new Task(name, description, progress);
                                task.setId(id);
                                taskManager.updateTask(task);
                            }
                        } else if (taskManager.checkIdInEpic(id)) {
                            System.out.println("Введите новое имя Эпика");
                            name = scanner.nextLine();
                            System.out.println("Введите новое описание Эпика");
                            description = scanner.nextLine();
                            Epic epic = new Epic(name, description);
                            epic.setId(id);
                            taskManager.updateEpic(epic);
                        } else if (taskManager.checkIdSubtask(id)) {
                            System.out.println("Введите новое имя подзадачи");
                            name = scanner.nextLine();
                            System.out.println("Введите новое описание подзадачи");
                            description = scanner.nextLine();
                            printMenuStatus();
                            numberOfProgress = Integer.parseInt(scanner.nextLine());
                            progress = setProgress(numberOfProgress);
                            Subtask oldSubtask = (Subtask) taskManager.getSubtasks().get(id);
                            if (progress != null) {
                                Subtask subtask = new Subtask(name, description, oldSubtask.getIdEpic(), progress);
                                subtask.setId(oldSubtask.getId());
                                taskManager.updateSubtask(subtask);
                            }
                        } else {
                            System.out.println("Такого действия нет!");
                        }
                    } else if (replace == 2) {
                        if (taskManager.checkIdInTask(id)) {
                            printMenuStatus();
                            numberOfProgress = Integer.parseInt(scanner.nextLine());
                            progress = setProgress(numberOfProgress);
                            if (progress != null) {
                                Task task = null;
                                List<Task> taskList = taskManager.getTasks();
                                for (Task taskFromList : taskList){
                                    if (taskFromList.getId() == id){
                                        task = taskFromList;
                                        break;
                                    }
                                }
                                task.setProgress(progress);
                                taskManager.updateTask(task);
                            }
                        } else if (taskManager.checkIdSubtask(id)) {
                            printMenuStatus();
                            numberOfProgress = Integer.parseInt(scanner.nextLine());
                            progress = setProgress(numberOfProgress);
                            if (progress != null) {
                                Subtask subtask = null;
                                List<Task> subTaskList = taskManager.getSubtasks();
                                for (Task subtaskFromList : subTaskList){
                                    if (subtaskFromList.getId() == id){
                                        subtask = (Subtask) subtaskFromList;
                                        break;
                                    }
                                }
                                subtask.setProgress(progress);
                                taskManager.updateSubtask(subtask);
                            }
                        } else {
                            System.out.println("Невозможно изменить статус у данного номера!");
                        }
                    } else {
                        System.out.println("Такого действия нет!");
                    }
                    break;
                case 14:
                    if (!taskManager.getHistory().isEmpty()) {
                        System.out.println("История просмотров: ");
                        for (Task task : taskManager.getHistory()) {
                            task.print();
                        }
                    } else {
                        System.out.println("История просмотров пуста!");
                    }
                    break;
                case 15:
                    System.out.print("Введите номер истории которую хотите удалить: ");
                    id = Integer.parseInt(scanner.nextLine());
                    historyManager.remove(id);
                    break;
                case 16:
                    return;
                default:
                    System.out.println("Такого действия нет!");
            }
        }
    }

    public static void printMenu() {
        System.out.println("--------------------");
        System.out.println("Выберите действие: ");
        System.out.println("1. Добавить Эпик");
        System.out.println("2. Добавить задачу");
        System.out.println("3. Добавить подзадачу Эпика");
        System.out.println("4. Распечатать все задачи");
        System.out.println("5. Распечатать все Эпики");
        System.out.println("6. Распечатать все подзадачи");
        System.out.println("7. Распечатать задачу/Эпик/подзадачу по номеру");
        System.out.println("8. Распечатать все подзадачи по номеру Эпика");
        System.out.println("9. Удалить все задачи");
        System.out.println("10. Удалить все Эпики");
        System.out.println("11. Удалить все подзадачи");
        System.out.println("12. Удалить задачу/Эпик/подзадачу по номеру");
        System.out.println("13. Обновить статус");
        System.out.println("14. Получить историю");
        System.out.println("15. Удалить историю по номеру");
        System.out.println("16. Выход");
        System.out.println("--------------------");
    }

    public static void printMenuStatus() {
        System.out.println("Введите номер статуса задачи: ");
        System.out.println("1. NEW");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. DONE");
    }

    public static Progress setProgress(int numberOfProgress) {
        switch (numberOfProgress) {
            case 1:
                return Progress.NEW;
            case 2:
                return Progress.IN_PROGRESS;
            case 3:
                return Progress.DONE;
            default:
                System.out.println("Вы выбрали неверный статус задачи.");
                return null;
        }
    }

    private static void additionEpic(TaskManager taskManager) throws FileBackedTaskManager.ManagerSaveException {
        Epic epic = new Epic("1длоотываа", "1dkiu5d");
        if (taskManager.addEpic(epic) > 0) {
            System.out.println("Добавлена задача под номером: " + epic.getId());
        }
        epic = new Epic("2slkdnfbjb", "2kjsbdf");
        if (taskManager.addEpic(epic) > 0) {
            System.out.println("Добавлена задача под номером: " + epic.getId());
        }
        epic = new Epic("3sdkfjbrf", "3sdkjfb");
        if (taskManager.addEpic(epic) > 0) {
            System.out.println("Добавлена задача под номером: " + epic.getId());
        }
        epic = new Epic("4dfmg5", "4kjsdkfb");
        if (taskManager.addEpic(epic) > 0) {
            System.out.println("Добавлена задача под номером: " + epic.getId());
        }
    }

    private static void additionTask(TaskManager taskManager) throws FileBackedTaskManager.ManagerSaveException {
        Task task = new Task("5sdfkjb45", "5kjsdfgjfb", Progress.NEW);
        taskManager.addTask(task);
        task = new Task("6afjhbsdf", "ksdfu4", Progress.IN_PROGRESS);
        taskManager.addTask(task);
        task = new Task("7sdkjbfbi4", "7sdfjbldsf", Progress.DONE);
        taskManager.addTask(task);
    }

    private static void additionSubtask(TaskManager taskManager) throws FileBackedTaskManager.ManagerSaveException {
        Subtask subtask = new Subtask("8sdlknfb", "8aslkfb", 2, Progress.NEW);
        taskManager.addSubtask(subtask);
        subtask = new Subtask("9sdlknfb", "9aslkfb", 3, Progress.NEW);
        taskManager.addSubtask(subtask);
        subtask = new Subtask("10sdlknfb", "10aslkfb", 3, Progress.IN_PROGRESS);
        taskManager.addSubtask(subtask);
        subtask = new Subtask("11sdlknfb", "11aslkfb", 3, Progress.DONE);
        taskManager.addSubtask(subtask);
    }
}
