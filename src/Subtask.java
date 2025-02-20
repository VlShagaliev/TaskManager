public class Subtask extends Task {

    public int getIdEpic() {
        return idEpic;
    }

    private final int idEpic;

    public Subtask(String name, String description, int idEpic, Progress progress) {
        super(name, description,progress);
        this.idEpic = idEpic;
    }

    @Override
    public void print() {
        System.out.println(id + ". Подзадача: " + name + "|\t Описание: " + description + "|\t Статус: " + progress);
    }
}
