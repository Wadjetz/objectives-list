package fr.objectiveslist.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.objectiveslist.R;
import fr.objectiveslist.TaskActivity;
import fr.objectiveslist.TaskListActivity;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskAdapter;
import fr.objectiveslist.models.TaskDAO;


public class ListFragment extends Fragment {

    private ListView taskListView = null;

    private ArrayList<Task> tasks = new ArrayList<>();

    private TaskDAO tasksDao = null;


    private List<Task> list = null;

    private Button trie = null;

    private static final String TAG = "TaskListActivity";
    public static final String TASK_SELECTED = "TASK_SELECTED";
    private FragmentManager fragmentManager = null;

    private Context context = null;
    public ListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        fragmentManager = this.getFragmentManager();
        tasksDao = new TaskDAO(inflater.getContext());
        tasksDao.open();
        context = view.getContext();
        //tasks = (ArrayList<Task>) tasksDao.getAllTasks();
        tasks = (ArrayList<Task>) tasksDao.getNonFinishedTasks();
        trie = (Button) view.findViewById(R.id.trie);
        Log.d(TAG, tasks.toString());

        taskListView = (ListView) view.findViewById(R.id.task_listView);
        if(list == null) {
            taskListView.setAdapter(new TaskAdapter(context, tasks));
        }else{
            taskListView.setAdapter(new TaskAdapter(context, list));
        }


        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                Log.d(TAG, task.toString());
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra(TASK_SELECTED, task);
                Log.d(TAG, task.toString());
                startActivity(intent);
            }
        });

        timeAlert();


        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0){
            tasks = data.getParcelableArrayListExtra("ResultTrie");

            taskListView.setAdapter(new TaskAdapter(context, tasks));
        }

    }

    public void timeAlert()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        String formattedDate = df.format(c.getTime());

        Cursor cursor = tasksDao.db.query(tasksDao.TABLE_NAME, tasksDao.allColumns, "date_limit < '"+formattedDate+"' and state = 'In progress'", null, null, null, null);
        cursor.moveToFirst();
        while (! cursor.isAfterLast()) {
            Log.d(TAG, cursor.getString(4));
            cursor.moveToNext();
        }

        if(cursor.getCount()>0) {
            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tasks are passed !!!");
            builder.setPositiveButton("Ok", null);
            builder.create().show();
            */

            Toast.makeText(context, "Tasks are passed !!!", Toast.LENGTH_SHORT).show();
        }

        cursor.close();

    }


    public void setList(List<Task> list){
        this.list = list;

    }
}
