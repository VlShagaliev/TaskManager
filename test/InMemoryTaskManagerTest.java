import managers.InMemoryTaskManager;
import managers.Managers;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest {

    @Test
    void clearTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("1", "1", Progress.NEW);
        task.setId(1);
        taskManager.addTask(task);
        taskManager.clearTask();
        Assertions.assertEquals(taskManager.getTasks(), new ArrayList<>());
    }

    @Test
    void clearEpic() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("1", "1");
        epic.setId(1);
        taskManager.addEpic(epic);
        taskManager.clearEpic();
        Assertions.assertEquals(taskManager.getEpics(), new ArrayList<>());
    }

    @Test
    void clearSubtask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("1", "1");
        epic.setId(1);
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("2", "2", 1, Progress.NEW);
        subtask.setId(2);
        taskManager.addSubtask(subtask);
        taskManager.clearSubtask();
        Assertions.assertEquals(taskManager.getSubtasks(), new ArrayList<>());
    }

    @Test
    void updateEpicStatusInProgress() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("1", "1");
        epic.setId(1);
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("2", "2", 1, Progress.NEW);
        subtask.setId(2);
        taskManager.addSubtask(subtask);
        Subtask subtask1 = (Subtask) taskManager.getSubtasks().get(0);
        subtask1.setProgress(Progress.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        Assertions.assertEquals(taskManager.getEpics().get(0).getProgress(), Progress.IN_PROGRESS);
    }

    @Test
    void updateEpicStatusDone() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("1", "1");
        epic.setId(1);
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("2", "2", 1, Progress.NEW);
        subtask.setId(2);
        taskManager.addSubtask(subtask);
        Subtask subtask1 = (Subtask) taskManager.getSubtasks().get(0);
        subtask1.setProgress(Progress.DONE);
        taskManager.updateSubtask(subtask1);
        Assertions.assertEquals(taskManager.getEpics().get(0).getProgress(), Progress.DONE);
    }

    @Test
    void updateEpicStatusAfterClearSubtask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("1", "1");
        epic.setId(1);
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("2", "2", 1, Progress.NEW);
        subtask.setId(2);
        taskManager.addSubtask(subtask);
        Subtask subtask1 = (Subtask) taskManager.getSubtasks().get(0);
        subtask1.setProgress(Progress.DONE);
        taskManager.clearSubtask();
        Assertions.assertEquals(taskManager.getEpics().get(0).getProgress(), Progress.NEW);
    }

    @Test
    void updateTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("1", "1", Progress.NEW);
        task.setId(1);
        taskManager.addTask(task);
        Task task1 = taskManager.getTasks().get(0);
        task1.setProgress(Progress.DONE);
        Assertions.assertEquals(taskManager.getTasks().get(0).getProgress(), Progress.DONE);
    }

    @Test
    void updateSubtask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("1", "1");
        epic.setId(1);
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask("2", "2", 1, Progress.NEW);
        subtask.setId(2);
        taskManager.addSubtask(subtask);
        Subtask subtask1 = (Subtask) taskManager.getSubtasks().get(0);
        subtask1.setProgress(Progress.DONE);
        taskManager.updateSubtask(subtask1);
        Assertions.assertEquals(taskManager.getSubtasks().get(0).getProgress(), Progress.DONE);
    }

    @Test
    void testInitializationTaskManager() {
        Managers managers = new Managers();
        Assertions.assertNotNull(managers.getDefault());
    }

    @Test
    void testInitializationHistoryManager() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    void testFindTaskById() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("1", "1", Progress.NEW);
        task.setId(1);
        taskManager.addTask(task);
        Assertions.assertNotNull(taskManager.getTasks().get(0));
    }

    @Test
    void testHistorySavePreviousVersionTask() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("1", "1", Progress.NEW);
        task.setId(1);
        taskManager.addTask(task);
        taskManager.printById(1);
        task.setProgress(Progress.DONE);
        Task taskBefore = taskManager.getHistory().get(0);
        boolean checkDifferent = (taskBefore.getName().equals(task.getName())) || (taskBefore.getDescription()
                .equals(task.getDescription())) || (taskBefore.getProgress().equals(task.getProgress()));
        Assertions.assertTrue(checkDifferent);
    }

    @Test
    void testHistoryReplaceTaskChecked(){
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("1", "1", Progress.NEW);
        task.setId(1);
        taskManager.addTask(task);
        taskManager.printById(1);
        task = new Task("2", "2", Progress.NEW);
        task.setId(2);
        taskManager.addTask(task);
        taskManager.printById(2);
        List<Task> previousHistory = new ArrayList<>(taskManager.getHistory());
        taskManager.printById(1);
        List<Task> newHistory = new ArrayList<>(taskManager.getHistory());
        Assertions.assertNotEquals(previousHistory,newHistory);
    }

    @Test
    void testHistoryRemovedTaskById(){
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("1", "1", Progress.NEW);
        task.setId(1);
        taskManager.addTask(task);
        taskManager.printById(1);
        task = new Task("2", "2", Progress.NEW);
        task.setId(2);
        taskManager.addTask(task);
        taskManager.printById(2);
        List<Task> previousHistory = new ArrayList<>(taskManager.getHistory());
        taskManager.getHistoryManager().remove(1);
        List<Task> newHistory = new ArrayList<>(taskManager.getHistory());
        Assertions.assertNotEquals(previousHistory,newHistory);
    }
}