package com.g.todo;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Main_subject_adapter extends RecyclerView.Adapter<Main_subject_adapter.MyViewHolder> {
    private final List<Subject_Names> subName;

    public Main_subject_adapter(List<Subject_Names> subName) {
        this.subName =subName;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subject_name, subject_desc;
        public ImageView subpic;
        public ImageButton next;
        public RelativeLayout relativeLayout;


        public MyViewHolder(View view) {
            super(view);
            subject_name = (TextView) view.findViewById(R.id.subjectName);
            subpic = (ImageView)view.findViewById(R.id.subjectPic);
            subject_desc = (TextView)view.findViewById(R.id.subject_description);
            relativeLayout = (RelativeLayout)view.findViewById(R.id.relative_subjects);
            next = (ImageButton) view.findViewById(R.id.subject_next);

        }
    }










    @NonNull
    @Override
    public Main_subject_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_rows_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Main_subject_adapter.MyViewHolder holder, int position) {
        Subject_Names sub = subName.get(position);
        holder.relativeLayout.setBackgroundResource(sub.getBackgroundRows());
        holder.subject_name.setText(sub.getSub());
        holder.subject_desc.setText(sub.getSub_desc());
        setpic(holder, sub.getPic());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(v.getClass().getA);
                Intent i = new Intent(v.getContext(), SubjectData.class);
                i.putExtra("SUBJECT_NAME", sub.getSub());
                v.getContext().startActivity(i);
            }
        });
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(v.getClass().getA);
                Intent i = new Intent(v.getContext(), SubjectData.class);
                i.putExtra("SUBJECT_NAME", sub.getSub());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subName.size();
    }

    public void setpic(Main_subject_adapter.MyViewHolder holder, int pic){
        holder.subpic.setImageResource(pic);

    }
}
