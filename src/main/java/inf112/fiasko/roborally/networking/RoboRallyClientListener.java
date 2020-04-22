package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.gamewrapper.RoboRallyWrapper;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.GameStartInfoResponse;
import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;
import inf112.fiasko.roborally.objects.ProgrammingCardDeck;
import inf112.fiasko.roborally.objects.RoboRallyGame;

import java.util.ArrayList;

/**
 * This listener handles all receiving from the server
 */
class RoboRallyClientListener extends Listener {
    private final RoboRallyWrapper wrapper;

    /**
     * Instantiates a new Robo Rally client listener
     *
     * @param wrapper The Robo Rally wrapper to interact with
     */
    RoboRallyClientListener(RoboRallyWrapper wrapper) {
        super();
        this.wrapper = wrapper;
    }

    @Override
    public void disconnected(Connection connection) {
        this.wrapper.quit("The server closed the connection.");
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) object;
            if (errorResponse.isCritical()) {
                wrapper.quit(errorResponse.getErrorMessage());
            }
            System.out.println(errorResponse.getErrorMessage());
        } else if (object instanceof GameStartInfoResponse) {
            GameStartInfoResponse info = (GameStartInfoResponse) object;
            wrapper.roboRallyGame = new RoboRallyGame(info.getPlayerList(), info.getBoardName(),
                    wrapper.server != null, info.getPlayerName(), wrapper.server);
        } else if (object instanceof ProgrammingCardDeck) {
            if (((ProgrammingCardDeck) object).isEmpty()) {
                wrapper.roboRallyGame.setProgram(new ArrayList<>());
                if (wrapper.roboRallyGame.getRobotPowerDown()) {
                    wrapper.roboRallyGame.setGameState(GameState.SKIP_POWER_DOWN_SCREEN);
                } else {
                    wrapper.roboRallyGame.setGameState(GameState.CHOOSING_POWER_DOWN);
                }
            } else {
                wrapper.roboRallyGame.setGameState(GameState.CHOOSING_CARDS);
            }
            new Thread(() -> wrapper.roboRallyGame.setPlayerHand((ProgrammingCardDeck) object)).start();
        } else if (object instanceof ProgramsContainerResponse) {
            new Thread(() -> {
                try {
                    wrapper.roboRallyGame.receiveAllPrograms((ProgramsContainerResponse) object);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else if (object instanceof PowerDownContainerResponse) {
            new Thread(() -> wrapper.roboRallyGame.receiveStayInPowerDown((PowerDownContainerResponse) object)).start();
        }

    }
}

