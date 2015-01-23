package fr.berezovskiy.objectiveslist.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fr.berezovskiy.objectiveslist.models.TaskDAO;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getName();

    private static final String DATABASE_NAME = "objectives_list.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " +
            TaskDAO.TABLE_NAME + " ( " +
                TaskDAO.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskDAO.TITLE + " TEXT NOT NULL, " +
                TaskDAO.DESCRIPTION + " TEXT NULL, " +
                TaskDAO.STATE + " TEXT NOT NULL, " +
                TaskDAO.DATE_LIMIT + " DATETIME NOT NULL, " +
                TaskDAO.CREATED_AT + " DATETIME NOT NULL, " +
                TaskDAO.UPDATED_AT + " DATETIME NOT NULL " +
            " );";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TaskDAO.TABLE_NAME);
        onCreate(db);
    }
}
