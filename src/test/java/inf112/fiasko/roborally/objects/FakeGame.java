package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.elementproperties.GameState;
import inf112.fiasko.roborally.networking.containers.PowerDownContainerResponse;
import inf112.fiasko.roborally.networking.containers.ProgramsContainerResponse;

import java.util.List;

/**
 * A class implementing just enough features of an interactable game to be able to test a phase
 */
public class FakeGame implements InteractableGame {
    private String winningPlayerName;

    @Override
    public GameState getGameState() {
        return null;
    }

    @Override
    public void setGameState(GameState gameState) {
        //Not needed for testing
    }

    @Override
    public String getWinningPlayerName() {
        return winningPlayerName;
    }

    @Override
    public void setWinningPlayerName(String winningPlayerName) {
        this.winningPlayerName = winningPlayerName;
    }

    @Override
    public void receiveAllPrograms(ProgramsContainerResponse programs) {
        //Not needed for testing
    }

    @Override
    public void receiveStayInPowerDown(PowerDownContainerResponse powerDowns) {
        //Not needed for testing
    }

    @Override
    public ProgrammingCardDeck getPlayerHand() {
        return null;
    }

    @Override
    public void setPlayerHand(ProgrammingCardDeck playerHand) {
        //Not needed for testing
    }

    @Override
    public ProgrammingCardDeck getExtraCards() {
        return null;
    }

    @Override
    public void setExtraCards(ProgrammingCardDeck extraCards) {
        //Not needed for testing
    }

    @Override
    public int getProgramSize() {
        return 0;
    }

    @Override
    public List<ProgrammingCard> getProgram() {
        return null;
    }

    @Override
    public void setProgram(List<ProgrammingCard> program) {
        //Not needed for testing
    }
}
