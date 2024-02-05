package com.lesnoy.todo;

import java.sql.Timestamp;

public class Task {

    private int id;
    private String task;
    private boolean isDone;
    private Timestamp deadline;

    public Task() {
    }

    public Task(String task, Timestamp deadline) {
        this.task = task;
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
