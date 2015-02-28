package fr.objectiveslist;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import fr.objectiveslist.helpers.SQLiteHelper;
import fr.objectiveslist.helpers.TimePickerFragment;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskDAO;


public class EditTaskActivity extends FragmentActivity {

    private static final String TAG = "EditTaskActivity";
    private Calendar calendar = Calendar.getInstance();

    private TaskDAO tasksDao = null;
    private Task task = null;

    private EditText title = null;
    private EditText description = null;
    private Button dateLimit = null;
    private Button timeLimit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        title = (EditText) findViewById(R.id.task_title);
        description = (EditText) findViewById(R.id.task_description);
        dateLimit = (Button) findViewById(R.id.task_date_limit);
        timeLimit = (Button) findViewById(R.id.task_time_limit);



        Intent intent = getIntent();
        task = intent.getExtras().getParcelable(TaskActivity.TASK_EDITED);
        Log.d(TAG, task.toString());

        calendar.setTimeInMillis(task.getDateLimit().getTime());

        dateLimit.setText(SQLiteHelper.dateFormat.format(calendar.getTime()));
        timeLimit.setText(SQLiteHelper.timeFormat.format(calendar.getTime()));

        title.setText(task.getTitle());
        description.setText(task.getDescription());

        tasksDao = new TaskDAO(this);
        tasksDao.open();
    }

    public void updateTask(View v) {

        task.setTitle(title.getText().toString());
        task.setDescription(description.getText().toString());
        task.setDateLimit(calendar.getTime());

        Log.d(TAG, task.toString());
        int res = tasksDao.update(task);
        if (res > 0) {
            Toast.makeText(this, "Task Saved " + task.toString(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, TaskListActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Task Update Error", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelAction(View v) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(TaskActivity.TASK_EDITED, task);
        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment date = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                calendar.set(y, m, d);
                dateLimit.setText(SQLiteHelper.dateFormat.format(calendar.getTime()));
            }
        };
        date.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment time = new TimePickerFragment() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                timeLimit.setText(SQLiteHelper.timeFormat.format(calendar.getTime()));
            }
        };
        time.show(getSupportFragmentManager(), "timePicker");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
