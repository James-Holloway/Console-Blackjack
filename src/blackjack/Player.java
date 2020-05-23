package blackjack;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    private boolean isHouse;
    private boolean isAI;
    private int balance;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private int handValue = 0;
    private int currentBet;
    private String name;
    private boolean roundRunning;
    private boolean surrendered;

    public Player(boolean isHouse) {
        this.isHouse = isHouse;
        this.name = "House";
    }

    public Player(String name, boolean isAI, int balance) {
        this.isAI = isAI;
        this.balance = balance;
        this.name = name;
    }
    
    public void receiveCard(Card card) {
        hand.add(card);
    }
    
    public void startRound() {
        roundRunning = true;
        if(isHouse) {
            System.out.println(ANSI.HOUSE_COLOUR+"\n"+name+"'s hand"+ANSI.DEFAULT);
            printHandHouse();
            if(getHandValue() >= 16) {
                roundRunning = false;
            }
        }
        else if(isAI) {
            System.out.println(ANSI.AI_COLOUR+"\n"+name+"'s hand (AI)"+ANSI.DEFAULT);
            printHand();
        }
        else {
            System.out.println(ANSI.USER_COLOUR+"\n"+name+"'s hand (User)"+ANSI.DEFAULT);
            printHand();
        }
    }
    
    public void placeBet() {
        if(isHouse) {
            System.out.println("House is ballin', it doesn't need to bet.");
        }
        else if(isAI) { //AI's betting
            int randIdiot = ThreadLocalRandom.current().nextInt(0,100);
            if(randIdiot==9) {
                currentBet = balance;
                balance-=currentBet;
            }
            else {
                double randMin = (ThreadLocalRandom.current().nextDouble(0.05,0.5));
                double randMax = (ThreadLocalRandom.current().nextDouble(0.5,0.95));
                double randMax2 = (ThreadLocalRandom.current().nextDouble(randMin,randMax));
                double randPercentage = (ThreadLocalRandom.current().nextDouble(0.05,randMax2));
                int randBet = (int) Math.round(balance*randPercentage);
                currentBet = randBet;
                balance-=currentBet;
            }
            System.out.println(ANSI.AI_COLOUR+name+" placed a bet of £"+currentBet+". Remaining: £"+balance);
        }
        else { //User's betting
            int lineInt;
            outerloop:
            while(true) {
                System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+", what do you want to place as your bet? Your balance is "+ANSI.GREEN+ANSI.DARKER+"£"+balance+ANSI.DEFAULT);
                Scanner reader = new Scanner(System.in);
                String line = reader.nextLine();
                try {
                    lineInt = Integer.parseInt(line);
                }
                catch(NumberFormatException e) {
                    System.out.println(ANSI.RED+"Input is not a valid integer."+ANSI.DEFAULT);
                    continue;
                }
                
                if(lineInt>balance) {
                    System.out.println(ANSI.RED+"That input is bigger than your balance, you can't bet more than you have."+ANSI.DEFAULT);
                }
                else if(lineInt<0) {
                    System.out.println(ANSI.RED+"Yes, yes, as much as we would like you frolicking off with our money, you can't put in a negative bet."+ANSI.DEFAULT);
                }
                else if(lineInt==0) {
                    System.out.println(ANSI.RED+"This game has stakes! You've got to place a bet."+ANSI.DEFAULT);
                }
                else if(lineInt>=balance*0.5) {
                    while(true) {
                        System.out.println(ANSI.RED+"Excuse me, but you seem to putting a bet of at least 50%, are you sure you want to do this?"+ANSI.DEFAULT);
                        line = reader.nextLine().toLowerCase();
                        if(line.equals("y") || line.equals("yes")) {
                            System.out.println("Well okay then.");
                            currentBet=lineInt;
                            balance-=currentBet;
                            break outerloop;
                        }
                        else if(line.equals("n")||line.equals("no")) {
                            continue outerloop;
                        }
                        else {
                            continue;
                        }
                    }
                }
                else {
                    currentBet = lineInt;
                    balance-=currentBet;
                    break;
                }
            }
            System.out.println(ANSI.USER_COLOUR+"Your bet: "+ANSI.DARKER+ANSI.GREEN+"£"+currentBet+ANSI.DEFAULT);
        }
    }
    
    public void turn() {
        if(isHouse) {
            houseControlledTurn();
        }
        else if(isAI) {
            AIControlledTurn();
        }
        else {
            userControlledTurn();
        }
    }
    
    private void houseControlledTurn() {
        System.out.println(ANSI.HOUSE_COLOUR+"--House's turn--"+ANSI.DEFAULT);
        checkHandValue();
        checkIfBust();
        if(roundRunning) {
            int random = ThreadLocalRandom.current().nextInt(0,25);
            if(handValue==15) {
                if(random>=1&&random<=27) {
                    stick();
                }
                else {
                    twist();
                }
            }
            else if(handValue<16) {
                twist();
            }
            else {
                stick();
            }
        }
        System.out.println(ANSI.HOUSE_COLOUR+"House's hand value: "+handValue+ANSI.DEFAULT);
        if(checkIfBust()) {
            System.out.println(ANSI.HOUSE_COLOUR+"House"+ANSI.DEFAULT+ANSI.RED+" has gone bust!"+ANSI.DEFAULT);
        }
        System.out.println();
    }
    
    private void AIControlledTurn() {
        System.out.println(ANSI.AI_COLOUR+"--"+name+"'s turn--"+ANSI.DEFAULT);
        checkHandValue();
        checkIfBust();
        if(roundRunning) {
            int random = ThreadLocalRandom.current().nextInt(0,25);
            if(handValue>18&&handValue<22) stick();
            else if(handValue==18) {
                if(random>=5&&random<=7) {
                    twist();
                }
                else {
                    stick();
                }
            }
            else if(handValue>=16&&handValue<18) {
                if(random>=1&&random<=7) {
                    twist();
                }
                else {
                    stick();
                }
            }
            else {
                twist();
            }
        }
        System.out.println(ANSI.AI_COLOUR+name+"'s hand value: "+handValue+ANSI.DEFAULT);
        if(checkIfBust()) {
            System.out.println(ANSI.AI_COLOUR+name+ANSI.DEFAULT+ANSI.RED+" has gone bust!"+ANSI.DEFAULT);
        }
        System.out.println();
    }
    
    private void userControlledTurn() {
        Scanner reader = new Scanner(System.in);
        System.out.println(ANSI.USER_COLOUR+"--"+name+"'s turn--"+ANSI.DEFAULT);
        if(checkIfBust()) {
            System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.RED+", you're bust. You might get your money back if the house goes bust."+ANSI.DEFAULT);
        }
        if(roundRunning) {
            System.out.println("Your hand: ");
            printHand();
            checkHandValue();
            printHandValue();
        }
        while(roundRunning) {
            System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+", what do you want to do? "+ANSI.DEFAULT);
            String input = reader.nextLine().toLowerCase();
            
            String[] help = {"h","help","?"};
            String[] twist = {"t","twist","hit","hit me","+"};
            String[] stick = {"s","stick","stay","keep","="};
            String[] view = {"v","view","hand","-"};
            String[] doubleDown = {"d","double down","double","2","*"};
            String[] surrender = {"sur","surrender","surender","surrennder","."};
            String[] quit = {"q","quit","exit","leave","cash out","cash","c","x"};
            String[] money = {"balance", "bal", "b", "money", "bet"};
            
            if(Blackjack.linSearch(input, help)) {
                System.out.println(ANSI.USER_COLOUR+"HELP:"+ANSI.DEFAULT
                        + "\n  Twist - pick up another card. Aliases: t, hit, hit me, +"
                        + "\n  Stick - keep your current hand. Aliases: s, stay, keep, ="
                        + "\n  View - view your current hand. Aliases: v, hand, -"
                        + "\n  Double down - double the bet but forced to pick up one last card. Aliases: d, double, 2"
                        + "\n  Surrender - keep half your bet but you're no longer playing. Aliases - sur, ."
                        + "\n  Help - show this message. Aliases: h, help, ?"
                        + "\n  Balance - get your balance and current bet. Aliases: bal, b, bet, money"
                        + "\n  Cash Out - leave the game and take your current balance, essentially quit. Aliases: cash, c, quit, leave, exit, x");
            }
            else if(Blackjack.linSearch(input, twist)) {
                twist();
                break;
            }
            else if(Blackjack.linSearch(input, stick)) {
                System.out.println("Sticking with current cards.");
                stick();
                break;
            }
            else if(Blackjack.linSearch(input, view)) {
                System.out.println("Your hand: ");
                printHand();
                printHandValue();
                continue;
            }
            else if (Blackjack.linSearch(input, doubleDown)) {
                if(hand.size()>2) {
                    System.out.println(ANSI.RED+"Could not double down. You can only do this on your first turn."+ANSI.DEFAULT);
                    continue;
                }
                if(balance<currentBet) {
                    System.out.println(ANSI.RED+"Your balance is too low to double down."+ANSI.DEFAULT);
                    continue;
                }
                System.out.println(ANSI.USER_COLOUR+"Bet doubled, picked up one card."+ANSI.DEFAULT);
                doubleDown();
                break;
            }
            else if (Blackjack.linSearch(input, surrender)) {
                System.out.println(ANSI.USER_COLOUR+"Surrendering, keeping half the bet placed."+ANSI.DEFAULT);
                surrender();
                break;
            }
            else if (Blackjack.linSearch(input, money)) {
                System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+"'s current balance: "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT+ANSI.USER_COLOUR+". Current bet: "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet+ANSI.DEFAULT);
                continue;
            }
            else if (Blackjack.linSearch(input, quit)) {
                cashOut();
                break;
            }
            else {
                System.out.println(ANSI.RED+"Unknown command. Type \"help\" for help"+ANSI.DEFAULT);
            }
        }
        System.out.println();
    }
    
    public void checkHandValue() {
        for(Card card:hand) {
            if(!card.getAccountedFor()) {
                if(card.getRank().equals("A")) {
                    card.setAceValue(acePickValue());
                    handValue += card.getAceValue();
                    card.setAccountedFor();
                }
                else {
                    handValue += Card.strValueToValue(card.getRank());
                    card.setAccountedFor();
                }
            }
        }
    }
    
    private int acePickValue() {
        if(isHouse||isAI) {
            int handValue = getHandValue();
            if(handValue==20 && hand.size()==2) return 11;
            else if(handValue<10 && hand.size()>3) return 1;
            else if(handValue>14 && handValue<18) return 1;
            else if(handValue<5) return 11;
            else if(hand.size()==4 && handValue < 20) return 1;
            else return 1;
        }
        else { //User
            Scanner reader = new Scanner(System.in);
            while(true) {
                System.out.println(ANSI.USER_COLOUR+"You have an ace, do you want its value to be 1 or 11?"+ANSI.DEFAULT);
                String choice = reader.nextLine();
                if(choice.equals("1")) {
                    return 1;
                }
                else if(choice.equals("11")) {
                    System.out.println("You chose a value of 11.");
                    return 11;
                }
                else {
                    System.out.println(ANSI.RED+"Sorry, that wasn't '1' or '11', please choose one of those numbers."+ANSI.DEFAULT);
                }
            }
        }
    }
    
    private void printHand() {
        for(Card card:hand) {
            card.printRankAndSuit();
        }
    }
    
    public void houseRoundEnded() {
        if(checkIfBust()) {
            System.out.println(ANSI.HOUSE_COLOUR+"House's end hand value: "+ANSI.DEFAULT+ANSI.RED+handValue+ANSI.DEFAULT);
        }
        else  {
            System.out.println(ANSI.HOUSE_COLOUR+"House's end hand value: "+handValue+ANSI.DEFAULT);
        }
    }
   
    public void houseRoundCleanup() {
        hand.removeAll(hand);
        handValue = 0;
    }
    
    public void roundEnded() {
        if(surrendered) {
            if(isAI) System.out.println(ANSI.AI_COLOUR+name+" got half their bet back, they surrendered."+ANSI.DEFAULT);
            else System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" got half their bet back, they surrendered."+ANSI.DEFAULT);
            hand.removeAll(hand);
            handValue = 0;
            currentBet = 0;
            surrendered=false;
            System.out.println();
            return;
        };
        if(isAI) System.out.println(ANSI.AI_COLOUR+name+"'s end hand value: "+handValue+ANSI.DEFAULT);
        else System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+"'s end hand value: "+handValue+ANSI.DEFAULT);
        if(checkIfBlackjack()) {
            if(Blackjack.getTable().getHouse().checkIfBlackjack()) { //house also got blackjack - 1x
                balance+=currentBet;
                if(isAI) {
                    System.out.println(ANSI.AI_COLOUR+name+" made a return of 1x - "+ANSI.DEFAULT+ANSI.BLUE+"£"+currentBet+ANSI.DEFAULT);
                }
                else {
                    System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 1x - "+ANSI.DEFAULT+ANSI.BLUE+"£"+currentBet+ANSI.DEFAULT);
                }
            }
            
            else { //house didn't get blackjack - 2x
                balance+=currentBet*2;
                if(isAI) {
                    System.out.println(ANSI.AI_COLOUR+name+" made a return of 2x - "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet*2+ANSI.DEFAULT);
                }
                else {
                    System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 2x - "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet*2+ANSI.DEFAULT);
                }
            }
        }
        
        else if(checkIfBust()) {
            if(Blackjack.getTable().getHouse().checkIfBust()) { //house is also bust - 1x
                balance+=currentBet;
                if(isAI) {
                    System.out.println(ANSI.AI_COLOUR+name+" made a return of 1x - "+ANSI.DEFAULT+ANSI.BLUE+"£"+currentBet+ANSI.DEFAULT);
                }
                else {
                    System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 1x - "+ANSI.DEFAULT+ANSI.BLUE+"£"+currentBet+ANSI.DEFAULT);
                }
            }
            else { //house not bust - 0x
                if(isAI) {
                    System.out.println(ANSI.AI_COLOUR+name+" made a return of 0x - "+ANSI.DEFAULT+ANSI.RED+"£0"+ANSI.DEFAULT);
                }
                else {
                    System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 0x - "+ANSI.DEFAULT+ANSI.RED+"£0"+ANSI.DEFAULT);
                }
            }
        }
        else { //if not blackjack or bust
            if(Blackjack.getTable().getHouse().checkIfBust()) { //if house bust, then 1.5x
                balance+=currentBet*1.5;
                if(isAI) {
                    System.out.println(ANSI.AI_COLOUR+name+" made a return of 1.5x - "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet*1.5+ANSI.DEFAULT);
                }
                else {
                    System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 1.5x - "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet*1.5+ANSI.DEFAULT);
                }
            }
            else { //if house didn't go bust
                int houseValue = Blackjack.getTable().getHouse().getHandValue();
                if(handValue>houseValue) { //if hand is better than house's, then 1.5x
                    balance+=currentBet*1.5;
                    if(isAI) {
                        System.out.println(ANSI.AI_COLOUR+name+" made a return of 1.5x - "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet*1.5+ANSI.DEFAULT);
                    }
                    else {
                        System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 1.5x - "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet*1.5+ANSI.DEFAULT);
                    }
                }
                else if(handValue<houseValue) { //if hand is worse than house's, 0x 
                    if(isAI) {
                        System.out.println(ANSI.AI_COLOUR+name+" made a return of 0x - "+ANSI.DEFAULT+ANSI.RED+"£0"+ANSI.DEFAULT);
                    }
                    else {
                        System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 0x - "+ANSI.DEFAULT+ANSI.RED+"£0"+ANSI.DEFAULT);
                    }
                }
                else if(handValue==houseValue) { //if hand is equal to house's value, 1x
                    if(hand.size()==5) { //5 card trick, 1.5x
                        System.out.println("Holey moley! It's a 5 card trick!");
                        balance+=currentBet*1.5;
                        if(isAI) {
                            System.out.println(ANSI.AI_COLOUR+name+" made a return of 1.5x - "+ANSI.DEFAULT+ANSI.GREEN+ANSI.DARKER+"£"+currentBet+ANSI.DEFAULT);
                        }
                        else {
                            System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 1.5x - "+ANSI.DEFAULT+ANSI.GREEN+ANSI.DARKER+"£"+currentBet+ANSI.DEFAULT);
                        }
                    }
                    else {
                        balance+=currentBet;
                        if(isAI) {
                            System.out.println(ANSI.AI_COLOUR+name+" made a return of 1x - "+ANSI.DEFAULT+ANSI.BLUE+"£"+currentBet+ANSI.DEFAULT);
                        }
                        else {
                            System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" made a return of 1x - "+ANSI.DEFAULT+ANSI.BLUE+"£"+currentBet+ANSI.DEFAULT);
                        }
                    }
                }
                else {
                    System.out.println("This message shouldn't print - it's impossible!");
                }
            }
        }
        
        //PRINTING OF BALANCE
        if(isAI) {
            System.out.println(ANSI.AI_COLOUR+name+"'s current balance: "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
        }
        else {
            System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+"'s current balance: "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
        }
        hand.removeAll(hand);
        handValue = 0;
        currentBet = 0;
        System.out.println();
    }
    
    private void stick() {
        roundRunning=false;
        if(isHouse) {
            System.out.print("House is sticking with a hand value of ");
            printHandValue();
            System.out.println("\nFinal hand: ");
            printHand();
        }
        else if(isAI) {
            System.out.print(name+" is sticking with a hand value of ");
            printHandValue();
            System.out.println("\nFinal hand: ");
            printHand();
        }
        else {
            System.out.println("Chose to stick with the hand of ");
            printHand();
            System.out.println("\nFinal hand: ");
            printHandValue();
        }
        System.out.println("Sticking with hand value of "+handValue);
    }
    
    private void cashOut() {
        balance+=currentBet;
        currentBet=0;
        System.out.println(ANSI.DARKER+ANSI.BLUE+name+ANSI.DEFAULT+ANSI.USER_COLOUR+" cashed out at a value of "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
        Blackjack.getTable().getPlayers().remove(this);
    }
    
    public void test1CheckOutRound() {
        if(isHouse) {
            //Shouldn't do anything - house never gets bored
        }
        else if(isAI) {
            if(balance>=Blackjack.NPCStartingBalance*2) {
                System.out.println(ANSI.AI_COLOUR+name+" cashed out at a value of "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
                Blackjack.getTable().getPlayers().remove(this);
            }
            else if(balance<=Blackjack.NPCStartingBalance/10) {
                System.out.println(ANSI.AI_COLOUR+name+" cashed out at a value of "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
                Blackjack.getTable().getPlayers().remove(this);
            }
        }
    }

    public void test2CheckOutRound() {
        if(isHouse) {
            //Shouldn't do anything - house never gets bored
        }
        else if(isAI) {
            if(balance>=Blackjack.NPCStartingBalance*1.6) {
                System.out.println(ANSI.AI_COLOUR+name+" cashed out at a value of "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
                Blackjack.getTable().getPlayers().remove(this);
            }
            else if(balance<=Blackjack.NPCStartingBalance/5) {
                System.out.println(ANSI.AI_COLOUR+name+" cashed out at a value of "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
                Blackjack.getTable().getPlayers().remove(this);
            }
        }
    }
    
    public void test3CheckOutRound() {
        if(isHouse) {
            //Shouldn't do anything - house never gets bored
        }
        else if(isAI) {
            if(balance>=Blackjack.NPCStartingBalance*1.25) {
                System.out.println(ANSI.AI_COLOUR+name+" cashed out at a value of "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
                Blackjack.getTable().getPlayers().remove(this);
            }
            else if(balance<=Blackjack.NPCStartingBalance/2) {
                System.out.println(ANSI.AI_COLOUR+name+" cashed out at a value of "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+balance+ANSI.DEFAULT);
                Blackjack.getTable().getPlayers().remove(this);
            }
        }
    }
    
    private void twist() {
        receiveCard(Blackjack.getTable().getShoe().getCard());
        checkHandValue();
        if(isHouse) {
            System.out.println("House picked up a new card, ");
            hand.get(hand.size()-1).printRankAndSuit();
            printHand();
        }
        else if(isAI) {
            System.out.print(name+" picked up a new card, ");
            hand.get(hand.size()-1).printRankAndSuit();
            printHand();
        }
        else {
            System.out.print("You picked up a new card, ");
            hand.get(hand.size()-1).printRankAndSuit();
            printHand();
            printHandValue();
        }
    }
    private void doubleDown() {
        balance-=currentBet;
        currentBet+=currentBet;
        roundRunning=false;
        receiveCard(Blackjack.getTable().getShoe().getCard());
        System.out.print(ANSI.USER_COLOUR+"Picked up a card, ");
        hand.get(hand.size()-1).printRankAndSuit();
        if(isAI) System.out.println(ANSI.AI_COLOUR+name+"'s bet is now "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet+ANSI.DEFAULT);
        else System.out.println(ANSI.BLUE+ANSI.DARKER+name+ANSI.DEFAULT+ANSI.USER_COLOUR+"'s bet is now "+ANSI.DEFAULT+ANSI.DARKER+ANSI.GREEN+"£"+currentBet+ANSI.DEFAULT);
        checkHandValue();
    }

    private void surrender() {
        //half the bet kept
        balance+=currentBet/2;
        currentBet=0;
        roundRunning=false;
        surrendered=true;
    }
    
    private void printHandHouse() {
        Card card = hand.get(0);
        if(card.getSuit().equals("♥")||card.getSuit().equals("♦")) {
            System.out.println(ANSI.RED+card.getRankAndSuit()+ANSI.DEFAULT);
        }
        else {
            System.out.println(ANSI.BLACK+card.getRankAndSuit()+ANSI.DEFAULT);
        }
        System.out.println("??\n");
    }
    
    public boolean checkIfBankrupt() {
        if(isHouse) return false;
        return balance<=0;
        
    }
    
    private void printHandPlain() {
        for(Card card:hand) {
            System.out.println(card.getRankAndSuit());
        }
    };
    
    private int getHandValue() {
        return handValue;
    }
    
    private void printHandValue() {
        System.out.println("Hand value: "+handValue);
    }
    
    public boolean checkIfBust() {
        if(handValue>21) {
            roundRunning = false;
        }
        return handValue>21;
    }
    
    public boolean checkIfBlackjack() {
        return handValue==21;
    }
    
    public int getBalance() {
        return balance;
    }

    public ArrayList getHand() {
        return hand;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public String getName() {
        return name;
    }

    public boolean isAI() {
        return isAI;
    }
    public boolean isRoundRunning() {
        return roundRunning;
    }    
}