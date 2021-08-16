package com.g.todo.topics;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.g.todo.R;

import java.util.ArrayList;
import java.util.List;

public class TopicsFragment extends Fragment {

    public RecyclerView recyclerView;
    private List<TopicsData> topics = new ArrayList<>();
    private TopicsAdapter mAdapter;
    private String subject;
    public TopicsFragment(String subject) {
        this.subject = subject;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topics, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.topics_recyclerView);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareTopics(view);


        return view;
    }

    private void prepareTopics(View view) {


        class GetTasks extends AsyncTask<Void, Void, List<TopicsDatabase>> {
            List<TopicsDatabase> t;

            @Override
            protected List<TopicsDatabase> doInBackground(Void... voids) {
                List<TopicsDatabase> tasks= TopicsModelDatabase
                        .getInstance(view.getContext())
                        .topicsDatabaseDao()
                        .findMyTopicsWithSubject(subject);
                t = tasks;
                return tasks;
            }

            @Override
            protected void onPostExecute(List<TopicsDatabase> topicsDatabases) {
                super.onPostExecute(topicsDatabases);
                TopicsAdapter mAdapter = new TopicsAdapter(t, view.getContext());
                recyclerView.setAdapter(mAdapter);
                }
            }


        GetTasks gt = new GetTasks();
        gt.execute();


    }
}
