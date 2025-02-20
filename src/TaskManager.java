import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class TaskManager {
    static int allTaskCount = 0;
    final HashMap<Integer, Task> taskMap = new HashMap<>();
    final HashMap<Integer, Epic> epicMap = new HashMap<>();

    public void printAllTask() {
        if (!taskMap.isEmpty()) {
            for (Integer key : taskMap.keySet()) {
                taskMap.get(key).print();
            }
        } else {
            System.out.println("Список задач пуст!");
        }
    }

    public void printAllEpic() {
        if (!epicMap.isEmpty()) {
            for (Integer key : epicMap.keySet()) {
                epicMap.get(key).print();
            }
        } else {
            System.out.println("Список Эпиков пуст!");
        }
    }

    public void printAllSubtask() {
        boolean check = false;
        if (!epicMap.isEmpty()) {
            for (Integer key : epicMap.keySet()) {
                HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(key).getSubtaskHashMap();
                for (Integer keySubtask : subtaskHashMap.keySet()) {
                    subtaskHashMap.get(keySubtask).print();
                    check = true;
                }
            }

        }
        if (!check) {
            System.out.println("Список подзадач пуст!");
        }
    }

    public void printById(int id) {
        if (checkIdInTask(id)) {
            taskMap.get(id).print();
        } else if (checkIdInEpic(id)) {
            epicMap.get(id).print();
        } else if (checkIdSubtask(id)) {
            for (Integer key : epicMap.keySet()) {
                HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(key).getSubtaskHashMap();
                if (subtaskHashMap.containsKey(id)) {
                    subtaskHashMap.get(id).print();
                    return;
                }
            }
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    public void printSubtaskByIdEpic(int idEpic) {
        if (!epicMap.isEmpty()) {
            getEpic(idEpic).printSubtask();
        } else {
            System.out.println("Такого Эпика нет!");
        }
    }

    public void clearTask() {
        taskMap.clear();
    }

    public void clearEpic() {
        epicMap.clear();
    }

    public void clearSubtask() {
        if (!epicMap.isEmpty())
            for (Integer key : epicMap.keySet()) {
                epicMap.get(key).getSubtaskHashMap().clear();
            }
    }

    public void deleteTaskById(int id) {
        taskMap.remove(id);
    }

    public void deleteEpicById(int id) {
        epicMap.remove(id);
    }

    public void deleteSubtaskById(int id) {
        for (Integer key : epicMap.keySet()) {
            HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(key).getSubtaskHashMap();
            if (!subtaskHashMap.isEmpty()) {
                if (subtaskHashMap.containsKey(id)) {
                    subtaskHashMap.remove(id);
                    return;
                }
            }
        }
    }

    public void deleteById(int id) {
        if (checkIdInTask(id)) {
            deleteTaskById(id);
            return;
        } else if (checkIdInEpic(id)) {
            deleteEpicById(id);
            return;
        } else if (checkIdSubtask(id)) {
            deleteSubtaskById(id);
            return;
        }
        System.out.println("Такое задачи нет!");
    }

    public Task getTask(int id) {
        return taskMap.get(id);
    }

    public Epic getEpic(int id) {
        return epicMap.get(id);
    }

    public Subtask getSubtask(int id) {
        for (Integer key : epicMap.keySet()) {
            HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(key).getSubtaskHashMap();
            if (subtaskHashMap.containsKey(id)) {
                return subtaskHashMap.get(id);
            }
        }
        return null;
    }

    public int addTask(Task newTask) {
        boolean checkInclude = false;
        if (!taskMap.isEmpty()) {
            for (Task task : taskMap.values()) {
                if (task.equals(newTask)) {
                    checkInclude = true;
                    break;
                }
            }
        }
        if (checkInclude) {
            System.out.println("Такая задача уже существует!");
            return -1;
        } else {
            allTaskCount++;
            newTask.setId(allTaskCount);
            taskMap.put(newTask.getId(), newTask);
            return newTask.getId();
        }
    }

    public void addEpic(Epic newEpic) {
        boolean checkInclude = false;
        if (!epicMap.isEmpty()) {
            for (Epic epic : epicMap.values()) {
                if (epic.equals(newEpic)) {
                    checkInclude = true;
                    break;
                }
            }
        }
        if (checkInclude) {
            System.out.println("Такой Эпик уже существует!");
        } else {
            allTaskCount++;
            newEpic.setId(allTaskCount);
            epicMap.put(newEpic.getId(), newEpic);
        }
    }

    public void addSubtask(Subtask newSubtask) {
        if (epicMap.containsKey(newSubtask.getIdEpic())) {
            Epic currentEpic = epicMap.get(newSubtask.getIdEpic());
            allTaskCount++;
            newSubtask.setId(allTaskCount);
            currentEpic.addSubtask(newSubtask);
            updateEpic(newSubtask.getIdEpic());
        } else {
            System.out.println("Данного Эпика нет в списке!");
        }

    }

    public void updateEpic(int id) {
        HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(id).getSubtaskHashMap();
        for (Integer keySubtask : subtaskHashMap.keySet()) {
            if (subtaskHashMap.get(keySubtask).progress == Progress.IN_PROGRESS) {
                epicMap.get(id).progress = Progress.IN_PROGRESS;
                return;
            }
            if (subtaskHashMap.get(keySubtask).progress == Progress.DONE) {
                epicMap.get(id).progress = Progress.DONE;
            }
        }
    }

    public void updateSubtask(Subtask subtask) {
        updateEpic(subtask.getIdEpic());
    }

    public boolean checkIdInEpic(int id) {
        return epicMap.containsKey(id);
    }

    public boolean checkIdInTask(int id) {
        return taskMap.containsKey(id);
    }

    public boolean checkIdSubtask(int id) {
        if (!epicMap.isEmpty()) {
            for (Integer key : epicMap.keySet()) {
                if (epicMap.get(key).getSubtaskHashMap().containsKey(id)) {
                    return true;
                }
            }
        }
        return false;
    }
}
