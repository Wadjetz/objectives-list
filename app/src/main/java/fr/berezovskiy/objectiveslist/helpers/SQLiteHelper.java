package fr.berezovskiy.objectiveslist.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.berezovskiy.objectiveslist.models.TaskDAO;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getName();

    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd", Locale.getDefault());
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private static final String DATABASE_NAME = "objectives_list.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " +
            TaskDAO.TABLE_NAME + " ( " +
                TaskDAO.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskDAO.TITLE + " TEXT NOT NULL, " +
                TaskDAO.DESCRIPTION + " TEXT NULL, " +
                TaskDAO.STATE + " TEXT NOT NULL, " +
                TaskDAO.DATE_LIMIT + " DATETIME NOT NULL, " +
                TaskDAO.CREATED_AT + " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                TaskDAO.UPDATED_AT + " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
            " );";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public static String getDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    public static Date getDateTime(int yyyy, int MM, int dd, int HH, int mm) {
        final Calendar c = Calendar.getInstance();
        c.set(yyyy, MM, dd, HH, mm);
        return c.getTime();
    }

    public static Date getDateTime(String dateTime) {
        Date date = new Date();
        try {
            date = dateTimeFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
