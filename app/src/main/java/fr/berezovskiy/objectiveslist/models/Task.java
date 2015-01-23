package fr.berezovskiy.objectiveslist.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import fr.berezovskiy.objectiveslist.helpers.SQLiteHelper;

public class Task implements Parcelable {

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
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Task(String title, String description, String state, String dateLimit) {
        this.id = 0;
        this.title = title;
        this.description = description;
        this.state = state;
        this.dateLimit = SQLiteHelper.getDateTime(dateLimit);
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }



    private Task(Parcel in) {
        this.setTitle(in.readString());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getTitle());
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
}
