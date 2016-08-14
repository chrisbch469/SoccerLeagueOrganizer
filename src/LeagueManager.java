import Model.Player;
import Model.Players;
import View.PromptMenu;

public class LeagueManager {
    public static void main(String[] args){
        Player[] players = Players.load();
        PromptMenu promptMenu = new PromptMenu(players);
        promptMenu.runPromptMenu();
    }
}
