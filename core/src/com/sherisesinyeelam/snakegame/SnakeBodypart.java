package com.sherisesinyeelam.snakegame;

public class SnakeBodypart {

    private int x;
    private int y;

    public SnakeBodypart(int x, int y, int boardSize){
        this.x = x % boardSize;
        if (this.x < 0){
            this.x += boardSize;
        }

        this.y = y % boardSize;
        if (this.y < 0){
            this.y += boardSize;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
