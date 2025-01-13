import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subtaskHashMap;

    public Epic(String NAME, String DESCRIPTION, int ID, Progress progress) {
        super(NAME, DESCRIPTION, ID, progress);
    }

    public Subtask getSubtask(int id) {
        return subtaskHashMap.get(id);
    }

    @Override
    public void print(){
        for (Integer key : subtaskHashMap.keySet()){
            subtaskHashMap.get(key).print();
        }
    }
}
