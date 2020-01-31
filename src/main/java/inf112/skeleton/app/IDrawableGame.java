package inf112.skeleton.app;

import java.util.List;

/**
 * This interface describes a game drawable using libgdx
 */
public interface IDrawableGame {

    /**
     * Gets a list of objects which are to be rendered
     * @return A list of drawable objects in the order they are to be drawn
     */
    List<IDrawableObject> objectsToRender();

}
