/*
 Name: James Hagan
 Class: CPSC 224, Spring 2024
 Farkle 2
 Description: Runs a singleplayer multi-turned farkle
*/
package edu.gonzaga.Farkle;

import java.util.ArrayList;
//Checks for farkle
public class FarkleScoreChecker {
    public Boolean checkFarkle(ArrayList<Die> dice, int numDice) {
        ArrayList<Boolean> alwaysTrue = new ArrayList<>();
        for (int i = 0; i < numDice; i++) {
            alwaysTrue.add(true);
        }

        int num1 = numberTally(1,dice, alwaysTrue,numDice);
        int num2 = numberTally(2,dice, alwaysTrue,numDice);
        int num3 = numberTally(3,dice, alwaysTrue,numDice);
        int num4 = numberTally(4,dice, alwaysTrue,numDice);
        int num5 = numberTally(5,dice, alwaysTrue,numDice);
        int num6 = numberTally(6,dice, alwaysTrue,numDice);

        if (num1 >= 1){
            return true;
        }
        else if (num5 >= 1) {
            return true;
        }
        else if (num2 >= 3) {
            return true;
        }
        else if (num3 >= 3) {
            return true;
        }
        else if (num4 >= 3) {
            return true;
        }
        else if (num6 >= 3) {
            return true;
        }
        else {
            return false;
        }
    }
    //Calculates Score
    public int scoreCalculator(ArrayList<Die> dice, ArrayList<Boolean> isInMeld, int numDice){
        int score = 0;
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(0);
        nums.add(numberTally(1,dice, isInMeld,numDice));
        nums.add(numberTally(2,dice, isInMeld,numDice));
        nums.add(numberTally(3,dice, isInMeld,numDice));
        nums.add(numberTally(4,dice, isInMeld,numDice));
        nums.add(numberTally(5,dice, isInMeld,numDice));
        nums.add(numberTally(6,dice, isInMeld,numDice));

        score += nums.get(1) * 100;
        score += nums.get(5) * 50;
        if (nums.get(1) >= 3) {
            score = score - 300;
            score = score + 1000;
        }
        if (nums.get(2) >= 3) {
            score = score + 200;
            for (int i = 0; i < nums.get(2) - 3; i++){
                score = score + 200;
            }
        }
        if (nums.get(3) >= 3) {
            score = score + 300;
            for (int i = 0; i < nums.get(3) - 3; i++){
                score = score + 300;
            }
        }
        if (nums.get(4) >= 3) {
            score = score + 400;
            for (int i = 0; i < nums.get(4) - 3; i++){
                score = score + 400;
            }
        }
        if (nums.get(5) >= 3) {
            score = score - (nums.get(5) * 50);
            score = score + 500;
            for (int i = 0; i < nums.get(5) - 3; i++){
                score = score + 500;
            }
        }
        if (nums.get(6) >= 3) {
            score = score + 600;
            for (int i = 0; i < nums.get(6) - 3; i++){
                score = score + 600;
            }
        }
        if (checkForStraight(nums,numDice)){
            score = 1000;
        }
        if (checkForThree(nums,numDice)){
            score = 750;
        }
        if (checkFullHouse(nums,numDice)){
            score = 1500;
        }
        return score;
    }
    //Calculates number of type
    private int numberTally(int number, ArrayList<Die> dice, ArrayList<Boolean> isInMeld, int numDice) {
        int num = 0;
        for(int i = 0; i < numDice; i++){
            if (dice.get(i).getSideUp() == number) {
                if(isInMeld.get(i) == true) {
                    num++;
                }
            }
        }
        return num;
    }
    private boolean checkForStraight(ArrayList<Integer> nums, int numDice) {
        int numOf1 = 0;
        for(int i = 0; i < numDice + 1; i++) {
            if(nums.get(i).equals(1)){
                numOf1++;
            }
        }
        if(numOf1 == 6){
            return true;
        }
        return false;
    }
    private boolean checkForThree(ArrayList<Integer> nums, int numDice) {
        int numOfPairs = 0;
        for(int i = 0; i < numDice + 1; i++) {
            if(nums.get(i).equals(2)){
                numOfPairs++;
            }
        }
        if(numOfPairs == 3){
            return true;
        }
        return false;
    }

    private boolean checkFullHouse(ArrayList<Integer> nums, int numDice){
        boolean threeDice = false;
        boolean twoDice = false;
        for(int i = 0; i < numDice + 1; i++) {
            if(nums.get(i).equals(3)){
                threeDice = true;
            }
            if(nums.get(i).equals(2)){
                twoDice = true;
            }
        }
        if (threeDice && twoDice) {
            return true;
        }
        return false;
    }
}
