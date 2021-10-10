package com.sherisesinyeelam.snakegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SnakeControls {

    private int currentDirection = 0; // 0,1,2,3 Up,Right,Down,Left
    private int nextDirection = 0;

    private Vector2 touch = new Vector2();

    private Rectangle upBox = new Rectangle(235,265,130,135);
    private Rectangle downBox = new Rectangle(235,0,130,135);
    private Rectangle leftBox = new Rectangle(105,135,130,130);
    private Rectangle rightBox = new Rectangle(365,135,130,130);

    public int getDirection(){
        currentDirection = nextDirection;
        return nextDirection;
    }

    public void updateDirection(Viewport viewport) {
        // detect touches
        if (Gdx.input.isTouched()){
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();
            viewport.unproject(touch);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || upBox.contains(touch) && currentDirection != 2){
            nextDirection = 0;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightBox.contains(touch) && currentDirection != 3){
            nextDirection = 1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || downBox.contains(touch) && currentDirection != 0){
            nextDirection = 2;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftBox.contains(touch) && currentDirection != 1){
            nextDirection = 3;
        }
    }
}
