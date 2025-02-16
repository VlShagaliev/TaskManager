import java.util.Objects;

public class Task {
    protected final int ID;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected Progress progress;

    public Task(String NAME, String DESCRIPTION, int ID) {
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.ID = ID;
        this.progress = Progress.NEW;
    }

    public int getID() {
        return ID;
    }

    public void print() {
        System.out.println(ID + ". Задача: " + NAME + "|\t Описание: " + DESCRIPTION + "|\t Статус: " + progress);
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(NAME, task.NAME) && Objects.equals(DESCRIPTION, task.DESCRIPTION);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NAME, DESCRIPTION);
    }
}
