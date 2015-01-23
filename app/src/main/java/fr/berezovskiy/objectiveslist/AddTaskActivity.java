package fr.berezovskiy.objectiveslist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

import fr.berezovskiy.objectiveslist.models.Task;
import fr.berezovskiy.objectiveslist.models.TaskDAO;


public class AddTaskActivity extends ActionBarActivity {

    private static final String TAG = AddTaskActivity.class.getName();

    private Button saveButton = null;

    private TaskDAO tasksDao = null;

    private EditText title = null;
    private EditText description = null;
    private DatePicker dateLimit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        title = (EditText) findViewById(R.id.task_title);
        description = (EditText) findViewById(R.id.task_description);
        dateLimit = (DatePicker) findViewById(R.id.task_date_limit);

        saveButton = (Button) findViewById(R.id.save);

        tasksDao = new TaskDAO(this);
        tasksDao.open();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task(title.getText().toString(), description.getText().toString(), "created", new Date());
                tasksDao.create(task);
                Log.d(TAG, "Save = " + task.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
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
