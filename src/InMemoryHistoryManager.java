import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final DoublyLinkedList<Task> historyLinkedList = new DoublyLinkedList<>();
    private final HashMap<Integer, Node<Task>> mapHistory = new HashMap<>();

    @Override
    public void add(Task task) {
        Node<Task> taskNode = new Node<>(task);
        if (mapHistory.containsKey(taskNode.data.getId())) {
            Node<Task> removableTask = mapHistory.get(taskNode.data.getId());
            removeNode(removableTask);
        }
        historyLinkedList.linkLast(taskNode);
        mapHistory.put(task.getId(), taskNode);
    }

    @Override
    public void remove(int id) {
        if (mapHistory.get(id) != null) {
            Node<Task> taskNode = mapHistory.get(id);
            removeNode(taskNode);
        } else {
            System.out.println("Данной задачи в истории нет!");
        }
    }

    private void removeNode(Node<Task> taskNode) {
        Node<Task> head = historyLinkedList.head;
        Node<Task> tail = historyLinkedList.tail;
        if (taskNode != head && taskNode != tail) {
            taskNode.prev.next = taskNode.next;
            taskNode.next.prev = taskNode.prev;
        }
        if (taskNode == head) {
            if (head.next != null) {
                if (head.next == tail) {
                    historyLinkedList.head = historyLinkedList.tail;
                } else {
                    historyLinkedList.head = head.next;
                }
                historyLinkedList.head.prev = null;
            } else {
                historyLinkedList.head = null;
            }
        }
        if (taskNode == tail) {
            historyLinkedList.tail = tail.prev;
            historyLinkedList.tail.next = null;
        }
        taskNode.next = null;
        taskNode.prev = null;
    }

    @Override
    public List<Task> getHistory() {
        return historyLinkedList.getTasks();
    }
}
