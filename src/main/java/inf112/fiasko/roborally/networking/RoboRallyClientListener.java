package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.GameStartInfo;
import inf112.fiasko.roborally.networking.containers.PowerdownContainer;
import inf112.fiasko.roborally.networking.containers.ProgamsContainer;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;
import inf112.fiasko.roborally.objects.RoboRallyGame;

/**
 * This listener handles all receiving from the server
 */
class RoboRallyClientListener extends Listener {
    private final RoboRallyWrapper wrapper;

    /**
     * Instantiates a new Robo Rally client listener
     * @param wrapper The Robo Rally wrapper to interact with
     */
    RoboRallyClientListener(RoboRallyWrapper wrapper) {
        super();
        this.wrapper = wrapper;
    }

    @Override
    public void received (Connection connection, Object object) {
        if (object instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) object;
            System.out.println(errorResponse.getErrorMessage());
        } else if (object instanceof GameStartInfo) {
            GameStartInfo info = (GameStartInfo) object;
            wrapper.roboRallyGame = new RoboRallyGame(info.getPlayerList(), info.getBoardName(),
                    wrapper.server != null, info.getPlayerName(), wrapper.client, wrapper.server);
        } else if (object instanceof ProgrammingCardDeck) {
            wrapper.roboRallyGame.setGameState(GameState.CHOOSING_CARDS);
            wrapper.roboRallyGame.setPlayerHand((ProgrammingCardDeck) object);
        } else if (object instanceof ProgamsContainer) {
            try {
                wrapper.roboRallyGame.receiveAllPrograms((ProgamsContainer) object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (object instanceof PowerdownContainer) {
            wrapper.roboRallyGame.receiveStayInPowerDown((PowerdownContainer) object);
        }

    }
}

