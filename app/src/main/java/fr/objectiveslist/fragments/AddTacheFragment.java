package fr.objectiveslist.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import fr.objectiveslist.R;
import fr.objectiveslist.TaskListActivity;
import fr.objectiveslist.helpers.DatePickerFragment;
import fr.objectiveslist.helpers.Dates;
import fr.objectiveslist.helpers.TimePickerFragment;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskDAO;


public class AddTacheFragment extends Fragment {

    private static final String TAG = "²";

    private Calendar calendar = Calendar.getInstance();

    private TaskDAO tasksDao = null;

    private EditText title = null;
    private EditText description = null;
    private Button dateLimit = null;
    private Button timeLimit = null;
    private Button save = null;
    private Button cancel = null;
    private Context context = null;
    private Spinner categories = null;

    public AddTacheFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_tache, container, false);
        Log.i(TAG, "test");
        context = inflater.getContext();
        title = (EditText) view.findViewById(R.id.task_title);
        description = (EditText) view.findViewById(R.id.task_description);
        dateLimit = (Button) view.findViewById(R.id.task_date_limit);

        dateLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        timeLimit = (Button) view.findViewById(R.id.task_time_limit);

        timeLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        dateLimit.setText(Dates.dateFormat.format(calendar.getTime()));
        timeLimit.setText(Dates.timeFormat.format(calendar.getTime()));
        categories = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.categorie, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categories.setAdapter(adapter);

        save = (Button) view.findViewById(R.id.save_task);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(v);
            }
        });


        cancel = (Button) view.findViewById(R.id.cancel_task);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAction(v);
            }
        });

        tasksDao = new TaskDAO(context);
        tasksDao.open();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tasksDao.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        tasksDao.close();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void saveTask(View v) {
        if(title.getText().toString().trim().length()>0 && title.getText().toString().trim().length()<=30) {
            Task task = new Task(title.getText().toString(), description.getText().toString(), Task.CREATED,
                    calendar.getTime(), categories.getSelectedItem().toString());
            Log.d(TAG, task.toString());
            tasksDao.create(task);
            Toast.makeText(context, "Task Saved " + task.toString(), Toast.LENGTH_LONG).show();
            startActivity(new Intent(context, TaskListActivity.class));

        } else {
            Toast.makeText(context, "You need to enter a title ! (Max 30 characters)", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelAction(View v) {
        ListFragment listFragment = new ListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, listFragment, "fragmentlist");
        fragmentTransaction.commit();
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
