package com.example.mynotes;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskLab {

    private static TaskLab sTaskLab;
    private List<Task> mTasks;

    private TaskLab(Context context) {
        mTasks = new ArrayList<>();

    }

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }

    public void addTask(Task t) {
        mTasks.add(t);
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(UUID id) {
        for (Task task : mTasks)
            if (task.getTaskId().equals(id))
                return task;
            return null;
    }
}
