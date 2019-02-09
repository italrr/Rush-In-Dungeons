package com.nite.rid;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nite.rid.RiD;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Rush In Dungeons";
		cfg.useGL20 = true;
		cfg.width = (int) (720/2);
		cfg.height = (int) (1280/2);	    
		new LwjglApplication(new RiD(new desktopSocial()), cfg);
	}
}

