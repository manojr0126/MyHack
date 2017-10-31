package com.apps.my.myhealthassist.DataModal;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by Manoj on 10/20/2017.
 */

public class APIRequest {

    private String _Language = Locale.getDefault().getLanguage();
    private String _SessionID = UUID.randomUUID().toString();
    private String _converseType = "";

    public String getLanguage() {
        return _Language;
    }

    public String getSessionID() {
        return _SessionID;
    }

    public String getConverseType () { return _converseType; }

    public  void setConverseType(String value)
    {
        _converseType = value;
    }
}
