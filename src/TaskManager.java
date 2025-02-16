import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public final class TaskManager {

    public int getTaskAndEpicCount() {
        return taskAndEpicCount;
    }

    static int taskAndEpicCount = 0;
    HashMap<Integer, Task> taskMap;
    HashMap<Integer, Epic> epicMap;

    public void printAll() {
        List<Integer> list = new ArrayList<>();
        if (taskMap != null) {
            list.addAll(taskMap.keySet());
        }
        if (epicMap != null) {
            list.addAll(epicMap.keySet());
        }
        if (!list.isEmpty()) {
            Collections.sort(list);
            for (Integer i : list) {
                if (epicMap.containsKey(i)) {
                    epicMap.get(i).print();
                } else {
                    taskMap.get(i).print();
                }
            }
        } else {
            System.out.println("Список задач пуст!");
        }
    }

    public void printById(int id) {
        if (checkIdInTask(id)) {
            getTask(id).print();
        } else if (checkIdInEpic(id)) {
            getEpic(id).print();
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    public void printSubtaskByIdEpic(int idEpic) {
        if (epicMap != null) {
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

    public void clearAll() {
        clearTask();
        clearEpic();
    }

    public void deleteById(int id) {
        if (checkIdInTask(id)) {
            taskMap.remove(id);
        } else if (checkIdInEpic(id)) {
            epicMap.remove(id);
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    public Task getTask(int id) {
        if (checkIdInTask(id)) {
            return taskMap.get(id);
        }
        System.out.println("Такой задачи нет!");
        return null;
    }

    public Epic getEpic(int id) {
        if (checkIdInEpic(id)) {
            return epicMap.get(id);
        }
        System.out.println("Такого Эпика нет!");
        return null;
    }

    public void addTask(Task newTask) {
        if (taskMap == null) {
            taskMap = new HashMap<>();
        }
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
        } else {
            taskAndEpicCount = newTask.getID();
            taskMap.put(taskAndEpicCount, newTask);
        }
    }

    public void addEpic(Epic newEpic) {
        if (epicMap == null) {
            epicMap = new HashMap<>();
        }
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
            taskAndEpicCount = newEpic.getID();
            epicMap.put(taskAndEpicCount, newEpic);
        }
    }

    public void addSubtask(int idEpic, Subtask newSubtask) {
        if (epicMap.containsKey(idEpic)) {
            Epic currentEpic = epicMap.get(idEpic);
            currentEpic.addSubtask(newSubtask);
        } else {
            System.out.println("Данного Эпика нет в списке!");
        }

    }

    public void updateTask(int id, int idProgress) {
        Task task = getTask(id);
        if (idProgress == 1) {
            if (task.progress.equals(Progress.IN_PROGRESS)) {
                System.out.println("Задача уже находится в данном статусе");
                return;
            }
            if (task.progress.equals(Progress.DONE)) {
                System.out.println("Задача уже выполнена");
                return;
            }
            task.setProgress(Progress.IN_PROGRESS);
        } else {
            if (task.progress.equals(Progress.DONE)) {
                System.out.println("Задача уже выполнена");
                return;
            }
            task.setProgress(Progress.DONE);
        }
    }

    public void updateEpic(int idEpic, int idSubtask, int idProgress) {
        Epic epic = getEpic(idEpic);
        Subtask subtask = epic.getSubtaskHashMap().get(idSubtask);
        if (idProgress == 1) {
            if (subtask.progress.equals(Progress.IN_PROGRESS)) {
                System.out.println("Подзадача уже находится в данном статусе");
                return;
            }
            if (subtask.progress.equals(Progress.DONE)) {
                System.out.println("Подзадача уже выполнена");
                return;
            }
            if (epic.getCountDoneSubtask() + epic.getCountInProgressSubtask() == 0) {
                epic.setProgress(Progress.IN_PROGRESS);
            }
            subtask.setProgress(Progress.IN_PROGRESS);
            epic.setCountInProgressSubtask(epic.getCountInProgressSubtask() + 1);
        } else {
            if (subtask.progress.equals(Progress.DONE)) {
                System.out.println("Подзадача уже выполнена");
                return;
            }
            epic.setCountDoneSubtask(epic.getCountDoneSubtask() + 1);
            if (epic.getCountInProgressSubtask() > 0) {
                epic.setCountInProgressSubtask(epic.getCountInProgressSubtask() - 1);
            }
            subtask.setProgress(Progress.DONE);
        }
        if (epic.getSubtaskHashMap().size() == epic.getCountDoneSubtask()) {
            epic.setProgress(Progress.DONE);
        }
    }


    public boolean checkIdInEpic(int id) {
        return epicMap.containsKey(id);
    }

    public boolean checkIdInTask(int id) {
        return taskMap.containsKey(id);
    }

    public boolean checkIdSubtask(int idEpic, int idSubtask) {
        return getEpic(idEpic).getSubtaskHashMap().containsKey(idSubtask);
    }
}
