import java.util.HashMap;

public class Epic extends Task {
    private final HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description, Progress.NEW);
    }

    public HashMap<Integer, Subtask> getSubtaskHashMap() {
        return subtaskHashMap;
    }

    public void addSubtask(Subtask newSubtask) {
        for (Integer key : subtaskHashMap.keySet()) {
            if (subtaskHashMap.get(key).equals(newSubtask)) {
                System.out.println("Такая подзадача уже существует");
                return;
            }
        }
        subtaskHashMap.put(newSubtask.getId(), newSubtask);
    }

    public void print() {
        System.out.println(id + ". Эпик: " + name + "|\t Описание: " + description + "|\t Статус: " + progress);
        System.out.print("\tСписок подзадач: ");
        printSubtask();
    }

    public void printSubtask() {
        if (!subtaskHashMap.isEmpty()) {
            System.out.println("\n\t--------------------");
            for (Integer key : subtaskHashMap.keySet()) {
                System.out.print("\t");
                subtaskHashMap.get(key).print();
            }
            System.out.println("\t--------------------");
        } else {
            System.out.println("Список пуст!");
        }
    }
}
