import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
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
                    System.out.print("Введите имя Эпика: ");
                    name = scanner.nextLine();
                    System.out.print("Введите описание Эпика: ");
                    description = scanner.nextLine();
                    Epic newEpic = new Epic(name, description);
                    taskManager.addEpic(newEpic);
                    break;
                case 2:
                    System.out.print("Введите имя задачи: ");
                    name = scanner.nextLine();
                    System.out.print("Введите описание задачи: ");
                    description = scanner.nextLine();
                    printMenuStatus();
                    numberOfProgress = Integer.parseInt(scanner.nextLine());
                    progress = setProgress(numberOfProgress);
                    if (progress != null) {
                        Task newTask = new Task(name, description, progress);
                        if (taskManager.addTask(newTask) > 0) {
                            System.out.println("Добавлена задача под номером: " + newTask.getId());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Введите ID Эпика которому хотите добавить подзадачу: ");
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
                            Subtask newSubtask = new Subtask(name, description, idEpic, progress);
                            taskManager.addSubtask(newSubtask);
                        }
                    } else {
                        System.out.println("Такого Эпика нет!");
                    }
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
                    taskManager.printById(id);
                    break;
                case 8:
                    System.out.print("Введите ID Эпика подзадачи которого хотите вывести: ");
                    id = Integer.parseInt(scanner.nextLine());
                    taskManager.printSubtaskByIdEpic(id);
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
                    System.out.print("Введите номер задачи статус хоторой хотите обновить: ");
                    id = Integer.parseInt(scanner.nextLine());
                    printMenuStatus();
                    numberOfProgress = Integer.parseInt(scanner.nextLine());
                    progress = setProgress(numberOfProgress);
                    if (progress != null) {
                        if (taskManager.checkIdInTask(id)) {
                            Task task = taskManager.getTask(id);
                            task.setProgress(progress);
                        } else if (taskManager.checkIdSubtask(id)) {
                            Subtask subtask = taskManager.getSubtask(id);
                            subtask.setProgress(progress);
                            taskManager.updateSubtask(subtask);
                        } else {
                            System.out.println("Невозможно изменить статус у данного номера!");
                        }
                    }
                    break;
                case 14:
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
        System.out.println("14. Выход");
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
}
