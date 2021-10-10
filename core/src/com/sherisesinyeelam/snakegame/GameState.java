package com.sherisesinyeelam.snakegame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameState {

    // create a square board for the game to take place on
    private int boardSize = 30;

    //a ShapeRenderer to draw rectangles for us.
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private int yOffset = 400;

    private SnakeControls snakeControls = new SnakeControls();
    private Queue<SnakeBodypart> snakeBody = new Queue<SnakeBodypart>();

    private float timer = 0;

    private Food food = new Food(boardSize);
    private int snakeLength = 3;

    private float colourCounter = 0;


    public GameState() {
        snakeBody.addLast(new SnakeBodypart(15,15,boardSize)); // snake head
        snakeBody.addLast(new SnakeBodypart(15,14,boardSize));
        snakeBody.addLast(new SnakeBodypart(15,13,boardSize)); // snake tail
    }

    // updating the game logic
    public void update (float delta, Viewport viewport) {
        timer += delta;
        colourCounter += delta;
        snakeControls.updateDirection(viewport);
        // reset the timer
        if(timer > 0.13f){
            timer = 0;
            advance();
        }
    }

    // adds one to the snake and takes one off the end.
    private void advance(){
        int headX = snakeBody.first().getX();
        int headY = snakeBody.first().getY();

        switch (snakeControls.getDirection()){
            case 0: // up
                snakeBody.addFirst(new SnakeBodypart(headX, headY+1, boardSize));
                break;
            case 1: // right
                snakeBody.addFirst(new SnakeBodypart(headX+1, headY, boardSize));
                break;
            case 2: // down
                snakeBody.addFirst(new SnakeBodypart(headX, headY-1, boardSize));
                break;
            case 3: // left
                snakeBody.addFirst(new SnakeBodypart(headX-1, headY, boardSize));
                break;
            default: // should never happen
                snakeBody.addFirst(new SnakeBodypart(headX, headY+1, boardSize));
                break;
        }

        // eating the food and randomise the food location
        // detect if the newly placed head is touching the food. If it is, we increment the snakeLength and randomise the food position.
        if (snakeBody.first().getX() == food.getX() && snakeBody.first().getY() == food.getY()){
            snakeLength++;
            food.randomisePos(boardSize); // TODO to check it's not in body
        }

        // If the head touches another part of the body, the length of the snake should reset to 3.
        // initialise int i as 1 so that it doesn't check if the xy of the head matches the xy of the head, as this would always be true.
        for ( int i = 1; i < snakeBody.size; i++){
            if (snakeBody.get(i).getX() == snakeBody.first().getX() && snakeBody.get(i).getY() == snakeBody.first().getY()){
                snakeLength = 3;
            }
        }

        // if the player has 'died' we can remove all extra bodyParts in the queue.
        // keep removing body parts until the snake reaches length 3.
        while (snakeBody.size-1 >= snakeLength){
            snakeBody.removeLast();
        }
    }

    // drawing the snake and board.
    public void draw(int width, int height, OrthographicCamera camera){
        // sets up the ShapeRenderer to draw filled shapes in the correct position.
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // rectangle drawing happens here
        // drawing two rectangles, one slightly bigger than the other.
        shapeRenderer.setColor(1,1,1,1); // board outline
        shapeRenderer.rect(0, yOffset, width, width);

        shapeRenderer.setColor(0,0,0,1);
        shapeRenderer.rect(0+5, yOffset+5, width-5*2, width-5*2);

        shapeRenderer.setColor(MathUtils.sin(colourCounter),-MathUtils.sin(colourCounter),1,1);

        // draw the control buttons
        shapeRenderer.rect(235,265,130,135);
        shapeRenderer.rect(235,0,130,135);
        shapeRenderer.rect(105,135,130,130);
        shapeRenderer.rect(365,135,130,130);

        // width of one board square
        float scaleSnake = width/boardSize;

        // drawing the food here
        shapeRenderer.rect(food.getX() * scaleSnake, food.getY() * scaleSnake + yOffset, scaleSnake, scaleSnake);

        // snake drawing happens here
        // The loop iterates through all SnakeBodypart and draws them, scaled by the scaleSnake factor which converts from the board size to the screen size.
        for ( SnakeBodypart body: snakeBody){
            shapeRenderer.rect(body.getX() * scaleSnake, body.getY() * scaleSnake + yOffset, scaleSnake, scaleSnake);
        }

        shapeRenderer.end();
    }
}
