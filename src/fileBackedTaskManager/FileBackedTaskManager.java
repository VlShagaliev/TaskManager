package fileBackedTaskManager;

import managers.InMemoryTaskManager;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public int addSubtask(Subtask subtask) throws ManagerSaveException {
        int id = super.addSubtask(subtask);
        if (id == -1) {
            System.out.println("Эпика для данной задачи нет!");
            return -1;
        }
        save();
        return id;
    }

    @Override
    public void updateEpicStatus(int id) throws ManagerSaveException {
        super.updateEpicStatus(id);
        save();
    }

    @Override
    public void updateTask(Task task) throws ManagerSaveException {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws ManagerSaveException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws ManagerSaveException {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskById(int id) throws ManagerSaveException {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) throws ManagerSaveException {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) throws ManagerSaveException {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteById(int id) throws ManagerSaveException {
        super.deleteById(id);
        save();
    }

    @Override
    public int addTask(Task task) throws ManagerSaveException {
        int id = super.addTask(task);
        save();
        return id;
    }

    @Override
    public int addEpic(Epic epic) throws ManagerSaveException {
        int id = super.addEpic(epic);
        save();
        return id;
    }



    public void save() throws ManagerSaveException {
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
                    fileBackedTaskManager.addEpic((Epic) task);
                } else if (task instanceof Subtask) {
                    fileBackedTaskManager.addSubtask((Subtask) task);
                } else {
                    fileBackedTaskManager.addTask(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        } catch (ManagerSaveException e) {
            System.out.println("Непредвиденная ошибка");
        }
        return fileBackedTaskManager;
    }

    public class ManagerSaveException extends Exception {
    }
}
