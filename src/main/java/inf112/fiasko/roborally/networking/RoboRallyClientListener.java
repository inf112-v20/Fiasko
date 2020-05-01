package inf112.fiasko.roborally.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.gamewrapper.RoboRallyUI;
import inf112.fiasko.roborally.networking.containers.ErrorResponse;
import inf112.fiasko.roborally.networking.containers.GameStartInfoResponse;
import inf112.fiasko.roborally.networking.containers.HandResponse;
import inf112.fiasko.roborally.networking.containers.HurryResponse;
import inf112.fiasko.roborally.networking.containers.OkayResponse;
import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;
import inf112.fiasko.roborally.objects.RoboRallyGame;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * This listener handles all receiving from the server
 */
class RoboRallyClientListener extends Listener {
    private final RoboRallyUI wrapper;
    private RequestState lastRequestState = RequestState.NOT_SENT;

    /**
     * Instantiates a new Robo Rally client listener
     *
     * @param wrapper The Robo Rally wrapper to interact with
     */
    RoboRallyClientListener(RoboRallyUI wrapper) {
        super();
        this.wrapper = wrapper;
    }

    /**
     * Gets the state of the lastly sent request
     *
     * @return The state of the lastly sent request
     */
    public RequestState getLastRequestState() {
        return lastRequestState;
    }

    /**
     * Resets the state of the last request to sent not received
     */
    public void resetLastRequestState() {
        this.lastRequestState = RequestState.SENT_NOT_RECEIVED;
    }

    @Override
    public void disconnected(Connection connection) {
        this.wrapper.quit("The server closed the connection.");
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof ErrorResponse) {
            handleError((ErrorResponse) object);
        } else if (object instanceof GameStartInfoResponse) {
            receiveGameStartInfo((GameStartInfoResponse) object);
        } else if (object instanceof HandResponse) {
            receiveHand((HandResponse) object);
        } else if (object instanceof ProgramsContainerResponse) {
            receivePrograms((ProgramsContainerResponse) object);
        } else if (object instanceof PowerDownContainerResponse) {
            new Thread(() -> wrapper.getGame().receiveStayInPowerDown((PowerDownContainerResponse) object)).start();
        } else if (object instanceof OkayResponse) {
            this.lastRequestState = RequestState.SENT_OKAY;
        } else if (object instanceof HurryResponse) {
            this.wrapper.setShouldHurry(true);
        }
    }

    /**
     * Handle an error received from the server
     *
     * @param errorResponse The error response received
     */
    private void handleError(ErrorResponse errorResponse) {
        this.lastRequestState = RequestState.SENT_REJECTED;
        if (errorResponse.isCritical()) {
            wrapper.quit(errorResponse.getErrorMessage());
        }
        System.out.println(errorResponse.getErrorMessage());
    }

    /**
     * Receive information about the game and start the turn
     *
     * @param info The information received from the server
     */
    private void receiveGameStartInfo(GameStartInfoResponse info) {
        wrapper.setGame(new RoboRallyGame(info.getPlayerList(), info.getBoardName(), info.getPlayerName(),
                wrapper.getServer()));
        new Thread(() -> wrapper.getGame().runTurn()).start();
    }

    /**
     * Receive a hand from the server
     *
     * @param newHand The new hand this client can choose from
     */
    private void receiveHand(HandResponse newHand) {
        new Thread(() -> {
            //Prevents a bug where the game
            while (wrapper.getGame().getGameState() != GameState.WAITING_FOR_CARDS_FROM_SERVER) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (newHand.getNewHand().isEmpty() && wrapper.getGame().getRobotPowerDown()) {
                wrapper.getGame().setProgram(new ArrayList<>());
                wrapper.getGame().setGameState(GameState.SKIP_POWER_DOWN_SCREEN);
            } else {
                wrapper.getGame().setGameState(GameState.CHOOSING_CARDS);
            }
            wrapper.getGame().setPlayerHand(newHand.getNewHand());
            wrapper.getGame().setExtraCards(newHand.getExtraCards());
        }).start();
    }

    /**
     * Receive and handle programs send from server
     *
     * @param response The response received from the server
     */
    private void receivePrograms(ProgramsContainerResponse response) {
        new Thread(() -> {
            try {
                wrapper.getGame().receiveAllPrograms(response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

