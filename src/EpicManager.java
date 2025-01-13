import java.util.HashMap;

public final class EpicManager {
    static int epicCount = 0;
    HashMap<Integer, Epic> epicMap;

    public void printEpic() {
        for (Integer key : epicMap.keySet()) {
            epicMap.get(key).print();
        }
    }

    public void deleteEpic() {
        epicMap.clear();
    }

    public void getEpicById(int id) {
        if (epicMap.isEmpty() & epicMap.containsKey(id)) {
            epicMap.get(id).print();
        } else {
            System.out.println("Список Эпиков пустой");
        }
    }

    public void addEpic(Epic epic) {
        if (epicMap.isEmpty()) {
            epicMap = new HashMap();
        }
        epicMap.put(++epicCount, epic);
    }

    public void updateEpic(int id_epic, int id_subtask, Progress progress) {
        if (!epicMap.isEmpty() && epicMap.containsKey(id_epic)) {
            epicMap.get(id_epic).getSubtask(id_subtask).setProgress(progress);
        } else {
            System.out.println("Такого Эпика в списке нет");
        }
    }

    public void deleteEpicById(int id) {
        epicMap.remove(id);
    }

    public void getSubtask(int id_epic, int id_subtask) {
        epicMap.get(id_epic).getSubtask(id_subtask).print();
    }
}
