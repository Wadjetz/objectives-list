package fr.berezovskiy.objectiveslist.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.berezovskiy.objectiveslist.R;

public class TaskAdapter extends BaseAdapter {

    private List<Task> taskList;
    private Context context;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.taskList = taskList;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        if (convertView == null) {
            layoutItem = (LinearLayout) inflater.inflate(R.layout.task_list_view_layout, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView taskTitleTextView = (TextView) layoutItem.findViewById(R.id.task_title);
        taskTitleTextView.setText(this.taskList.get(position).getTitle());

        return layoutItem;
    }
}
