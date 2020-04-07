package inf112.fiasko.roborally.game_wrapper.screens;

import com.badlogic.gdx.Screen;

/**
 * This class overrides methods of screens which are often unused
 */
public abstract class AbstractScreen implements Screen {
    final int applicationWidth = 600;
    final int applicationHeight = 800;

    @Override
    public void show() {
        //Nothing to do
    }

    @Override
    public void pause() {
        //Nothing to do
    }

    @Override
    public void resume() {
        //Nothing to do
    }

    @Override
    public void hide() {
        //Nothing to do
    }

    @Override
    public void dispose() {
        //Nothing to do
    }
}
