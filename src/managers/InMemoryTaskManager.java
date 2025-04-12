package managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;

public class InMemoryTaskManager implements TaskManager {
    static int allTaskCount = 0;
    protected static final Map<Integer, Task> taskMap = new HashMap<>();
    protected static final Map<Integer, Epic> epicMap = new HashMap<>();
    protected final static HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void printAllTask() {
        if (!taskMap.isEmpty()) {
            for (Integer key : taskMap.keySet()) {
                taskMap.get(key).print();
            }
        } else {
            System.out.println("Список задач пуст!");
        }
    }

    @Override
    public void printAllEpic() {
        if (!epicMap.isEmpty()) {
            for (Integer key : epicMap.keySet()) {
                epicMap.get(key).print();
            }
        } else {
            System.out.println("Список Эпиков пуст!");
        }
    }

    @Override
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

    @Override
    public void printById(int id) {
        if (checkIdInTask(id)) {
            Task task = getTask(id);
            task.print();
        } else if (checkIdInEpic(id)) {
            Epic epic = getEpic(id);
            epic.print();
        } else if (checkIdSubtask(id)) {
            Subtask subtask = getSubtask(id);
            subtask.print();
        } else {
            System.out.println("Такой задачи нет!");
        }
    }

    @Override
    public void printSubtaskByIdEpic(int idEpic) {
        if (!epicMap.isEmpty()) {
            getEpics().get(idEpic).print();
        } else {
            System.out.println("Такого Эпика нет!");
        }
    }

    @Override
    public void clearTask() {
        for (Task task : getTasks()) {
            int idTask = task.getId();
            for (Task taskHistory : historyManager.getHistory()) {
                if (taskHistory.getId() == idTask) {
                    historyManager.remove(idTask);
                }
            }
        }
        taskMap.clear();
    }

    @Override
    public void clearEpic() {
        for (Task epic : getEpics()) {
            int idEpic = epic.getId();
            for (Task taskHistory : historyManager.getHistory()) {
                if (taskHistory.getId() == idEpic) {
                    historyManager.remove(idEpic);
                }
            }
        }
        epicMap.clear();
    }

    @Override
    public void clearSubtask() {
        for (Task subtask : getSubtasks()) {
            int idSubtask = subtask.getId();
            for (Task taskHistory : historyManager.getHistory()) {
                if (taskHistory.getId() == idSubtask) {
                    historyManager.remove(idSubtask);
                }
            }
        }
        if (!epicMap.isEmpty()) {
            for (Integer key : epicMap.keySet()) {
                epicMap.get(key).getSubtaskHashMap().clear();
            }
        }

    }

    @Override
    public void deleteTaskById(int id) {
        historyManager.remove(id);
        taskMap.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        epicMap.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        for (Integer key : epicMap.keySet()) {
            Map<Integer, Subtask> subtaskHashMap = epicMap.get(key).getSubtaskHashMap();
            if (!subtaskHashMap.isEmpty()) {
                if (subtaskHashMap.containsKey(id)) {
                    int idEpic = subtaskHashMap.get(id).getIdEpic();
                    subtaskHashMap.remove(id);
                    updateEpicStatus(idEpic);
                    return;
                }
            }
        }
    }

    @Override
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

    @Override
    public Task getTask(int id) {
        Task task = taskMap.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epicMap.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        for (Integer key : epicMap.keySet()) {
            Map<Integer, Subtask> subtaskHashMap = epicMap.get(key).getSubtaskHashMap();
            if (subtaskHashMap.containsKey(id)) {
                Subtask subtask = subtaskHashMap.get(id);
                historyManager.add(subtask);
                return subtask;
            }
        }
        return null;
    }

    @Override
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

    @Override
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

    @Override
    public int addSubtask(Subtask newSubtask) {
        if (epicMap.containsKey(newSubtask.getIdEpic())) {
            Epic currentEpic = epicMap.get(newSubtask.getIdEpic());
            allTaskCount++;
            newSubtask.setId(allTaskCount);
            currentEpic.addSubtask(newSubtask);
            updateEpicStatus(newSubtask.getIdEpic());
            return newSubtask.getId();
        } else {
            return -1;
        }
    }

    @Override
    public void updateEpicStatus(int id) {
        HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(id).getSubtaskHashMap();
        Epic epic = epicMap.get(id);
        if (subtaskHashMap.isEmpty()) {
            epicMap.get(id).setProgress(Progress.NEW);
        }
        int countSubtaskDone = 0;
        for (Integer keySubtask : subtaskHashMap.keySet()) {
            if (subtaskHashMap.get(keySubtask).getProgress() == Progress.IN_PROGRESS) {
                epic.setProgress(Progress.IN_PROGRESS);
                return;
            }
            if (subtaskHashMap.get(keySubtask).getProgress() == Progress.DONE) {
                countSubtaskDone++;
                if (countSubtaskDone == subtaskHashMap.size()) {
                    epic.setProgress(Progress.DONE);
                    return;
                } else {
                    epic.setProgress(Progress.IN_PROGRESS);
                }
            } else if (subtaskHashMap.get(keySubtask).getProgress() == Progress.NEW) {
                if (countSubtaskDone == 0) {
                    epic.setProgress(Progress.NEW);
                } else if (countSubtaskDone > 0) {
                    epic.setProgress(Progress.IN_PROGRESS);
                }
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        taskMap.replace(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic oldEpic = epicMap.get(epic.getId());
        HashMap<Integer, Subtask> subtaskHashMap = epic.getSubtaskHashMap();
        subtaskHashMap.putAll(oldEpic.getSubtaskHashMap());
        epicMap.replace(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        HashMap<Integer, Subtask> subtaskHashMap = epicMap.get(subtask.getIdEpic()).getSubtaskHashMap();
        subtaskHashMap.replace(subtask.getId(), subtask);
        updateEpicStatus(subtask.getIdEpic());
    }

    @Override
    public boolean checkIdInEpic(int id) {
        return epicMap.containsKey(id);
    }

    @Override
    public boolean checkIdInTask(int id) {
        return taskMap.containsKey(id);
    }

    @Override
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

    @Override
    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        for (Integer key : taskMap.keySet()) {
            taskList.add(taskMap.get(key));
        }
        return taskList;
    }

    @Override
    public List<Task> getEpics() {
        List<Task> epicList = new ArrayList<>();
        for (Integer key : epicMap.keySet()) {
            epicList.add(epicMap.get(key));
        }
        return epicList;
    }

    @Override
    public List<Task> getSubtasks() {
        List<Task> subtasksList = new ArrayList<>();
        for (Integer key : epicMap.keySet()) {
            Epic epic = epicMap.get(key);
            HashMap<Integer, Subtask> subtaskHashMap = epic.getSubtaskHashMap();
            for (Integer keySub : subtaskHashMap.keySet()) {
                subtasksList.add(subtaskHashMap.get(keySub));
            }
        }
        return subtasksList;
    }

    public List<Task> getHistory(){
        return historyManager.getHistory();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
