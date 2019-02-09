package com.nite.rid;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nite.rid.RiD;

public class MainActivity extends AndroidApplication {
	androidSocial currentSocial;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        super.onCreate(savedInstanceState);
        currentSocial = new androidSocial();
        currentSocial.setActivity(this);
	    initialize(new RiD(currentSocial), false);
	    currentSocial.setActive();
    }
    
    public void onResume() {
        super.onResume();
        currentSocial.setActive();
    }

    public void onPause() {
        super.onPause();
        currentSocial.setInActive();
        
    }
}