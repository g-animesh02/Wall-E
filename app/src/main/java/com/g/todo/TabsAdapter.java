package com.g.todo;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.g.todo.assignments.AssignmentsFragment;
import com.g.todo.topics.TopicsFragment;

public class TabsAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    String subject;

    public TabsAdapter(Context context, FragmentManager fm, int totalTabs, String sub) {
        super(fm);
        this.subject = sub;
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TopicsFragment(subject);
            case 1:
                return new AssignmentsFragment(subject);

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
