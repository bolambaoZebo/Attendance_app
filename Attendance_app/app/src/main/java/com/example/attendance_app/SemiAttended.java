package com.example.attendance_app;

public class SemiAttended {

    int id;
    String title;
    String date;

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

    public String getDale() {
        return date;
    }

    public void setDale(String dale) {
        this.date = dale;
    }

    public SemiAttended(int id,String title, String date) {

        this.id = id;
        this.title = title;
        this.date = date;
    }
}
