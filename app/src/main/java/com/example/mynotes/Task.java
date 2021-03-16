package com.example.mynotes;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID taskId;
    private String title;
    private String description;
    private Date mDate;
    private boolean completed;

    public Task() {
        taskId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
