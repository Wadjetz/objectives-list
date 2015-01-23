package fr.berezovskiy.objectiveslist.models;

import android.database.sqlite.SQLiteDatabase;

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
}
