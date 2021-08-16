package com.g.todo;

public class TasksData {
    String title,title_desc,time;
    int done;

    TasksData(String title, String title_desc, String time, int done){
        this.done = done;
        this.title = title;
        this.title_desc = title_desc;
        this.time = time;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_desc() {
        return title_desc;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitle_desc(String title_desc) {
        this.title_desc = title_desc;
    }
}
