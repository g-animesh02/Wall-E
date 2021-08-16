package com.g.todo.topics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.g.todo.R;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.MyViewHolder> {
    private final List<TopicsDatabase> topics;
    private Context context;

    public TopicsAdapter(List<TopicsDatabase> topics, Context context) {
        this.topics = topics;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topics_rows, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TopicsDatabase topic = topics.get(position);
        holder.topic_name.setText(topic.getTopic());
        holder.topic_desc.setText(topic.getTopic_desc());
        int d = topic.getFinished();

        if (d == 1) {
            holder.smiley.setVisibility(View.VISIBLE);
            holder.done.setChecked(true);
        }
        else {
            holder.smiley.setVisibility(View.INVISIBLE);
            holder.done.setChecked(false);
        }

        holder.done.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) {
                    new AlertDialog.Builder(buttonView.getContext())
                            .setTitle("Confirmation")
                            .setMessage("Dekh bhai Jhoot nhi bolne ka...")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes...Ofc.", new DialogInterface.OnClickListener()  {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    class GetTasks extends AsyncTask<Void, Void, List<TopicsDatabase>> {
                                        List<TopicsDatabase> t;

                                        @Override
                                        protected List<TopicsDatabase> doInBackground(Void... voids) {
                                            TopicsModelDatabase.getInstance(context).topicsDatabaseDao().updateTopic(topic.getTopic(), topic.getTopic_desc(), 1);

                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(List<TopicsDatabase> topicsDatabases) {
                                            super.onPostExecute(topicsDatabases);
                                            holder.smiley.setVisibility(View.VISIBLE);
                                            holder.done.setChecked(true);
                                        }
                                    }
                                    GetTasks gt = new GetTasks();
                                    gt.execute();

                                }
                            })
                            .setNegativeButton("just kidding", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.done.setChecked(false);
                                    class GetTasks extends AsyncTask<Void, Void, List<TopicsDatabase>> {
                                        List<TopicsDatabase> t;

                                        @Override
                                        protected List<TopicsDatabase> doInBackground(Void... voids) {
                                            TopicsModelDatabase.getInstance(context).topicsDatabaseDao().updateTopic(topic.getTopic(), topic.getTopic_desc(), 0);

                                            return null;
                                        }

                                        @Override
                                        protected void onPostExecute(List<TopicsDatabase> topicsDatabases) {
                                            super.onPostExecute(topicsDatabases);
                                            holder.done.setChecked(false);
                                        }
                                    }
                                    GetTasks gt = new GetTasks();
                                    gt.execute();

                                }
                            }).show();


                }
                else{
                    class GetTasks extends AsyncTask<Void, Void, List<TopicsDatabase>> {
                        List<TopicsDatabase> t;

                        @Override
                        protected List<TopicsDatabase> doInBackground(Void... voids) {
                            TopicsModelDatabase.getInstance(context).topicsDatabaseDao().updateTopic(topic.getTopic(), topic.getTopic_desc(), 0);

                            return null;
                        }

                        @Override
                        protected void onPostExecute(List<TopicsDatabase> topicsDatabases) {
                            super.onPostExecute(topicsDatabases);
                            holder.smiley.setVisibility(View.INVISIBLE);
                            holder.done.setChecked(false);
                        }
                    }
                    GetTasks gt = new GetTasks();
                    gt.execute();
                }
            }
        });

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you wanna delete this...")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yess", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                int pos = topics.indexOf(topic);


                                class GetTasks extends AsyncTask<Void, Void, List<TopicsDatabase>> {
                                    List<TopicsDatabase> t;

                                    @Override
                                    protected List<TopicsDatabase> doInBackground(Void... voids) {
                                        TopicsModelDatabase
                                                .getInstance(context)
                                                .topicsDatabaseDao()
                                                .deleteTopic(topic.getTopic(), topic.getTopic_desc());

                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(List<TopicsDatabase> topicsDatabases) {
                                        super.onPostExecute(topicsDatabases);
                                        topics.remove(pos);
                                        notifyItemRemoved(pos);
                                    }
                                }
                                GetTasks gt = new GetTasks();
                                gt.execute();
                                }
                        })
                        .setNegativeButton("Nahh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return topics.size();
    }






    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView topic_name, topic_desc;
        public ImageView smiley;
        public CheckBox done;
        public RelativeLayout relativeLayout;


        public MyViewHolder(View view) {
            super(view);
            topic_name = (TextView) view.findViewById(R.id.data_title);
            smiley = (ImageView) view.findViewById(R.id.data_done);
            topic_desc = (TextView) view.findViewById(R.id.data_desc);
            done = (CheckBox) view.findViewById(R.id.checkbox_data);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.relative_topics);

        }
    }


}