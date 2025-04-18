package fileBackedTaskManager;

import managers.InMemoryTaskManager;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        int id = super.addSubtask(subtask);
        if (id == -1) {
            System.out.println("Эпика для данной задачи нет!");
            return -1;
        }
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public void updateEpicStatus(int id) {
        super.updateEpicStatus(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        super.deleteById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int addTask(Task task) {
        int id = super.addTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public int addEpic(Epic epic) {
        int id = super.addEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }


    public void save() {
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8); BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (int id = 1; id <= allTaskCount; id++) {
                if (checkIdInTask(id)) {
                    writer.write(toString(taskMap.get(id)));
                } else if (checkIdInEpic(id)) {
                    writer.write(toString(epicMap.get(id)));
                } else {
                    for (Task task : getSubtasks()) {
                        if (task.getId() == id) {
                            writer.write(toString(task));
                            break;
                        }
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
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
                    new String(String.format("%d,%s,%s,%s,%s", task.getId(), typeTask, task.getName(), task.getProgress().toString(),
                            task.getDescription()).getBytes(), StandardCharsets.UTF_8);
            case SUBTASK ->
                    new String(String.format("%d,%s,%s,%s,%s,%s", task.getId(), typeTask, task.getName(), task.getProgress().toString(),
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

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(fileReader)) {
            reader.readLine();
            while (reader.ready()) {
                Task task = fileBackedTaskManager.fromString(reader.readLine());
                if (task instanceof Epic) {
                    fileBackedTaskManager.epicMap.put(task.getId(), (Epic) task);
                    //fileBackedTaskManager.addEpic((Epic) task);
                } else if (task instanceof Subtask) {
                    HashMap<Integer, Subtask> subtaskHashMap = fileBackedTaskManager.epicMap.get(((Subtask) task).getIdEpic()).getSubtaskHashMap();
                    subtaskHashMap.put(task.getId(), (Subtask) task);
                    //fileBackedTaskManager.addSubtask((Subtask) task);
                } else {
                    fileBackedTaskManager.taskMap.put(task.getId(), task);
                    //fileBackedTaskManager.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return fileBackedTaskManager;
    }

    public static class ManagerSaveException extends RuntimeException {

    }
}
