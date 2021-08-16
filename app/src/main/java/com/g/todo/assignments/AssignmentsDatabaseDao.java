package com.g.todo.assignments;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssignmentsDatabaseDao {

    @Query("SELECT * FROM MyAssignments WHERE subject LIKE :subject")
    List<AssignmentsDatabase> findWithSubject(String subject);

    @Query("DELETE FROM MyAssignments WHERE assignment LIKE :assignment AND assignment_desc LIKE:topic_desc")
    void deleteTopic(String assignment, String topic_desc);

    @Insert
    void insert(AssignmentsDatabase AssignmentsDatabase);

    @Delete
    void delete(AssignmentsDatabase AssignmentsDatabase);

    @Update
    void update(AssignmentsDatabase AssignmentsDatabase);



}

