package blackjack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Shoe { //A shoe holds the decks of cards
    private ArrayList cardsUsed = new ArrayList();
    private ArrayList cardsLeft = new ArrayList();
    private final Player house;
    private ArrayList players;
    private final int decks = 8;

    public Shoe() {
        this.house = Blackjack.getTable().getHouse();
        this.players = Blackjack.getTable().getPlayers();
    }
    
    public void init() { //Adds the decks
        System.out.println("Shoe init started");
        for(int deck=1;deck<=decks;deck++) {
            for(int suit=0;suit<4;suit++) {
                for(int rank=1;rank<=13;rank++) {
                    if(rank==1) cardsLeft.add(new Card(suit,"A"));
                    else if(rank<11) cardsLeft.add(new Card(suit,""+rank));
                    else {
                        switch(rank) {
                            case 11:
                                cardsLeft.add(new Card(suit,"J"));
                                break;
                            case 12:
                                cardsLeft.add(new Card(suit,"Q"));
                                break;
                            case 13:
                                cardsLeft.add(new Card(suit,"K"));
                                break;
                            default:
                                System.out.println("Well whoops - d:"+deck+"s:"+suit+" r"+rank);
                                break;
                        }
                    }
                }
            }
        }
        System.out.println("Shoe init finished. cardsLeft size:"+cardsLeft.size()+" decks:"+cardsLeft.size()/52);
    }
    
    public void shuffleDecks() {
        ArrayList shuffledCards = new ArrayList();
        while(cardsLeft.size()>0) {
            int randomNum = ThreadLocalRandom.current().nextInt(0,cardsLeft.size());
            shuffledCards.add(cardsLeft.get(randomNum));
            cardsLeft.remove(randomNum);
        }
        cardsLeft = shuffledCards;
    }
    
    public void printCardsLeft() {
        for(int card=0;card<cardsLeft.size();card++) {
            Card cardObj = (Card) cardsLeft.get(card);
            if(card%52==0) System.out.print("\n\n====="+(card/52+1)+"=====");
            if(card%13==0) System.out.println("");
            System.out.print(cardObj.getRankAndSuit()+" ");
        }
        System.out.println();
    }
    
    public Card getCard() {
        if(cardsLeft.isEmpty()) {
            System.out.println("Cards were emptied, refilling and shuffling.");
            init();
            shuffleDecks();
            System.out.println("Decks shuffled.");
        }
        Card card = (Card)cardsLeft.get(0);
        cardsLeft.remove(0);
        return card;
    }
}