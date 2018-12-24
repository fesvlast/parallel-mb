package ua.mb.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {

    public static final String ENABLE = "enable";

    public static final String DISABLE = "disable";

    /**
     * @param deviceId
     * @param key
     * @return
     */
    public String manageMobileInternet(String deviceId, String key){
        return "adb -s " + deviceId + " shell svc data "+ key;

    }

    public String isConnectedToInternet(String deviceId){
        return "adb -s " + deviceId + " shell ping -w 1 -q www.google.com";
    }

    public String forceStopChrome(String deviceId){
        return "adb -s " + deviceId + " shell am force-stop com.android.chrome";
    }

    public String getListOfConnectedDevices(){
        return "adb devices";
    }
}

