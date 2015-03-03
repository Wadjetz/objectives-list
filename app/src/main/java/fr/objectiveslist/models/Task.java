package fr.objectiveslist.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

import fr.objectiveslist.helpers.SQLiteHelper;

public class Task implements Parcelable {

    public static final String DONE = "Finish";
    public static final String CREATED = "In progress";
    public static final String UNDONE = "Cancel";

    private int id = 0;
    private String title = "";
    private String description = "";
    private String state = "";
    private Date dateLimit = null;
    private Date createdAt = null;
    private Date updatedAt = null;

    public Task() {}

    public Task(String title, String description, String state, Date dateLimit) {
        this.id = 0;
        this.title = title;
        this.description = description;
        this.state = state;
        this.dateLimit = dateLimit;
    }

    public Task(String title, String description, String state, String dateLimit) {
        this.id = 0;
        this.title = title;
        this.description = description;
        this.state = state;
        this.dateLimit = SQLiteHelper.getDateTime(dateLimit);
    }

    private Task(Parcel in) {
        this.setId(in.readInt());
        this.setTitle(in.readString());
        this.setDescription(in.readString());
        this.setState(in.readString());
        this.setDateLimit(dateFromTimestamp(in.readLong()));
        this.setCreatedAt(dateFromTimestamp(in.readLong()));
        this.setUpdatedAt(dateFromTimestamp(in.readLong()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getTitle());
        dest.writeString(this.getDescription());
        dest.writeString(this.getState());
        dest.writeLong(this.getDateLimit().getTime());
        dest.writeLong(this.getCreatedAt().getTime());
        dest.writeLong(this.getUpdatedAt().getTime());
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[0];
        }
    };

    public Date dateFromTimestamp(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c.getTime();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", dateLimit=" + dateLimit +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(Date dateLimit) {
        this.dateLimit = dateLimit;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
