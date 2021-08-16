package com.g.todo;

import androidx.recyclerview.widget.RecyclerView;

public class Subject_Names {

    private int Id;
    private String sub;
    private String sub_desc;
    private int backgroundRows;;

    public Subject_Names(){}

    public Subject_Names(String sub, int id, String subdesc, int backgroundRows){
        this.sub = sub;
        this.Id = id;
        this.sub_desc = subdesc;
        this.backgroundRows = backgroundRows;
    }

    public String getSub(){
        return sub;
    }
    public void setType(String sub){
        this.sub = sub;
    }

    public int getPic(){
        return Id;
    }
    public void setPic(int id){
        this.Id = id;
    }

    public String getSub_desc(){
        return sub_desc;
    }
    public void setSub_desc(String sub_desc){
        this.sub_desc = sub_desc;
    }

    public int getBackgroundRows() { return backgroundRows; }

    public void setBackgroundRows(int backgroundRows) { this.backgroundRows = backgroundRows; }
}
