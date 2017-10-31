package com.apps.my.myhealthassist.DataModal;

import java.util.Date;

/**
 * Created by Manoj on 10/16/2017.
 */

public class Scheduler {
    private int _ID;
    private Date _Date;
    private Date _Time;
    private boolean _Recurrence;
    private boolean _IsScheduled;
    private String _VoiceMessage;

    public int getID()
    {
        return _ID;
    }

    public Date getDate()
    {
        return _Date;
    }

    public Date getTime()
    {
        return _Time;
    }

    public boolean getRecurrence()
    {
        return _Recurrence;
    }

    public boolean isScheduled()
    {
        return _IsScheduled;
    }

    public String getVoiceMessage()
    {
        return _VoiceMessage;
    }

    public void setID(int value)
    {
        _ID = value;
    }

    public void setDate(Date value)
    {
        _Date = value;
    }

    public void setTime(Date value)
    {
        _Time = value;
    }

    public void setRecurrence(boolean value)
    {
        _Recurrence = value;
    }

    public void setScheduled(boolean value)
    {
        _IsScheduled = value;
    }

    public void setVoiceMessage(String value)
    {
        _VoiceMessage = value;
    }
}
