package com.g.todo.assignments;

public class AssignmentsData {

    String assignment, ass_desc, deadline;
    AssignmentsData(String assignment, String ass_desc, String deadline) {
        this.deadline = deadline;
        this.ass_desc = ass_desc;
        this.assignment = assignment;
    }

    public String getAssignment(){
        return assignment;
    }
    public void setAssignment(String assignment){
        this.assignment = assignment;
    }

    public String getAss_desc(){return ass_desc; }
    public void setAss_desc(String ass_desc){
        this.ass_desc = ass_desc;
    }

    public String getDeadline(){
        return deadline;
    }
    public void setDeadline(String deadline){
        this.deadline = deadline;
    }

}
