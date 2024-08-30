/*
 Name: James Hagan
 Class: CPSC 224, Spring 2024
 Farkle 2
 Description: Runs a singleplayer multi-turned farkle
*/
package edu.gonzaga.Farkle;

import java.util.ArrayList;

public class TurnHand {
    //determines if in meld
    private ArrayList<Boolean> isInMeld = new ArrayList<>();;
    //dice object
    private ArrayList<Die> dice = new ArrayList<>();;
    //Constructor
    public TurnHand(int numDice) {
        for (int i = 0; i < numDice; i++){
            dice.add(new Die(6));
            isInMeld.add(false);
        }
    }
    //get meld status
    public ArrayList<Boolean> getIsInMeld() {
        return isInMeld;
    }
    //Gets dice object
    public ArrayList<Die> getDice() {
        return dice;
    }
    //Rolls dice
    public void rollDice(int numDice) {
        for (int i = 0; i < numDice; i++){
            dice.get(i).roll();
        }
    }
    //Prints dice list
    public String printDice(int numDice) {
        String rern = "";
        for (int i = 0; i < numDice; i++){
            rern = rern + dice.get(i);
        }
        return rern;
    }
    //Banks the Dice
    public void bankDice(String choice) {
        char c = (choice.charAt(0));
        if (c >= 'A' && c <= 'F') {
            int index = c - 'A';
            isInMeld.set(index, !isInMeld.get(index));
        }
    }
    //Prints banked/unbanked dice
    public void printBank(int numDice) {
        System.out.println(" |Hand|Bank|");
        char c = 'A';
        for (int i = 0; i < numDice; i++){
            System.out.print(c + ": ");
            printBankLocation(dice.get(i), isInMeld.get(i));
            c++;
        }
    }
    //Prints banked/unbanked dice location
    private void printBankLocation(Die die, Boolean banked) {
        if (banked == false){
            System.out.println(die + "  |");
        }
        else {
            System.out.println( "   | " + die);
        }
    }
    public int getNumInMeld(int numDice){
        int numMelded = 0;
        for(int i = 0; i < numDice; i++){
            if (isInMeld.get(i) == true)
                numMelded++;
        }
        return numMelded;
    }
    public void reset (int numDice) {
        for (int i = 0; i < numDice; i++) {
            isInMeld.set(i, false);
        }
    }
}
