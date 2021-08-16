package com.g.todo.topics;

public class TopicsData {

    String topic, topic_desc;
    int done;

    TopicsData(String topic, String topic_desc, int done){
        this.done = done;
        this.topic = topic;
        this.topic_desc = topic_desc;
    }
    public String getTopic(){
        return topic;
    }
    public void setTopic(String topic){
        this.topic = topic;
    }

    public String getTopic_desc(){
        return topic_desc;
    }
    public void setTopic_desc(String topic_desc){
        this.topic_desc = topic_desc;
    }

    public int getDone(){
        return done;
    }
    public void setDone(int done){
        this.done = done;
    }
}
