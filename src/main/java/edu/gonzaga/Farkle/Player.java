package edu.gonzaga.Farkle;

public class Player {
    private String name;
    private int score;
    private int numDice;

    public Player(){
        score = 0;
        numDice = 6;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getNumDice() {
        return numDice;
    }

    public int getScore() {
        return score;
    }
}
