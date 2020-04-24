package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.InputProcessor;

/**
 * This class overrides all InputProcessor methods to make cleaner abstract screens with input processors
 */
public abstract class InteractiveScreen extends AbstractScreen implements InputProcessor {
    @Override
    public void show() {
        super.show();
        inputMultiplexer.addProcessor(this);
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
