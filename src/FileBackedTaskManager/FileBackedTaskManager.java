package FileBackedTaskManager;

import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;
    HashMap<Integer, Task> hashMap = new HashMap<>();

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        int id = super.addSubtask(subtask);
        if(id != -1){
            hashMap.put(id, subtask);
            save();
            return id;
        }
        System.out.println("Эпика для данной подзадачи нет!");
        return -1;
    }

    @Override
    public int addTask(Task task) {
        int id = super.addTask(task);
        hashMap.put(id, task);
        save();
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        int id = super.addEpic(epic);
        hashMap.put(id, epic);
        save();
        return id;
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8); BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (Integer id : hashMap.keySet()) {
                writer.write(toString(hashMap.get(id)));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
        }
    }

    public String toString(Task task) {
        TypeTask typeTask;
        if (checkIdInTask(task.getId())) {
            typeTask = TypeTask.TASK;
        } else if (checkIdInEpic(task.getId())) {
            typeTask = TypeTask.EPIC;
        } else if (checkIdSubtask(task.getId())) {
            typeTask = TypeTask.SUBTASK;
        } else {
            System.out.println("Данной задачи нет!");
            return null;
        }
        return switch (typeTask) {
            case EPIC, TASK ->
                    new String(String.format("%d,%s,%s,%s,%s", task.getId(), typeTask.toString(), task.getName(), task.getProgress().toString(),
                            task.getDescription()).getBytes(), StandardCharsets.UTF_8);
            case SUBTASK ->
                    new String(String.format("%d,%s,%s,%s,%s,%s", task.getId(), typeTask.toString(), task.getName(), task.getProgress().toString(),
                            task.getDescription(), ((Subtask) task).getIdEpic()).getBytes(), StandardCharsets.UTF_8);
        };
    }

    public Task fromString(String value) {
        String[] taskString = value.split(",");
        Progress progress = switch (taskString[3]) {
            case "IN_PROGRESS" -> Progress.IN_PROGRESS;
            case "DONE" -> Progress.DONE;
            default -> Progress.NEW;
        };
        Task task = null;
        switch (taskString[1]) {
            case "TASK": {
                task = new Task(taskString[2], taskString[4], progress);
                task.setId(Integer.parseInt(taskString[0]));
                break;
            }
            case "EPIC": {
                task = new Epic(taskString[2], taskString[4]);
                task.setId(Integer.parseInt(taskString[0]));
                task.setProgress(progress);
                break;
            }
            case "SUBTASK": {
                task = new Subtask(taskString[2], taskString[4], Integer.parseInt(taskString[5]), progress);
                task.setId(Integer.parseInt(taskString[0]));
            }
        }
        return task;
    }
}
