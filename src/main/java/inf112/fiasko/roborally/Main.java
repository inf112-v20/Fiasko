package inf112.fiasko.roborally;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;

/**
 * The mail class which runs the game
 */
public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";
        cfg.width = 900;
        cfg.height = 900;
        cfg.samples = 3;

        new LwjglApplication(new RoboRallyWrapper(), cfg);
    }
}