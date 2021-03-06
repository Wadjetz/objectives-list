package fr.objectiveslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import fr.objectiveslist.helpers.DatePickerFragment;
import fr.objectiveslist.helpers.Dates;
import fr.objectiveslist.helpers.TimePickerFragment;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskDAO;


public class AddTaskActivity extends ActionBarActivity {

    private static final String TAG = "AddTaskActivity";

    private Calendar calendar = Calendar.getInstance();

    private TaskDAO tasksDao = null;

    private EditText title = null;
    private EditText description = null;
    private Button dateLimit = null;
    private Button timeLimit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = (EditText) findViewById(R.id.task_title);
        description = (EditText) findViewById(R.id.task_description);
        dateLimit = (Button) findViewById(R.id.task_date_limit);
        timeLimit = (Button) findViewById(R.id.task_time_limit);

        dateLimit.setText(Dates.dateFormat.format(calendar.getTime()));
        timeLimit.setText(Dates.timeFormat.format(calendar.getTime()));

        tasksDao = new TaskDAO(this);
        tasksDao.open();
    }


    @Override
    protected void onResume() {
        super.onResume();
        tasksDao.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tasksDao.close();
    }

    public void saveTask(View v) {
        if(title.getText().toString().trim().length()>0 && title.getText().toString().trim().length()<=30) {
            Task task = new Task(title.getText().toString(), description.getText().toString(), Task.CREATED,
                    calendar.getTime(), "");
            Log.d(TAG, task.toString());
            tasksDao.create(task);
            Toast.makeText(this, "Task Saved " + task.toString(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, TaskListActivity.class));
            finish();
        } else {
            Toast.makeText(this, "You need to enter a title ! (Max 30 characters)", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelAction(View v) {
        startActivity(new Intent(this, TaskListActivity.class));
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment date = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                calendar.set(y, m, d);
                dateLimit.setText(Dates.dateFormat.format(calendar.getTime()));
            }
        };
        date.show(getFragmentManager().beginTransaction(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment time = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                timeLimit.setText(Dates.timeFormat.format(calendar.getTime()));
            }
        };
        time.show(getFragmentManager(), "timePicker");
    }
}
