public class Subtask extends Task {

    private final int idEpic;

    public Subtask(String name, String description, int idEpic, Progress progress) {
        super(name, description,progress);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public void print() {
        System.out.println(id + ". Номер Эпика: " + idEpic + ". Подзадача: " + name + "|\t Описание: " + description + "|\t Статус: " + progress);
    }
}
