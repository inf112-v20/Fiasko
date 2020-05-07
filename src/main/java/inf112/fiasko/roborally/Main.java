package inf112.fiasko.roborally;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.fiasko.roborally.ui.RoboRallyWrapper;

/**
 * The mail class which runs the game
 */
public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "Robo Rally";
        configuration.width = 900;
        configuration.height = 900;
        configuration.samples = 3;

        new LwjglApplication(new RoboRallyWrapper(), configuration);
    }
}