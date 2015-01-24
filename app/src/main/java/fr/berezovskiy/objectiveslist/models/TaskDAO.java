package fr.berezovskiy.objectiveslist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.berezovskiy.objectiveslist.helpers.SQLiteHelper;

public class TaskDAO {

    public static final String TABLE_NAME = "tasks";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String STATE = "state";
    public static final String DATE_LIMIT = "date_limit";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    private String[] allColumns = {
            ID,
            TITLE,
            DESCRIPTION,
            STATE,
            DATE_LIMIT,
            CREATED_AT,
            UPDATED_AT
    };

    public TaskDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Task create(Task task) {
        ContentValues values = new ContentValues();
        values.put(TITLE, task.getTitle());
        values.put(DESCRIPTION, task.getDescription());
        values.put(STATE, task.getState());
        values.put(DATE_LIMIT, SQLiteHelper.getDateTime(task.getDateLimit()));

        long insertId = db.insert(TABLE_NAME, null, values);
        Cursor cursor = db.query(TABLE_NAME, allColumns, ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getInt(0));
        task.setTitle(cursor.getString(1));
        task.setDescription(cursor.getString(2));
        task.setState(cursor.getString(3));
        task.setDateLimit(SQLiteHelper.getDateTime(cursor.getString(4)));
        task.setCreatedAt(SQLiteHelper.getDateTime(cursor.getString(5)));
        task.setUpdatedAt(SQLiteHelper.getDateTime(cursor.getString(6)));
        return task;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (! cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }

        cursor.close();
        return tasks;
    }

}

