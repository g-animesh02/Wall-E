package com.g.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.g.todo.DailyRoutine.DailyWork;
import com.g.todo.DailyRoutine.DailyWorkDatabase;
import com.g.todo.DailyRoutine.DailyWorkModelDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class firebase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);


        class SaveTask extends AsyncTask<Void, Void, Void> {
            List<DailyWorkDatabase> t;
            @Override
            protected Void doInBackground(Void... voids) {


                t = DailyWorkModelDatabase.getInstance(firebase.this).dailyWorkDatabaseDao().getAll();
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://todo-93da1-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference myRef = database.getReference("DailyRoutine");
                DatabaseReference ref = database.getReference("message");
                ref.setValue("Hi sample");

                myRef.setValue(t);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();







    }
}