import java.util.Objects;

public class Task {
    int id;
    final String name;
    final String description;
    Progress progress;

    public Task(String name, String description, Progress progress) {
        this.name = name;
        this.description = description;
        this.progress = progress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void print() {
        System.out.println(id + ". Задача: " + name + "|\t Описание: " + description + "|\t Статус: " + progress);
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
