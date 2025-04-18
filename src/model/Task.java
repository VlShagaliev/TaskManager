package model;

import fileBackedTaskManager.FileBackedTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected int id;
    protected final String name;
    protected final String description;
    protected Progress progress;
    protected Duration duration;

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    protected LocalDateTime startTime;

    public Task(String name, String description, Progress progress) {
        this.name = name;
        this.description = description;
        this.progress = progress;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        if (startTime != null) {
            this.duration = duration;
        } else {
            System.out.println("Отсутствует время начала задачи!");
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void print() {
        System.out.println(id + ". Задача: " + name + "|\t Описание: " + description + "|\t Статус: " +
                progress + "|\t Время начала: " + startTime.format(FileBackedTaskManager.dateTimeFormatter));
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Progress getProgress() {
        return progress;
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

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }
}
