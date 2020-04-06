package inf112.fiasko.roborally.networking;

import inf112.fiasko.roborally.objects.Player;

import java.util.List;

public class GameStartInfo {
    private String boardname;
    private List<Player> playerlist;
    public GameStartInfo(){}

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    public void setPlayerlist(List<Player> playerlist) {
        this.playerlist = playerlist;
    }

    public GameStartInfo(String boardname, List<Player> playerlist){
        this.boardname=boardname;
        this.playerlist=playerlist;
    }
    public List<Player> getPlayerlist(){
        return playerlist;
    }
    public String getBoardname(){
        return boardname;
    }

}
