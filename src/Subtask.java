public class Subtask extends Task {
    public Subtask(String NAME, String DESCRIPTION, int ID) {
        super(NAME, DESCRIPTION, ID);
    }
    @Override
    public void print() {
        System.out.println(ID + ". Подзадача: " + NAME + "|\t Описание: " + DESCRIPTION + "|\t Статус: " + progress);
    }


}
