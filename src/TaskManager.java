import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class TaskManager {
    static int allTaskCount = 0;
    private final HashMap<Integer, Task> taskMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();

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
                    int idEpic = subtaskHashMap.get(id).getIdEpic();
                    subtaskHashMap.remove(id);
                    updateEpic(idEpic);
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
            Task task = taskMap.get(newTask.getId());
            if (task != null) {
                checkInclude = true;
            }
        }
        if (checkInclude) {
            return -1;
        } else {
            allTaskCount++;
            newTask.setId(allTaskCount);
            taskMap.put(newTask.getId(), newTask);
            return newTask.getId();
        }
    }

    public int addEpic(Epic newEpic) {
        boolean checkInclude = false;
        if (!epicMap.isEmpty()) {
            Epic epic = epicMap.get(newEpic.getId());
            if (epic != null) {
                checkInclude = true;
            }
        }
        if (checkInclude) {
            return -1;
        } else {
            allTaskCount++;
            newEpic.setId(allTaskCount);
            epicMap.put(newEpic.getId(), newEpic);
            return newEpic.getId();
        }
    }

    public int addSubtask(Subtask newSubtask) {
        if (epicMap.containsKey(newSubtask.getIdEpic())) {
            Epic currentEpic = epicMap.get(newSubtask.getIdEpic());
            allTaskCount++;
            newSubtask.setId(allTaskCount);
            currentEpic.addSubtask(newSubtask);
            updateEpic(newSubtask.getIdEpic());
            return newSubtask.getId();
        } else {
            return -1;
        }
    }

    public void updateEpic(int id) {
        HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(id).getSubtaskHashMap();
        if (subtaskHashMap.isEmpty()) {
            epicMap.get(id).setProgress(Progress.NEW);
        }
        int countSubtaskDone = 0;
        for (Integer keySubtask : subtaskHashMap.keySet()) {
            if (subtaskHashMap.get(keySubtask).progress == Progress.IN_PROGRESS) {
                epicMap.get(id).setProgress(Progress.IN_PROGRESS);
                return;
            }
            if (subtaskHashMap.get(keySubtask).progress == Progress.DONE) {
                countSubtaskDone++;
                if (countSubtaskDone == subtaskHashMap.size()) {
                    epicMap.get(id).setProgress(Progress.DONE);
                    return;
                } else {
                    epicMap.get(id).setProgress(Progress.IN_PROGRESS);
                }
            } else if (subtaskHashMap.get(keySubtask).progress == Progress.NEW) {
                if (countSubtaskDone == 0) {
                    epicMap.get(id).setProgress(Progress.NEW);
                } else if (countSubtaskDone > 0) {
                    epicMap.get(id).setProgress(Progress.IN_PROGRESS);
                }
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
