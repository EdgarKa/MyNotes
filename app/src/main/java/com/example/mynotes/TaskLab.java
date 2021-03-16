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
        for (int i = 0; i < 100; i++) {
            Task task = new Task();
            task.setTitle("Task #" + i);
            task.setDescription("Description for Task #" + i);
            task.setCompleted(i % 2 == 0);
            mTasks.add(task);
        }
    }

    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
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
