package com.apps.my.myhealthassist.DataModal;

/**
 * Created by Manoj on 10/20/2017.
 */

public class APIResponse {
    private String _Date = "";
    private String _Recurrence = "";
    private String _Time = "";
    private String _Medicine = "";

    public String getDate() {
        return _Date;
    }

    public String getRecurrence() {
        return _Recurrence;
    }

    public String getTime() {
        return _Time;
    }

    public String getMedicine() {
        return _Medicine;
    }

    public void setDate(String value) {
        _Date = value;
    }

    public void setRecurrence(String value) {
        _Recurrence = value;
    }

    public void setTime(String value) {
        _Time = value;
    }

    public void setMedicine(String value) {
        _Medicine = value;
    }
}
