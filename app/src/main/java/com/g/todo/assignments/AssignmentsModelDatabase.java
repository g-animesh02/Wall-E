package com.g.todo.assignments;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;



@Database(entities = {AssignmentsDatabase.class}, version = 1)
public abstract class AssignmentsModelDatabase extends RoomDatabase {

    public abstract AssignmentsDatabaseDao assignmentsDatabaseDao();
    private static AssignmentsModelDatabase instance;

    public static synchronized AssignmentsModelDatabase getInstance(Context context) {
        // below line is to check if
        // the instance is null or not.
        if (instance == null) {
            // if the instance is null we
            // are creating a new instance
            instance =
                    // for creating a instance for our database
                    // we are creating a database builder and passing
                    // our database class with our database name.
                    Room.databaseBuilder(context.getApplicationContext(),
                            AssignmentsModelDatabase.class, "MyAssignments")
                            // below line is use to add fall back to
                            // destructive migration to our database.
                            .fallbackToDestructiveMigration()
                            // below line is to add callback
                            // to our database.
                            .addCallback(roomCallback)
                            // below line is to
                            // build our database.
                            .build();
        }
        // after creating an instance
        // we are returning our instance
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
            new AssignmentsModelDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(AssignmentsModelDatabase instance) {
            AssignmentsDatabaseDao dao = instance.assignmentsDatabaseDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
