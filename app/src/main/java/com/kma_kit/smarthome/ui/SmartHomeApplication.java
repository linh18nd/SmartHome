package com.kma_kit.smarthome.ui;

import android.app.Application;
import android.content.Context;

import com.kma_kit.smarthome.data.model.response.UserResponse;
import com.kma_kit.smarthome.ui.fragment.RootController;

public class SmartHomeApplication extends Application {

    private static SmartHomeApplication instance;
    private RootController rootController;

    public static synchronized SmartHomeApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        rootController = new RootController();
    }

    public RootController getRootController() {
        return rootController;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
