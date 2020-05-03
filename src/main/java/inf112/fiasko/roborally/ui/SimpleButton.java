package inf112.fiasko.roborally.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * This class generates a simple text button using a default skin
 */
public class SimpleButton {
    private final TextButton button;

    /**
     * Instantiates a new simple button
     *
     * @param text The text to display on the button
     * @param font The font to use to draw button text
     */
    public SimpleButton(String text, BitmapFont font) {
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        Skin skin = new Skin(buttonAtlas);
        TextButton.TextButtonStyle confirmCardsStyle = new TextButton.TextButtonStyle();
        confirmCardsStyle.font = font;
        confirmCardsStyle.up = skin.getDrawable("default-round");
        confirmCardsStyle.down = skin.getDrawable("default-round-down");
        this.button = new TextButton(text, confirmCardsStyle);
    }

    /**
     * Gets the button generated
     *
     * @return A button
     */
    public TextButton getButton() {
        return this.button;
    }
}
