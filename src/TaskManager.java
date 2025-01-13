import java.util.HashMap;

public final class TaskManager {
    static int taskCount = 0;
    HashMap<Integer, Task> taskMap = new HashMap<>();

    public void printTasks() {
        for (Integer key : taskMap.keySet()) {
            taskMap.get(key).print();
        }
    }

    public void deleteTask() {
        taskMap.clear();
    }

    public void getTaskById(int id) {
        taskMap.get(id).print();
    }

    public void addTask(Task task) {
        taskMap.put(++taskCount, task);
    }

    public void updateTask(int id, Progress progress) {
        taskMap.get(id).setProgress(progress);
    }

    public void deleteTaskById(int id) {
        taskMap.remove(id);
    }
}
