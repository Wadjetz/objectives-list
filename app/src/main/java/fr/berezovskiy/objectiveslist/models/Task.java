package fr.berezovskiy.objectiveslist.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private String title;

    public Task(String title) {
        this.title = title;
    }

    private Task(Parcel in) {
        this.setTitle(in.readString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                '}';
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
}
