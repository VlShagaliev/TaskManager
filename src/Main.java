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
            command = Integer.parseInt(scanner.nextLine());
            switch (command) {
                case 1:
                    System.out.print("Введите имя Эпика: ");
                    name = scanner.nextLine();
                    System.out.print("Введите описание Эпика: ");
                    description = scanner.nextLine();
                    id = taskManager.getTaskAndEpicCount();
                    Epic newEpic = new Epic(name, description, ++id);
                    taskManager.addEpic(newEpic);
                    break;
                case 2:
                    System.out.print("Введите имя задачи: ");
                    name = scanner.nextLine();
                    System.out.print("Введите описание задачи: ");
                    description = scanner.nextLine();
                    id = taskManager.getTaskAndEpicCount();
                    Task newTask = new Task(name, description, ++id);
                    taskManager.addTask(newTask);
                    break;
                case 3:
                    System.out.print("Введите ID Эпика которому хотите добавить подзадачу: ");
                    int idEpic = Integer.parseInt(scanner.nextLine());
                    if (taskManager.checkIdInEpic(idEpic)) {
                        System.out.print("Введите имя подзадачи: ");
                        name = scanner.nextLine();
                        System.out.print("Введите описание подзадачи: ");
                        description = scanner.nextLine();
                        Epic epicById = taskManager.getEpic(idEpic);
                        int idForSubtask = epicById.getCountSubtask();
                        Subtask newSubtaskForIdEpic = new Subtask(name, description, ++idForSubtask);
                        taskManager.addSubtask(idEpic, newSubtaskForIdEpic);
                    } else {
                        System.out.println("Такого Эпика нет!");
                    }
                    break;
                case 4:
                    taskManager.printAll();
                    break;
                case 5:
                    System.out.print("Введите ID задачи или Эпика который хотите вывести: ");
                    id = Integer.parseInt(scanner.nextLine());
                    taskManager.printById(id);
                    break;
                case 6:
                    System.out.print("Введите ID Эпика который хотите вывести: ");
                    id = Integer.parseInt(scanner.nextLine());
                    taskManager.printSubtaskByIdEpic(id);
                    break;
                case 7:
                    taskManager.clearAll();
                    return;
                case 8:
                    System.out.print("Введите ID задачи которую хотите удалить: ");
                    id = Integer.parseInt(scanner.nextLine());
                    taskManager.deleteById(id);
                    break;
                case 9:
                    System.out.print("Введите ID задачи статус которой хотите обновить: ");
                    id = Integer.parseInt(scanner.nextLine());
                    int idProgress;
                    Epic epic = taskManager.getEpic(id);
                    if (epic.getSubtaskHashMap() != null && !epic.getSubtaskHashMap().isEmpty()) {
                        if (taskManager.checkIdInEpic(id)) {
                            System.out.print("Введите номер подзадачи статус хоторой хотите обновить: ");
                            int idSubtask = Integer.parseInt(scanner.nextLine());
                            if (taskManager.checkIdSubtask(id, idSubtask)) {
                                printMenuStatus();
                                idProgress = Integer.parseInt(scanner.nextLine());
                                if (idProgress == 1 || idProgress == 2) {
                                    taskManager.updateEpic(id, idSubtask, idProgress);
                                } else {
                                    System.out.println("Такого статуса нет!");
                                }
                            } else {
                                System.out.println("Такой подзадачи нет!");
                            }
                        } else if (taskManager.checkIdInTask(id)) {
                            printMenuStatus();
                            idProgress = Integer.parseInt(scanner.nextLine());
                            if (idProgress == 1 || idProgress == 2) {
                                taskManager.updateTask(id, idProgress);
                            } else {
                                System.out.println("Такого статуса нет!");
                            }
                        }
                    } else {
                        System.out.println("Список подзадач пуст!");
                    }
                    break;
                case 10:
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
        System.out.println("5. Распечатать задачу или Эпик");
        System.out.println("6. Распечатать подзадачи Эпика");
        System.out.println("7. Очистить все задачи");
        System.out.println("8. Удалить задачу по ID");
        System.out.println("9. Обновить статус");
        System.out.println("10. Выход");
        System.out.println("--------------------");
    }

    public static void printMenuStatus() {
        System.out.println("Введите номер нового статуса подзадачи: ");
        System.out.println("1. IN_PROGRESS");
        System.out.println("2. DONE");
    }
}
