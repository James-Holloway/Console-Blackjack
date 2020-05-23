package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Blackjack {
    private static boolean running = true;
    private static int currentRound = 1;
    private static Scanner reader = new Scanner(System.in);
    private static ArrayList<String> AInames = new ArrayList<>();
    private static Table table;
    
    private static final int NPCCashOutCheck1Round = 10;
    private static final int NPCCashOutCheck2Round = 20;
    private static final int NPCCashOutCheck3Round = 35;
    
    public static final int NPCStartingBalance = 500;
    public static final int UserStartingBalance = 500;
    
    
    public static void main(String[] args) {
        init();
        while(running) {
            round();
        }
    }
    public static void init() {
        table = new Table();
        table.init();
        String[] names = {"Joey","Tommy","Big Sam","Johnny Sausage","Jimmy","Big Tony","Big Frank","Little Nicky","Vinnie the Butcher","Crazy Joe","The Dapper Don","Tall Pete","Big Billy","Junior","Fish","Old Man Tony","Revolvers","Danny","Average Larry","Benny Squint","Lead Pipe Joe","One Armed Ronnie","Porky","Little Tony","Paulie","Mickey White","The Chief","Joe Cargo","Johnny Two Times","Stubborn Ronnie"};
        Collections.addAll(AInames,names);
        while(true) { //NPC ADDING
            System.out.println(ANSI.USER_COLOUR+"\nHow many NPCs do you want to add (excluding the house)?"+ANSI.DEFAULT);
            String plyc = reader.nextLine();
            int plycount;
            int plycParse;
            int maxNPCCount = 8;
            int minNPCCount = 0;
            try {
                plycParse = Integer.parseInt(plyc);
                if(plycParse>=minNPCCount && plycParse<=maxNPCCount) {
                    plycount = plycParse;
                }
                else {
                    System.out.println(ANSI.RED+"Please enter a valid number between "+minNPCCount+" and "+maxNPCCount+ANSI.DEFAULT);
                    continue;
                }
            }
            catch(NumberFormatException e) {
                System.out.println(ANSI.RED+"Please enter a valid number between "+minNPCCount+" and "+maxNPCCount+ANSI.DEFAULT);
                continue;
            }
            for(int i=0;i<plycount;i++) {
                int randomNameIndex = ThreadLocalRandom.current().nextInt(0,AInames.size());
                String name = AInames.get(randomNameIndex);
                AInames.remove(randomNameIndex);
                table.AddNPC(name);
            }
            System.out.println("Added "+table.getPlayers().size()+" players");
            break;
        }
        
        while(true) { //USER ADDING
            System.out.println(ANSI.USER_COLOUR+"\nHow many users do you want to add?"+ANSI.DEFAULT);
            String plyc = reader.nextLine();
            int plycount;
            int plycParse;
            int maxPlayerCount = 8;
            int minPlayerCount = 1;
            try {
                plycParse = Integer.parseInt(plyc);
                if(plycParse>=minPlayerCount && plycParse<=maxPlayerCount) {
                    plycount = plycParse;
                }
                else {
                    System.out.println(ANSI.RED+"Please enter a valid number between "+minPlayerCount+" and "+maxPlayerCount+ANSI.DEFAULT);
                    continue;
                }
            }
            catch(NumberFormatException e) {
                System.out.println(ANSI.RED+"Please enter a valid number between "+minPlayerCount+" and "+maxPlayerCount+ANSI.DEFAULT);
                continue;
            }
            System.out.println("You chose "+plycount+" users to add.");
            String name;
            for(int i=0;i<plycount;i++) {
                outerloop:
                while(true) {
                    System.out.println(ANSI.USER_COLOUR+"What do you want user #"+(i+1)+" to be called?"+ANSI.DEFAULT);
                    name = reader.nextLine();
                    if(name.trim().equals("")) {
                        System.out.println(ANSI.RED+"Please enter a valid string for the user's name"+ANSI.DEFAULT);
                        continue;
                    } 
                    else {
                        for (Player ply:table.getPlayers()) {
                            if(ply.getName().equals(name)) {
                                System.out.println(ANSI.RED+"Please enter a name that hasn't already been used"+ANSI.DEFAULT);
                                continue outerloop;
                            }
                        }
                    }
                    break;
                }
                table.AddUser(name);
            }
            System.out.print("Added "+table.getPlayers().size()+" players - ");
            for(int i=0;i<table.getPlayers().size()-1;i++) {
                String printColour;
                if(table.getPlayers().get(i).isAI()==true) printColour = ANSI.AI_COLOUR;
                else if(table.getPlayers().get(i).isAI()==false) printColour = ANSI.USER_COLOUR;
                else printColour = ANSI.HOUSE_COLOUR;
                System.out.print(printColour+table.getPlayers().get(i).getName()+ANSI.DEFAULT+", ");
            }
            System.out.println(ANSI.USER_COLOUR+table.getPlayers().get(table.getPlayers().size()-1).getName()+ANSI.DEFAULT);
            break;
        }
    }
    
    public static void round() {
        System.out.println(ANSI.DARKER+ANSI.BLUE+"\n\n=====Current round: "+currentRound+"====="+ANSI.DEFAULT);
        
        for(Player ply:table.getPlayers()) {
            ply.placeBet();            
        }
        deal();
        //Dealing cards
        for(Player ply:table.getPlayers()) {
            ply.startRound();
        }
        table.getHouse().startRound();
        
        
        while(true) {
            int playersStillTurning = table.getPlayers().size();
            int i=0;
            while(i<=table.getPlayers().size()-1) {
//I use while loops because the for loop has it's parameters defined at the start, which means the size of the array can change and mess everything up
                Player ply = table.getPlayers().get(i);
                if(ply.isRoundRunning()) {
                    ply.turn();
                }
                else {
                    playersStillTurning--;
                }
                i++;
            }
            if(table.getHouse().isRoundRunning()) {
                table.getHouse().turn();
            }
            if(playersStillTurning==0) {
                break;
            }
        }
        //end of round
        table.getHouse().houseRoundEnded();
        int i=0;
        while(i<=table.getPlayers().size()-1) {
            Player ply = table.getPlayers().get(i);
            ply.roundEnded();
            i++;
        }
        
        table.getHouse().houseRoundCleanup();
        
        i=0;
        //check if bankrupt
        while(i<=table.getPlayers().size()-1) { //for loop but doesn't crash hopefully
            if(table.getPlayers().get(i).checkIfBankrupt()) {
                table.getPlayers().remove(i);
            }
            i++;
        }
        
        if(currentRound>=NPCCashOutCheck1Round&&currentRound<NPCCashOutCheck2Round) { //test if NPCs cashing out yet first
            i=0;
            while(i<=table.getPlayers().size()-1) {
                table.getPlayers().get(i).test1CheckOutRound();
                i++;
            }
        }
        if(currentRound>=NPCCashOutCheck2Round&&currentRound<NPCCashOutCheck3Round) { //test if NPCs cashing out yet second
            i=0;
            while(i<=table.getPlayers().size()-1) {
                table.getPlayers().get(i).test2CheckOutRound();
                i++;
            }
        }
        if(currentRound>=NPCCashOutCheck3Round) { //test if NPCs cashing out yet third
            i=0;
            while(i<=table.getPlayers().size()-1) {
                table.getPlayers().get(i).test3CheckOutRound();
                i++;
            }
        }
        if(table.getPlayers().isEmpty()) {
            System.out.println(ANSI.YELLOW+ANSI.DARKER+"================================================"+ANSI.DEFAULT);
            System.out.println(ANSI.YELLOW+ANSI.DARKER+"That game took "+currentRound+" rounds. Thanks for playing!"+ANSI.DEFAULT);
            System.out.println(ANSI.YELLOW+ANSI.DARKER+"Blackjack, made by James"+ANSI.DEFAULT);
            System.out.println(ANSI.YELLOW+ANSI.DARKER+"================================================"+ANSI.DEFAULT);
            running=false;
        }
        currentRound++;
        /*if(currentRound>10) {
            running = false;
        }*/
    }
   
    public static void deal() {
        int i=0;
        while(i<=table.getPlayers().size()-1) {
            Player ply = table.getPlayers().get(i);
            ply.receiveCard(table.getShoe().getCard()); //first card
            ply.receiveCard(table.getShoe().getCard()); //second card
            i++;
        }
        //LEGACY CODE
        /*for (int i=0;i<players.size();i++) {
            Player ply = (Player)players.get(i);
            ply.receiveCard(table.getShoe().getCard()); //first card
            ply.receiveCard(table.getShoe().getCard()); //second card
        }*/
        //Didn't work because users could cash out mid game.
        
        table.getHouse().receiveCard(table.getShoe().getCard()); //first card
        table.getHouse().receiveCard(table.getShoe().getCard()); //second card
    }
    
    public static void dealOne(Player ply) {
        ply.receiveCard(table.getShoe().getCard());
    }

    public static Table getTable() {
        return table;
    }
    
    public static boolean linSearch(String term, String[] list) {
        for(String item:list) {
            if(term.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
