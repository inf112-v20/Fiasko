package inf112.fiasko.roborally;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Game Board";
        cfg.width = 768;
        cfg.height = 769;

        new LwjglApplication(new GameLauncher(), cfg);
    }
}