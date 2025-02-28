import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<? super Task> historyList = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (historyList.size()==10) {
            historyList.removeFirst();
        }
        Task taskCopy = new Task(task.name, task.description, task.progress);
        taskCopy.setId(task.getId());
        historyList.add(taskCopy);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>((List<Task>) historyList);
    }
}
