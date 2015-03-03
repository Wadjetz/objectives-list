package fr.objectiveslist.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.List;

import fr.objectiveslist.R;
import fr.objectiveslist.helpers.Dates;

public class TaskAdapter extends BaseAdapter {

    private static final String TAG = "TaskAdapter";
    private List<Task> taskList;
    private Context context;
    private LayoutInflater inflater;

    private PeriodFormatter formatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix(" hours ")
            .appendMinutes()
            .appendSuffix(" minutes ")
            .toFormatter();

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
        TextView taskDesctiptionTextView = (TextView) layoutItem.findViewById(R.id.task_description);
        TextView tastLastTime = (TextView) layoutItem.findViewById(R.id.time_last);
        TextView state = (TextView) layoutItem.findViewById(R.id.state);

        Task task = this.taskList.get(position);
        Log.d(TAG, task.toString());
        taskTitleTextView.setText(task.getTitle());
        taskDesctiptionTextView.setText(task.getDescription().trim());

        state.setText(task.getState());


        if (new DateTime(task.getDateLimit().getTime()).isAfter(new Instant())) {
            long interval = new DateTime(task.getDateLimit().getTime()).minus(new DateTime().getMillis()).getMillis();
            Duration lastDuree = new Duration(interval);

            if (interval > (86400000 * 2)) {
                tastLastTime.setText("The " + Dates.dateFormat.format(task.getDateLimit()));
            } else {
                String formatted = formatter.print(lastDuree.toPeriod());
                tastLastTime.setText("In " + formatted);
            }
        } else {
            long interval = new DateTime().getMillis() - task.getDateLimit().getTime();
            Duration lastDuree = new Duration(interval);
            tastLastTime.setTextColor(context.getResources().getColor(R.color.label_red));
            if (interval > (86400000 * 2)) {
                tastLastTime.setText("to be made of " + Dates.prettyDateFormat.format(task.getDateLimit()));
            } else {
                String formatted = formatter.print(lastDuree.toPeriod());

                tastLastTime.setText("Late " + formatted);
            }
        }

        return layoutItem;
    }
}
