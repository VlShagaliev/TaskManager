import java.util.HashMap;

public class Epic extends Task {
    private int countSubtask = 0;
    private int countInProgressSubtask = 0;
    private int countDoneSubtask = 0;
    private HashMap<Integer, Subtask> subtaskHashMap;


    public Epic(String NAME, String DESCRIPTION, int ID) {
        super(NAME, DESCRIPTION, ID);
    }

    public int getCountInProgressSubtask() {
        return countInProgressSubtask;
    }

    public void setCountInProgressSubtask(int countInProgressSubtask) {
        this.countInProgressSubtask = countInProgressSubtask;
    }

    public int getCountDoneSubtask() {
        return countDoneSubtask;
    }
    public void setCountDoneSubtask(int countDoneSubtask) {
        this.countDoneSubtask = countDoneSubtask;
    }

    public HashMap<Integer, Subtask> getSubtaskHashMap() {
        return subtaskHashMap;
    }

    public int getCountSubtask() {
        return countSubtask;
    }

    public void addSubtask(Subtask newSubtask) {
        if (subtaskHashMap == null) {
            subtaskHashMap = new HashMap<>();
        }
        for (Integer key: subtaskHashMap.keySet())
        {
            if (subtaskHashMap.get(key).equals(newSubtask)){
                System.out.println("Такая подзадача уже существует");
                return;
            }
        }
        this.countSubtask = newSubtask.getID();
        subtaskHashMap.put(this.countSubtask, newSubtask);
    }

    public void print() {
        System.out.println(ID + ". Эпик: " + NAME + "|\t Описание: " + DESCRIPTION + "|\t Статус: " + progress);
        System.out.print("\tСписок подзадач: ");
        printSubtask();
    }

    public void printSubtask() {
        if (subtaskHashMap != null) {

            System.out.println("\n\t--------------------");
            for (Integer key : subtaskHashMap.keySet()) {
                System.out.print("\t");
                subtaskHashMap.get(key).print();
            }
            System.out.println("\t--------------------");
        } else {
            System.out.println("Список пуст!");
        }
    }
}
