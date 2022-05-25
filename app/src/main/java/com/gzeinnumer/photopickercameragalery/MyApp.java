package com.gzeinnumer.photopickercameragalery;

import android.app.Application;

import com.gzeinnumer.eeda.helper.FGDir;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String externalFolderName = "Download/"+getApplicationContext().getString(R.string.app_name); //MyLibsTesting

        FGDir.initExternalDirectoryName(externalFolderName);
    }
}
