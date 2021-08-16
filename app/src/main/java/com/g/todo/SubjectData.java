package com.g.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SubjectData extends AppCompatActivity {
TextView subname;
TabLayout tabs;
ViewPager viewPager;
FloatingActionButton add_data;
ImageView refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_data);
        Objects.requireNonNull(getSupportActionBar()).hide();

        String subjectName = getIntent().getStringExtra("SUBJECT_NAME");
        subname = (TextView)findViewById(R.id.sub_name_data);
        add_data = (FloatingActionButton)findViewById(R.id.add_data);
       subname.setText(subjectName);

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(SubjectData.this, AddData.class);
                startActivity(i);

            }
        });


        viewPager = findViewById(R.id.viewPager_data);
        tabs = (TabLayout)findViewById(R.id.tabs_sub_data);
        tabs.addTab(tabs.newTab().setText("Topics"));
        tabs.addTab(tabs.newTab().setText("Assignments"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);


        final TabsAdapter adapter = new TabsAdapter(this,getSupportFragmentManager(), tabs.getTabCount(), subjectName);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

}