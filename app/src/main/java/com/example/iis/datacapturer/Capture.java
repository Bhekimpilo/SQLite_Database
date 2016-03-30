package com.example.iis.datacapturer;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by IIS on 9/18/2015.
 */
public class Capture extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "TrjNGWng2sTmwGw9VI2Rdj0B4ZvEY5V5zoTyyex9",
                "K0hXNBjrckv1RykV1taVrATJ2mWb20cM8dTLl6IE");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

    }
}
