package com.apps.my.myhealthassist.Utilities;

/**
 * Created by Manoj on 10/23/2017.
 */

public final class Config {

    private static String _apiURL = "https://mystical.herokuapp.com/v1/d3/sendtoapi";
    //_apiURL = "https://samplejavaapi.herokuapp.com/rest/helloworld/sendtoapi";

    private Config(){
    }

    public static String getAPI_URL() {
        return _apiURL;
    }
}
