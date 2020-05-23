package blackjack;

public class Card {
    private final String strValue;
    private final String suit;
    private final String suitName;
    private int aceValue;
    private boolean accountedFor;
    
    public Card(int newSuit, String newStrValue) {
        this.strValue = newStrValue;
        if(newSuit==0) {
            this.suit = "♥";
            this.suitName = "hearts";
        }
        else if(newSuit==1) {
            this.suit = "♦";
            this.suitName = "diamonds";
        }
        else if(newSuit==2) {
            this.suit = "♣";
            this.suitName = "clubs";
        }
        else if(newSuit==3) {
            this.suit = "♠";
            this.suitName = "spades";
        }
        else {
            this.suit = "?";
            this.suitName = "unknown";
        }
    }
    
    public static int strValueToValue(String strValue) {
        if(strValue.equals("K")) return 10;
        else if(strValue.equals("Q")) return 10;
        else if(strValue.equals("J")) return 10;
        else if(strValue.equals("A")) return 11;
        else{
            return Integer.parseInt(strValue);
        }
    }
    
    public void printRankAndSuit() {
        if(getSuit().equals("♥")||getSuit().equals("♦")) {
                System.out.println(ANSI.RED+getRankAndSuit()+ANSI.DEFAULT);
            }
            else {
                System.out.println(ANSI.BLACK+getRankAndSuit()+ANSI.DEFAULT);
            }
    }
    
    public String getRankAndSuit() {
        return this.suit+this.strValue;
    }
    
    public String getSuit() {
        return this.suit;
    }
    
    public String fullNameSuit() {
        return this.suitName;
    }
    
    public String getStrValue() {
        return strValue;
    }
    public String getRank() {
        return strValue;
    }

    public int getAceValue() {
        return aceValue;
    }

    public void setAceValue(int aceValue) {
        this.aceValue = aceValue;
    }

    public boolean getAccountedFor() {
        return accountedFor;
    }
    
    public void setAccountedFor(boolean accountedFor) {
        this.accountedFor = accountedFor;
    }
    public void setAccountedFor() {
        this.accountedFor = true;
    }
}
