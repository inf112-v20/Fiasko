package inf112.fiasko.roborally.gamewrapper.screens;

import com.badlogic.gdx.math.Rectangle;
import inf112.fiasko.roborally.objects.ProgrammingCard;

/**
 * A helper class for keeping track of card information and a rectangle
 */
public class CardRectangle {
    protected final Rectangle rectangle;
    protected final ProgrammingCard card;
    protected boolean selected = false;

    /**
     * Instantiates a new card rectangle
     * @param rectangle The rectangle of this card rectangle
     * @param card The card of this card rectangle
     */
    CardRectangle(Rectangle rectangle, ProgrammingCard card) {
        this.rectangle = rectangle;
        this.card = card;
    }

    @Override
    public String toString() {
        return card.toString();
    }
}