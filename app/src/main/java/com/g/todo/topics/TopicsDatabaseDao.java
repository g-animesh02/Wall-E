package com.g.todo.topics;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TopicsDatabaseDao {

    @Query("SELECT * FROM MyTopics WHERE subject LIKE :subject")
    List<TopicsDatabase> findMyTopicsWithSubject(String subject);

    @Query("DELETE FROM MyTopics WHERE topic LIKE :topic AND topic_desc LIKE:topic_desc")
    void deleteTopic(String topic, String topic_desc);

    @Query("UPDATE  MyTopics SET done = :done1 WHERE topic LIKE :topic AND topic_desc LIKE:topic_desc")
    void updateTopic(String topic, String topic_desc, int done1);

    @Insert
    void insert(TopicsDatabase TopicsDatabase);

    @Delete
    void delete(TopicsDatabase TopicsDatabase);

    @Update
    void update(TopicsDatabase TopicsDatabase);

}