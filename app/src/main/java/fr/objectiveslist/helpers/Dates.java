package fr.objectiveslist.helpers;


import java.text.SimpleDateFormat;
import java.util.Locale;

public class Dates {
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd", Locale.getDefault());
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static SimpleDateFormat prettyDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    public static SimpleDateFormat prettyDateTimeFormat = new SimpleDateFormat("dd MMMM yyyy - HH:mm", Locale.getDefault());
}
