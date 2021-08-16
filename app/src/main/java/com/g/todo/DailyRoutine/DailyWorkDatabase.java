package com.g.todo.DailyRoutine;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyTasks")
public class DailyWorkDatabase {
    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "task")
    private String task;

    @ColumnInfo(name = "task_desc")
    private String task_desc;

    @ColumnInfo(name = "task_time")
    private String task_time;

    @ColumnInfo(name = "done")
    private int finished;

    @ColumnInfo(name = "repeat")
    private String repeat;

    public int getFinished() {
        return finished;
    }

    public int getId() {
        return id;
    }


    public String getTask() {
        return task;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setTask(String task) {
        this.task = task;
    }

    public void setTask_desc(String task_desc) {
        this.task_desc = task_desc;
    }

    public String getTask_time() {
        return task_time;
    }

    public void setTask_time(String task_time) {
        this.task_time = task_time;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
