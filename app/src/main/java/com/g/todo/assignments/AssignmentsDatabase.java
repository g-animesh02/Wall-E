package com.g.todo.assignments;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MyAssignments")
public class AssignmentsDatabase {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "subject")
    private String sub;

    @ColumnInfo(name = "assignment")
    private String assignment;

    @ColumnInfo(name = "assignment_desc")
    private String assignment_desc;

    @ColumnInfo(name = "deadline")
    private String deadline;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSub() { return sub; }
    public void setSub(String sub) { this.sub = sub; }

    public String getAssignment() { return assignment; }
    public void setAssignment(String topic) { this.assignment = topic; }

    public String getAssignment_desc() { return assignment_desc; }
    public void setAssignment_desc(String topic_desc) { this.assignment_desc = topic_desc; }

    public String getDeadline(){return deadline;}

    public void setDeadline(String deadline) { this.deadline = deadline; }
}


