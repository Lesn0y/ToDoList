package com.lesnoy.todo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Task {

    private int id;
    private String task;
    private Timestamp deadline;
    private boolean isDone;

    public Task() {
    }

    public Task(String task) {
        this.task = task;
    }

    public Task(String task, Timestamp deadline) {
        this.task = task;
        this.deadline = deadline;
    }

    public Task(int id, String task, Timestamp deadline, boolean isDone) {
        this.id = id;
        this.task = task;
        this.isDone = isDone;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public String getFormatDeadline() {
        if (this.deadline != null) {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(this.deadline);
        }
        return "";
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", isDone=" + isDone +
                ", deadline=" + deadline +
                '}';
    }
}
