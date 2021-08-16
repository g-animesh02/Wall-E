package com.g.todo.topics;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "MyTopics")
public class TopicsDatabase {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "subject")
    private String sub;

    @ColumnInfo(name = "topic")
    private String topic;

    @ColumnInfo(name = "topic_desc")
    private String topic_desc;

    @ColumnInfo(name = "done")
    private int finished;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSub() { return sub; }
    public void setSub(String sub) { this.sub = sub; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getTopic_desc() { return topic_desc; }
    public void setTopic_desc(String topic_desc) { this.topic_desc = topic_desc; }

    public int getFinished() { return finished; }
    public void setFinished(int finished) { this.finished = finished; }


}
