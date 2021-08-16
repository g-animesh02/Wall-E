package com.g.todo.DailyRoutine;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

@Dao
public interface DailyWorkDatabaseDao {
    @Query("SELECT * FROM MyTasks ORDER BY done ASC")
    List<DailyWorkDatabase> getAll();

    @Query("DELETE FROM MyTasks WHERE repeat=0")
    void repeatedTask();

    @Query("UPDATE  MyTasks SET done = :done1 WHERE task LIKE :topic AND task_desc LIKE:topic_desc")
    void updateTask(String topic, String topic_desc, int done1);

    @Query("UPDATE  MyTasks SET done = :done1  ")
    void resetTask(int done1);

    @Query("UPDATE  MyTasks SET date = :date1  ")
    void resetDate(String date1);

    @Query("SELECT * FROM MyTasks WHERE date LIKE :date")
    List<DailyWorkDatabase> getDate(String date);

    @Insert
    void insert(DailyWorkDatabase dailyWorkDatabase);

    @Delete
    void delete(DailyWorkDatabase dailyWorkDatabase);

    @Update
    void update(DailyWorkDatabase dailyWorkDatabase);
}
