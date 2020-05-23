package blackjack;

import java.util.ArrayList;

public class Table {
    private Player house = new Player(true);
    private ArrayList<Player> players = new ArrayList<>();
    private Shoe shoe;

    public Table() {} //The constructor really isn't needed

    public void init() {
        System.out.println("Table init started");
        System.out.println("Added house");
        shoe = new Shoe();
        shoe.init();
        shoe.shuffleDecks();
        System.out.println("Cards Shuffled");
        System.out.println("Table init finished");
    }
    
    public void AddNPC(String name) {
        players.add(new Player(name, true, Blackjack.NPCStartingBalance));
        System.out.println("NPC, "+name+", added. Player #"+(players.size()));
    }
    
    public void AddUser(String name) {
        players.add(new Player(name, false, Blackjack.UserStartingBalance));
        System.out.println("User, "+name+", added. Player #"+players.size());
    }
    
    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public Player getHouse() {
        return house;
    }

    public Shoe getShoe() {
        return shoe;
    }
}
